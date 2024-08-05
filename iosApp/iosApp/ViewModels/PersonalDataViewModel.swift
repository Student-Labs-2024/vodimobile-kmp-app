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
    @Published var dataIsEditing: Bool = false
    @Published var showErrorAlert: Bool = false
    @Published var isLoading: Bool = false
    @ObservedObject var dataStorage = KMPDataStorage()
    
    func makeFakeNetworkRequest() {
        isLoading = true
        
        DispatchQueue.main.asyncAfter(deadline: .now() + 3) {
            self.isLoading = false
            self.showErrorAlert = true
        }
    }
    
    func saveEditedUserData() {
        isLoading = true
        let newUserData = User(
            fullName: userInput.fullname,
            password: userInput.password,
            accessToken: dataStorage.gettingUser.accessToken,
            refreshToken: dataStorage.gettingUser.refreshToken,
            expires: dataStorage.gettingUser.expires,
            phone: userInput.phone,
            email: userInput.email
        )
        
        Task {
//            do {
//                try await self.dataStorage.editUserData(newUserData)
//            } catch {
//                self.showErrorAlert.toggle()
//            }
            
            DispatchQueue.main.async {
                self.isLoading.toggle()
                self.dataHasBeenSaved.toggle()
            }
        }
    }
}

struct UserInputData: Equatable {
    var fullname = ""
    var email = ""
    var phone = ""
    var password = ""
    var phoneIsValid: Bool = false
    var fullnameIsValid: Bool = false
    var emailIsValid: Bool = false
    
    private func areAllFieldsFilled() -> Bool {
        !fullname.isEmpty && !email.isEmpty && !phone.isEmpty
    }
    
    func fieldsIsValid() -> Bool {
        phoneIsValid && emailIsValid && fullnameIsValid && areAllFieldsFilled()
    }
}
