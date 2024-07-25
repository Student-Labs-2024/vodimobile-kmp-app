//
//  PersonalDataViewModel.swift
//  iosApp
//
//  Created by Sergey Ivanov on 17.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

final class PersonalDataViewModel: ObservableObject {
    @Published var userInput = UserInputData()
    @Published var dataHasBeenSaved: Bool = false
    @Published var showAlert: Bool = false
    @Published var isLoading: Bool = false
    @ObservedObject var dataStorage: KMPDataStorage {
        didSet {
            userInput.email = dataStorage.gettingUser.email ?? ""
            userInput.fullname = dataStorage.gettingUser.fullName
            userInput.phone = dataStorage.gettingUser.phone
            userInput.password = dataStorage.gettingUser.password
        }
    }
    
    init() {
        self.dataStorage = KMPDataStorage()
        self.userInput.email = dataStorage.gettingUser.email ?? ""
        self.userInput.fullname = dataStorage.gettingUser.fullName
        self.userInput.phone = dataStorage.gettingUser.phone
    }
    
    func saveChangedUserData() async {
        Task {
            let newUserData = User(
                fullName: userInput.fullname,
                password: userInput.password,
                token: dataStorage.gettingUser.token,
                phone: userInput.phone,
                email: userInput.email
            )
            await dataStorage.editUserData(newUserData)
        }
    }
    
    func makeFakeNetworkRequest() {
        isLoading = true
        
        DispatchQueue.main.asyncAfter(deadline: .now() + 3) {
            self.isLoading = false
            self.showAlert = true
        }
    }
}

struct UserInputData {
    var fullname = ""
    var email = ""
    var phone = ""
    var password = ""
    
    func checkEmpty() -> Bool {
        !self.fullname.isEmpty && !self.email.isEmpty && !self.phone.isEmpty
    }
}
