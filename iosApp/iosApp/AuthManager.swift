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
    private var user: User?
    // TODO: - Make a single datastorage state oject in app
    
    func signUp(userData: User) {
        self.user = userData
        self.isAuthenticated = true
    }
    
    func login(user: User) {
        self.user = user
        self.isAuthenticated = true
    }
    
    func logout() {
        self.user = nil
        self.isAuthenticated = false
    }
}

