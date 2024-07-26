//
//  PersonalDataViewModel.swift
//  iosApp
//
//  Created by Sergey Ivanov on 17.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

final class PersonalDataViewModel: ObservableObject {
    @Published var userInput: UserInputData = UserInputData()
    @Published var dataHasBeenSaved: Bool = false
    @Published var dataIsEditing: Bool = false
    @Published var showAlert: Bool = false
    @Published var isLoading: Bool = false
    
    func makeFakeNetworkRequest() {
        isLoading = true
        
        DispatchQueue.main.asyncAfter(deadline: .now() + 3) {
            self.isLoading = false
            self.showAlert = true
        }
    }
    
    func makeSavingDataRequest() {
        isLoading = true
        
        DispatchQueue.main.asyncAfter(deadline: .now() + 3) {
            self.isLoading = false
            self.dataHasBeenSaved = true
        }
    }
    
    
}

struct UserInputData: Equatable {
    var fullname = ""
    var email = ""
    var phone = ""
    var phoneIsValid: Bool = false
    var fullnameIsValid: Bool = false
    var emailIsValid: Bool = false
    
    private func areAllFieldsFilled() -> Bool {
        !fullname.isEmpty && !email.isEmpty && !phone.isEmpty
    }
    
    func fieldsIsValid() -> Bool {
        phoneIsValid && emailIsValid && fullnameIsValid && areAllFieldsFilled()
    }
    
    static func ==(lhs: UserInputData, rhs: UserInputData) -> Bool {
        lhs.fullname == rhs.fullname &&
        lhs.email == rhs.email &&
        lhs.phone == rhs.phone
    }
}
