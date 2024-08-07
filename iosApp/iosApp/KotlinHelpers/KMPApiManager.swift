//
//  KMPApiManager.swift
//  iosApp
//
//  Created by Sergey Ivanov on 06.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

final class KMPApiManager {
    private var helper = KoinHelper()
    private var dataStorage = KMPDataStorage()
    static let shared = KMPApiManager()
    private var tokens: (String, String) = ("","")
    
    init() {
        setUserTokens()
    }
    
    func setUserTokens() {
        Task {
            do {
                let response = try await helper.postUser()
                switch onEnum(of: response) {
                case .crmData(let success):
                    let user = success.data
                    if let user = user {
                        let newUser = User(
                            fullName:  dataStorage.gettingUser.fullName,
                            password:  dataStorage.gettingUser.password,
                            accessToken:  user.accessToken,
                            refreshToken:  user.refreshToken,
                            expires:  user.expires,
                            phone:  dataStorage.gettingUser.phone,
                            email:  dataStorage.gettingUser.email
                        )
                        dataStorage.gettingUser = newUser
                        tokens = (user.accessToken, user.refreshToken)
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
        do {
            let response = try await helper.getCars(
                accessToken: tokens.0,
                refreshToken: tokens.1
            )
            switch onEnum(of: response) {
            case .crmData(let success):
                print(success.data ?? "Empty data")
                if let cars = success.data {
                    return convertNSArrayToArray(nsArray: cars)
                }
            case .crmError(let error):
                print(error.status?.value ?? "Empty error")
            case .crmLoading(_):
                print("loading...")
            }
        } catch {
            print(error)
        }
        return []
    }
    
    func convertNSArrayToArray(nsArray: NSArray) -> [Car] {
        var cars: [Car] = []
        
        for item in nsArray {
            if let car = item as? Car {
                cars.append(car)
            } else {
                print("Element is not of type Car: \(item)")
            }
        }
        
        return cars
    }
}