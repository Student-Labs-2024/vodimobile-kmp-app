//
//  KMPApiManager.swift
//  iosApp
//
//  Created by Sergey Ivanov on 06.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared
import Combine

// swiftlint:disable type_body_length
final class KMPApiManager: ObservableObject {
    private var helper = KoinHelper()
    @ObservedObject var dataStorage = KMPDataStorage.shared
    @ObservedObject var appState = AppState.shared
    @Published var isLoading = false
    static let shared = KMPApiManager()

    init() {
        appState.checkConnectivity()
        Task {
            await setUserTokens()
        }
    }

    func isPhoneAlreadyExists(_ phone: String) async -> Bool? {
        do {
            return try await helper.hasUserWithPhone(phone: phone).boolValue
        } catch {
            print(error)
        }
        return nil
    }

    func getSupaUser(pass: String, phone: String) async -> User? {
        if appState.isConnected {
            await MainActor.run {
                isLoading = true
            }
            do {
                let supaUser = try await helper.getUser(password: pass, phone: phone.cleanUp())
                return supaUser
            } catch {
                print(error)
            }
            await MainActor.run {
                isLoading = false
            }
        }
        return nil
    }

    func regNewUser(fullname: String, phone: String, password: String) async {
        if appState.isConnected {
            await MainActor.run {
                isLoading = true
            }
            if let storageUser = dataStorage.gettingUser {
                do {
                    try await helper.insertUser(
                        user: User(
                            id: 0,
                            fullName: fullname,
                            password: password,
                            accessToken: storageUser.accessToken,
                            refreshToken: storageUser.refreshToken,
                            phone: phone.cleanUp()
                        )
                    )
                } catch {
                    print(error)
                }
            }
            await MainActor.run {
                isLoading = false
            }
        }
    }

    func setUserTokens() async {
        if appState.isConnected {
            await MainActor.run {
                isLoading = true
            }
            let storageUser: User
            if let storageUserData = dataStorage.gettingUser {
                storageUser = storageUserData
            } else {
                storageUser = User.companion.empty()
            }
            do {
                let response = try await helper.postUser()
                switch onEnum(of: response) {
                case .crmData(let success):
                    if let user = success.data {
                        let newUser = User(
                            id: storageUser.id,
                            fullName: storageUser.fullName,
                            password: storageUser.password,
                            accessToken: user.accessToken,
                            refreshToken: user.refreshToken,
                            phone: storageUser.phone
                        )
                        await MainActor.run {
                            self.dataStorage.gettingUser = newUser
                        }
                    }
                case .crmError(let error):
                    print(error.status?.value ?? "Empty error")
                case .crmLoading:
                    print("loading...")
                }
            } catch {
                print(error)
            }
            await MainActor.run {
                isLoading = false
            }
        }
    }

    func changeUserPassword(
        newPassword: String,
        isResetPassFlow: Bool = false,
        phone: String = ""
    ) async -> Bool {
        if appState.isConnected {
            if !isResetPassFlow {
                await getAuthIfNeed()
                if let storageUser = dataStorage.gettingUser {
                    do {
                        try await helper.updatePassword(userId: storageUser.id, password: newPassword)
                        let newUserData = User(
                            id: storageUser.id,
                            fullName: storageUser.fullName,
                            password: newPassword,
                            accessToken: storageUser.accessToken,
                            refreshToken: storageUser.refreshToken,
                            phone: storageUser.phone
                        )
                        try await dataStorage.editUserData(newUserData)
                        return true
                    } catch {
                        print(error)
                    }
                }
            } else {
                do {
                    let userWithPhone = try await helper.getUserWithPhone(phone: phone.cleanUp())
                    if userWithPhone.id > 0 {
                        do {
                            try await helper.updatePassword(userId: userWithPhone.id, password: newPassword)
                            return true
                        } catch {
                            print(error)
                        }
                    }
                } catch {
                    print(error)
                }
            }
        }
        return false
    }

    func changeUserFullname(newFullname: String) async -> Bool {
        if appState.isConnected {
            await getAuthIfNeed()
            if let storageUser = dataStorage.gettingUser {
                do {
                    try await helper.updateFullName(userId: storageUser.id, fullName: newFullname)
                    return true
                } catch {
                    print(error)
                }
            }
        }
        return false
    }

    func fetchFreeCarIdsForDate(for carFreeListParamsDTO: CarFreeListParamsDTO) async -> [Int] {
        if appState.isConnected {
            if let storageUser = dataStorage.gettingUser {
                await MainActor.run {
                    isLoading = true
                }
                do {
                    let response = try await helper.getFreeCars(
                        accessToken: storageUser.accessToken,
                        refreshToken: storageUser.refreshToken,
                        carFreeListParamsDTO: carFreeListParamsDTO
                    )
                    switch onEnum(of: response) {
                    case .crmData(let success):
                        if let freeCars = success.data {
                            return (0..<freeCars.cars.size).compactMap { index in
                                guard let kotlinInt = freeCars.cars.get(index: index) else { return nil }
                                return kotlinInt.intValue
                            }
                        }
                    case .crmError(let error):
                        print(error.status?.value ?? "Empty error")
                    case .crmLoading:
                        print("loading...")
                    }
                } catch {
                    print(error)
                }
                await MainActor.run {
                    isLoading = false
                }
            }
        }
        return []
    }

    func fetchCarFreeDateRange(carId: Int32, beginDate: String, endDate: String) async -> [(Int64, Int64)] {
        if appState.isConnected {
            await MainActor.run {
                isLoading = true
            }
            do {
                if let storageUser = dataStorage.gettingUser {
                    let response = try await helper.getCarFreeDateRange(
                        accessToken: storageUser.accessToken,
                        refreshToken: storageUser.refreshToken,
                        carId: carId,
                        begin: beginDate,
                        end: endDate
                    )
                    return response.map { pair in
                        let first = pair.first?.int64Value ?? 0
                        let second = pair.second?.int64Value ?? 0
                        return (first, second)
                    }
                }
            } catch {
                print(error)
            }
            await MainActor.run {
                isLoading = false
            }
        }
        return []
    }

    func fetchCars() async -> [Car] {
        if appState.isConnected {
            await MainActor.run {
                isLoading = true
            }
            do {
                if let storageUser = dataStorage.gettingUser {
                    let response = try await helper.getCars(
                        accessToken: storageUser.accessToken,
                        refreshToken: storageUser.refreshToken
                    )
                    switch onEnum(of: response) {
                    case .crmData(let success):
                        if let cars = success.data {
                            await MainActor.run {
                                self.isLoading = false
                            }
                            return convertNSArrayToArray(nsArray: cars)
                        }
                    case .crmError(let error):
                        print(error.status?.value ?? "Empty error")
                    case .crmLoading:
                        print("loading...")
                    }
                }
            } catch {
                print(error)
            }
            await MainActor.run {
                isLoading = false
            }
        }
        return []
    }

    func fetchPlaces() async -> [Place] {
        if appState.isConnected {
            await MainActor.run {
                isLoading = true
            }
            do {
                await getAuthIfNeed()
                if let storageUser = dataStorage.gettingUser {
                    let response = try await helper.getPlaces(
                        accessToken: storageUser.accessToken,
                        refreshToken: storageUser.refreshToken
                    )
                    switch onEnum(of: response) {
                    case .crmData(let success):
                        print(success.data ?? "Empty data")
                        if let places = success.data {
                            return convertNSArrayToArray(nsArray: places)
                        }
                    case .crmError(let error):
                        print(error.status?.value ?? "Empty error")
                    case .crmLoading:
                        print("loading...")
                    }
                }
            } catch {
                print(error)
            }
            await MainActor.run {
                isLoading = false
            }
        }
        return []
    }

    func createBidToReserving(for bidCreateParams: BidCreateParams) async -> BidCreateDTO? {
        if appState.isConnected {
            await MainActor.run {
                isLoading = true
            }
            if let storageUser = dataStorage.gettingUser {
                do {
                    let response = try await helper.createBid(
                        accessToken: storageUser.accessToken,
                        refreshToken: storageUser.refreshToken,
                        bidCreateParams: bidCreateParams)
                    switch onEnum(of: response) {
                    case .crmData(let success):
                        if let bidCreateDTO = success.data {
                            return bidCreateDTO
                        }
                    case .crmError(let error):
                        print(error.status?.value ?? "Empty error")
                    case .crmLoading:
                        print("loading...")
                    }
                } catch {
                    print(error)
                }
            }
            await MainActor.run {
                isLoading = false
            }
        }
        return nil
    }

    func fetchServices() async -> [ServicesDTO] {
        if appState.isConnected {
            await MainActor.run {
                isLoading = true
            }
            do {
                if let storageUser = dataStorage.gettingUser {
                    let response = try await helper.getServices(
                        accessToken: storageUser.accessToken,
                        refreshToken: storageUser.refreshToken
                    )
                    switch onEnum(of: response) {
                    case .crmData(let success):
                        if let services = success.data {
                            return convertNSArrayToArray(nsArray: services)
                        }
                    case .crmError(let error):
                        print(error.status?.value ?? "Empty error")
                    case .crmLoading:
                        print("loading...")
                    }
                }
            } catch {
                print(error)
            }
            await MainActor.run {
                isLoading = false
            }
        }
        return []
    }

    func fetchUserOrders() async -> [Order] {
        if appState.isConnected {
            await MainActor.run {
                isLoading = true
            }
            do {
                await getAuthIfNeed()
                if let storageUser = dataStorage.gettingUser {
                    let orders = try await helper.getOrders(
                        userId: storageUser.id,
                        accessToken: storageUser.accessToken,
                        refreshToken: storageUser.refreshToken,
                        phone: storageUser.phone
                    )
                    return orders
                }
            } catch {
                print(error)
            }
            await MainActor.run {
                isLoading = false
            }
        }
        return []
    }

    func updateOrderStatus(userId: Int32, orderId: Int32, status: String) async {
        if appState.isConnected {
            await MainActor.run {
                isLoading = true
            }
            do {
                try await helper.updateStatus(userId: userId, orderId: orderId, status: status)
            } catch {
                print(error)
            }
            await MainActor.run {
                isLoading = false
            }
        }
    }

    func fetchBidCost(for bidCostParams: BidCostParams) async -> Bid? {
        do {
            if let storageUser = dataStorage.gettingUser {
                let response = try await helper.getBidCost(
                    accessToken: storageUser.accessToken,
                    refreshToken: storageUser.refreshToken,
                    bidCostParams: bidCostParams
                )
                switch onEnum(of: response) {
                case .crmData(let success):
                    print(success.data ?? "Empty data")
                    if let bidCost = success.data {
                        return bidCost
                    }
                case .crmError(let error):
                    print(error.status?.value ?? "Empty error")
                case .crmLoading:
                    print("loading...")
                }
            }
        } catch {
            print(error)
        }
        return nil
    }

    private func getAuthIfNeed() async {
        if let storageUser = dataStorage.gettingUser, storageUser.id < 0 &&
            storageUser != User.companion.empty() {
            await setUserTokens()
            let supaUser = await getSupaUser(pass: storageUser.password, phone: storageUser.phone.cleanUp())
            await MainActor.run {
                dataStorage.gettingUser = supaUser
            }
        }
    }

    private func convertNSArrayToArray<T>(nsArray: NSArray) -> [T] {
        var itemList: [T] = []

        for item in nsArray {
            if let item = item as? T {
                itemList.append(item)
            } else {
                print("Element is not of type T: \(item)")
            }
        }
        return itemList
    }
}
// swiftlint:enable type_body_length

final class AuthManager: ObservableObject {
    static let shared = AuthManager()
    @Published var isAuthenticated = false
    @Published var errorType: InputErrorType?
    private var dataStorage = KMPDataStorage.shared
    private var apiManager = KMPApiManager.shared
    private var cancellables = Set<AnyCancellable>()

    init() {
        dataStorage.$gettingUser
            .receive(on: DispatchQueue.main)
            .sink { _ in
                self.handleUserChange()
            }
            .store(in: &cancellables)
        Task {
            if let user = dataStorage.gettingUser,
               !user.phone.isEmpty && !user.password.isEmpty {
                await login(phone: user.phone, pass: user.password)
            }
        }
        handleUserChange()
    }

    @MainActor
    func signUp(fullname: String, phone: String, password: String) async {
        let suchPhoneAlreadyExists = await apiManager.isPhoneAlreadyExists(phone.cleanUp())
        if let suchPhoneAlreadyExists = suchPhoneAlreadyExists, !suchPhoneAlreadyExists {
            await apiManager.setUserTokens()
            await apiManager.regNewUser(fullname: fullname, phone: phone, password: password)
            await login(phone: phone, pass: password)
        } else {
            errorType = .alreadyExistsPhone
        }
    }

    @MainActor
    func login(phone: String, pass: String) async {
        let suchPhoneAlreadyExists = await apiManager.isPhoneAlreadyExists(phone.cleanUp())
        if let suchPhoneAlreadyExists = suchPhoneAlreadyExists, suchPhoneAlreadyExists {
            let supaUser = await apiManager.getSupaUser(pass: pass, phone: phone.cleanUp())
            if let supaUser = supaUser, supaUser.id >= 0 {
                dataStorage.gettingUser = supaUser
                let savingUser = User(
                    id: supaUser.id,
                    fullName: supaUser.fullName,
                    password: pass,
                    accessToken: supaUser.accessToken,
                    refreshToken: supaUser.refreshToken,
                    phone: phone
                )
                do {
                    try await dataStorage.editUserData(savingUser)
                } catch {
                    print(error)
                }
                self.handleUserChange()
            } else {
                self.errorType = .incorrectPass
            }
        } else {
            self.errorType = .notExistsPhone
        }
    }

    @MainActor
    func logout() async {
        await dataStorage.cleanLocalDataStorage()
    }

    private func handleUserChange() {
        self.isAuthenticated = dataStorage.gettingUser != User.companion.empty() &&
        dataStorage.gettingUser != nil && dataStorage.gettingUser?.fullName != ""
    }
}
