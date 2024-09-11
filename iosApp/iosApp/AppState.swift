//
//  AppState.swift
//  iosApp
//
//  Created by Sergey Ivanov on 20.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

final class AppState: ObservableObject {
    @Published var isInternetErrorVisible: Bool = false
    @Published var isConnected: Bool = true
    static let shared = AppState()

    init() {
        Task {
            await MainActor.run {
                checkConnectivity()
            }
        }
    }

    @MainActor
    func checkConnectivity() {
        self.isConnected = NetworkMonitor.shared.isConnected
        if !self.isConnected {
            self.isInternetErrorVisible = true
        } else {
            self.isInternetErrorVisible = false
        }
    }
}
