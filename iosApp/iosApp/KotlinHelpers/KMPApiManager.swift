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

    func getSupaUser(pass: String, phone: String) async -> User? {
        if appState.isConnected {
            await MainActor.run {
                isLoading = true
            }
            do {
                let supaUser = try await helper.getUser(password: pass, phone: phone.cleanUp())
                return supaUser != User.companion.empty() ? supaUser : nil
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
                            phone: phone
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
            if let storageUser = dataStorage.gettingUser,
               storageUser.accessToken.isEmpty {
                await MainActor.run {
                    isLoading = true
                }
                do {
                    let response = try await helper.postUser()
                    switch onEnum(of: response) {
                    case .crmData(let success):
                        if let user = success.data {
                            if let storageUser = dataStorage.gettingUser {
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
    }

    func fetchFreeCarIdsForDate(for carFreeListParamsDTO: CarFreeListParamsDTO) async -> [Int] {
        if appState.isConnected {
            if let storageUser = dataStorage.gettingUser,
               storageUser.accessToken.isEmpty {
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

    func fetchCarFreeDateRange(carId: Int32, beginDate: Int64, endDate: Int64) async -> [(Int64, Int64)] {
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
                                isLoading = false
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
                        print(success.data ?? "Empty data")
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
        if let storageUser = dataStorage.gettingUser, storageUser.id < 0 {
            await setUserTokens()
            let supaUser = await getSupaUser(pass: storageUser.password, phone: storageUser.phone)
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
    @ObservedObject var dataStorage = KMPDataStorage.shared
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
                user == User.companion.empty() || user.id < 0 {
                await login(phone: user.phone, pass: user.password)
            }
        }
        handleUserChange()
    }

    @MainActor
    func signUp(fullname: String, phone: String, password: String) async {
        await apiManager.regNewUser(fullname: fullname, phone: phone, password: password)
        await login(phone: fullname, pass: password)
    }

    @MainActor
    func login(phone: String, pass: String) async {
        let supaUser = await apiManager.getSupaUser(pass: pass, phone: phone)
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
            self.isAuthenticated = true
        } else {
            self.errorType = .incorrectPhone
        }
    }

    @MainActor
    func logout() async {
        await dataStorage.cleanLocalDataStorage()
    }

    private func handleUserChange() {
        self.isAuthenticated = dataStorage.gettingUser != User.companion.empty() &&
        dataStorage.gettingUser != nil
    }
}
