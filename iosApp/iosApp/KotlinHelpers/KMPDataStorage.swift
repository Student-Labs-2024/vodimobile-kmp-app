//
//  UserDataStorage.swift
//  iosApp
//
//  Created by Sergey Ivanov on 23.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared
import SwiftUI

@MainActor
final class KMPDataStorage: ObservableObject {
    private let repository = UserDataStoreRepositoryImpl(dataStore: CreateDataStore_iosKt.createDataStore())
    private let newUser = User(
        fullName: "test testov",
        password: "12344321",
        token: "token_test",
        phone: "+79029994148",
        email: "rele@df.df"
    )
    static let defaultUser = User.companion.empty()
    @Published var gettingUser = defaultUser

    func editUserData(_ userData: User) async throws {
        try await repository.editUserData(user: userData)
    }
    
//    func getUser() async {
//        for await userFromFlow in repository.getUserData() {
//            self.gettingUser = userFromFlow
//        }
//    }
}
