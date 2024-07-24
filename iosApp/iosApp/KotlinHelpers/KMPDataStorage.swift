//
//  UserDataStorage.swift
//  iosApp
//
//  Created by Sergey Ivanov on 23.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared

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

    func editUserData() {
        Task {
            try await repository.editUserData(user: newUser)
        }
    }
    
    func getUser() -> User {
        var user: User = KMPDataStorage.defaultUser
        Task {
            user = try await repository.getUserData()
        }
        return user
    }
}