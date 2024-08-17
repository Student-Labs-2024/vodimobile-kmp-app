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

final class KMPApiManager {
    private var helper = KoinHelper()
    @ObservedObject var dataStorage = KMPDataStorage.shared
    @ObservedObject var appState = AppState.shared
    static let shared = KMPApiManager()
    
    init() {
        appState.checkConnectivity()
        Task {
            await setUserTokens()
        }
    }
    
    func getSupaUser(pass: String, phone: String) async -> User? {
        if appState.isConnected {
            do {
                let supaUser = try await helper.getUser(password: pass, phone: phone)
                return supaUser != User.companion.empty() ? supaUser : nil
            } catch {
                print(error)
            }
        }
        return nil
    }
    
    func regNewUser(fullname: String, phone: String, password: String) async {
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
    }
    
    func setUserTokens() async {
        if appState.isConnected {
            do {
                let response = try await helper.postUser()
                switch onEnum(of: response) {
                case .crmData(let success):
                    if let user = success.data {
                        if let storageUser = dataStorage.gettingUser {
                            let newUser = User(
                                id: storageUser.id,
                                fullName:  storageUser.fullName,
                                password:  storageUser.password,
                                accessToken:  user.accessToken,
                                refreshToken:  user.refreshToken,
                                phone:  storageUser.phone
                            )
                            DispatchQueue.main.async {
                                self.dataStorage.gettingUser = newUser
                            }
                        }
                    }
                case .crmError(let error):
                    print(error.status?.value ?? "Empty error")
                case .crmLoading(_):
                    print("loading...")
                }
            } catch {
                print(error)
            }
        }
    }
    
    func fetchCars() async -> [Car] {
        if appState.isConnected {
            do {
                if let storageUser = dataStorage.gettingUser {
                    let response = try await helper.getCars(
                        accessToken: storageUser.accessToken,
                        refreshToken: storageUser.refreshToken
                    )
                    switch onEnum(of: response) {
                    case .crmData(let success):
                        if let cars = success.data {
                            return convertNSArrayToArray(nsArray: cars)
                        }
                    case .crmError(let error):
                        print(error.status?.value ?? "Empty error")
                    case .crmLoading(_):
                        print("loading...")
                    }
                }
            } catch {
                print(error)
            }
        }
        return []
    }
    
    func fetchPlaces() async -> [Place] {
        if appState.isConnected {
            do {
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
                    case .crmLoading(_):
                        print("loading...")
                    }
                }
            } catch {
                print(error)
            }
        }
        return []
    }
    
    func fetchUserOrders() async -> [Order] {
        if appState.isConnected {
            do {
                if let storageUser = dataStorage.gettingUser {
                    return try await helper.getOrders(
                        userId: storageUser.id,
                        accessToken: storageUser.accessToken,
                        refreshToken: storageUser.refreshToken
                    )
                }
            } catch {
                print(error)
            }
        }
        return []
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


final class AuthManager: ObservableObject {
    static let shared = AuthManager()
    @Published var isAuthenticated = false
    @ObservedObject var dataStorage = KMPDataStorage.shared
    private var apiManager = KMPApiManager.shared
    
    init() {
        self.isAuthenticated = dataStorage.gettingUser != User.companion.empty() &&
        dataStorage.gettingUser != nil
    }
    
    func signUp(fullname: String, phone: String, password: String) async {
        await apiManager.regNewUser(fullname: fullname, phone: phone, password: password)
        let supaUser = await apiManager.getSupaUser(pass: password, phone: phone)
        self.dataStorage.gettingUser = supaUser
        self.isAuthenticated = true
    }
    
    func login(phone: String, pass: String) async {
        let supaUser = await apiManager.getSupaUser(pass: pass, phone: phone)
        if let supaUser = supaUser {
            do {
                try await self.dataStorage.editUserData(supaUser)
            } catch {
                print(error)
            }
        }
        self.dataStorage.gettingUser = supaUser
        self.isAuthenticated = true
    }
    
    func logout() {
        self.dataStorage.gettingUser = nil
        self.isAuthenticated = false
    }
}
