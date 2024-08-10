//
//  AuthManager.swift
//  iosApp
//
//  Created by Sergey Ivanov on 11.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

final class AuthManager: ObservableObject {
    @Published var isAuthenticated: Bool = false
    @ObservedObject var dataStorage = KMPDataStorage()
    
    func signUp(userData: User) {
        dataStorage.gettingUser = userData
        self.isAuthenticated = true
    }
    
    func login(user: User) {
        dataStorage.gettingUser = user
        self.isAuthenticated = true
    }
    
    func logout() {
        dataStorage.gettingUser = nil
        self.isAuthenticated = false
    }
}

