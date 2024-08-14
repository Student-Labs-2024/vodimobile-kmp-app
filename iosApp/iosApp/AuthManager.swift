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
    static let shared = AuthManager()
    @Published var isAuthenticated: Bool = false
    @ObservedObject var dataStorage = KMPDataStorage.shared
    
    func signUp(userData: User) {
        self.dataStorage.gettingUser = userData
        self.isAuthenticated = true
    }
    
    func login(user: User) {
        self.dataStorage.gettingUser = user
        self.isAuthenticated = true
    }
    
    func logout() {
        self.dataStorage.gettingUser = nil
        self.isAuthenticated = false
    }
}

