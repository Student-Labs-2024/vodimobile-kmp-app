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
    static let shared = KMPApiManager()
    // TODO: - Make logic with appState observing
    
    init() {
        Task {
            await setUserTokens()
        }
    }
    
    func setUserTokens() async {
        do {
            let response = try await helper.postUser()
            switch onEnum(of: response) {
            case .crmData(let success):
                let user = success.data
                if let user = user {
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
    
    func fetchCars() async -> [Car] {
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
        
        return []
    }
    
    func fetchPlaces() async -> [Place] {
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
        return []
    }
    
    private func convertNSArrayToArray<T>(nsArray: NSArray) -> [T] {
        var itemList: [T] = []
        
        for item in nsArray {
            if let item = item as? T {
                itemList.append(item)
            } else {
                print("Element is not of type Car: \(item)")
            }
        }
        return itemList
    }
}
