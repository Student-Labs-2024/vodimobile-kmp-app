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
    private let newUser = User(
        fullName: "test testov",
        password: "12344321",
        accessToken: "",
        refreshToken: "",
        expires: 19223423,
        phone: "+79029994148",
        email: "rele@df.df"
    )
    static let defaultUser = User.companion.empty()
    
    @Published var gettingUser: User = KMPDataStorage.defaultUser

    func editUserData() async {
        try? await repository.editUserData(user: newUser)
    }
    
    @MainActor
    func getUser() async {
        for await flowUser in repository.getUserData() {
            self.gettingUser = flowUser
        }
    }
}
