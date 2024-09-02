//
//  UserDataStorage.swift
//  iosApp
//
//  Created by Sergey Ivanov on 23.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared
import SwiftUI

final class KMPDataStorage: ObservableObject {
    private let repository = UserDataStoreRepositoryImpl(dataStore: CreateDataStore_iosKt.createDataStore())
    @Published var gettingUser: User? = User.companion.empty()
    static let shared = KMPDataStorage()

    init() {
        Task {
            await getUser()
        }
    }

    @MainActor
    func editUserData(_ userData: User) async throws {
        try await repository.editUserData(user: userData)
    }

    func getUser() async {
        var cycleCounter = 0

        for await flowUser in repository.getUserData() {
            if flowUser != User.companion.empty() {
                await MainActor.run {
                    self.gettingUser = flowUser
                }
                break
            } else if cycleCounter >= 10 {
                cycleCounter = 0
                break
            }
        }
    }

    @MainActor
    func cleanLocalDataStorage() async {
        gettingUser = nil
        do {
            try await editUserData(User.companion.empty())
        } catch {
            print(error)
        }
    }
}
