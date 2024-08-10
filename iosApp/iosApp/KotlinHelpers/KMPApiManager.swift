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
                   let newUser = User(
                        fullName:  dataStorage.gettingUser.fullName,
                        password:  dataStorage.gettingUser.password,
                        accessToken:  user.accessToken,
                        refreshToken:  user.refreshToken,
                        expires:  user.expires,
                        phone:  dataStorage.gettingUser.phone,
                        email:  dataStorage.gettingUser.email
                    )
                    DispatchQueue.main.async {
                        self.dataStorage.gettingUser = newUser
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
            let response = try await helper.getCars(
                accessToken: dataStorage.gettingUser.accessToken,
                refreshToken: dataStorage.gettingUser.refreshToken
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
        } catch {
            print(error)
        }
        return []
    }
    
    private func convertNSArrayToArray(nsArray: NSArray) -> [Car] {
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
