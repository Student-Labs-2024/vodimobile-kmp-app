//
//  PersonalDataViewModel.swift
//  iosApp
//
//  Created by Sergey Ivanov on 17.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

final class PersonalDataViewModel: ObservableObject {
    @Published var userInput = UserInputData()
    @Published var dataHasBeenSaved: Bool = false
    @Published var showAlert: Bool = false
    @Published var isLoading: Bool = false
    
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
    
    func checkEmpty() -> Bool {
        !self.fullname.isEmpty && !self.email.isEmpty && !self.phone.isEmpty
    }
}
