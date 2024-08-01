//
//  PersonalDataViewModel.swift
//  iosApp
//
//  Created by Sergey Ivanov on 17.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Combine
import shared

final class PersonalDataViewModel: ObservableObject {
    @Published var dataHasBeenSaved: Bool = false
    @Published var dataIsEditing: Bool = false
    @Published var showErrorAlert: Bool = false
    @Published var isLoading: Bool = false
    @Published var fullname = ""
    @Published var password = ""
    @Published var phone = ""
    @Published var isFullnameValid = false
    @Published var isPhoneValid = false
    @Published var isPasswordLengthValid = false
    @Published var isPasswordHasCapitalLetter = false
    @Published var isPasswordHasSpecSymbol = false
    @Published var isPasswordValid = false
    @Published var inputError: InputErrorType? = nil
    @Published var oldPassword: String = KMPDataStorage.defaultUser.password
    @Published var newUserData: User = User.companion.empty()
    @ObservedObject var dataStorage = KMPDataStorage()

    private var cancellableSet: Set<AnyCancellable> = []

    init() {
        self.fullname = dataStorage.gettingUser.fullName
        self.phone = dataStorage.gettingUser.phone
        self.oldPassword = dataStorage.gettingUser.password
        
        dataStorage.$gettingUser
            .sink { newValue in
                self.fullname = newValue.fullName
                self.phone = newValue.phone
                self.oldPassword = newValue.password
            }
            .store(in: &cancellableSet)
        
        $fullname
            .receive(on: RunLoop.main)
            .map { fullname in
                let pattern = textRegex
                if let _ = fullname.range(of: pattern, options: .regularExpression) {
                    return true
                } else {
                    if !fullname.isEmpty {
                        self.inputError = .incorrectFullName
                    }
                    return false
                }
            }
            .assign(to: \.isFullnameValid, on: self)
            .store(in: &cancellableSet)
        
        $phone
            .receive(on: RunLoop.main)
            .map { phone in
                let pattern = phoneRegex
                if let _ = phone
                            .replacingOccurrences(of: "(", with: "")
                            .replacingOccurrences(of: ")", with: "")
                            .range(of: pattern, options: .regularExpression)
                {
                    return true
                } else {
                    if !phone.isEmpty {
                        self.inputError = .incorrectPhone
                    }
                    return false
                }
            }
            .assign(to: \.isPhoneValid, on: self)
            .store(in: &cancellableSet)
        
        $password
            .receive(on: RunLoop.main)
            .map { password in
                return !password.isEmpty ? password.count >= 8 : false
            }
            .assign(to: \.isPasswordLengthValid, on: self)
            .store(in: &cancellableSet)

        $password
            .receive(on: RunLoop.main)
            .map { password in
                let pattern = "[A-Z]"
                if let _ = password.range(of: pattern, options: .regularExpression) {
                    return true
                } else {
                    return false
                }
            }
            .assign(to: \.isPasswordHasCapitalLetter, on: self)
            .store(in: &cancellableSet)
        
        $password
            .receive(on: RunLoop.main)
            .map { password in
                let pattern = "[!@#$%^+-=&*(),.?\":{}|<>]"
                if let _ = password.range(of: pattern, options: .regularExpression) {
                    return true
                } else {
                    return false
                }
            }
            .assign(to: \.isPasswordHasSpecSymbol, on: self)
            .store(in: &cancellableSet)

        Publishers.CombineLatest3($isPasswordLengthValid, $isPasswordHasCapitalLetter, $isPasswordHasSpecSymbol)
            .receive(on: RunLoop.main)
            .map { (isPasswordLengthValid, isPasswordHasCapitalLetter, isPasswordHasSpecSymbol) in
                if !isPasswordLengthValid {
                    self.inputError = .tooShortPass
                    return false
                } else if !isPasswordHasCapitalLetter {
                    self.inputError = .noUpperLettersInPass
                    return false
                } else if !isPasswordHasSpecSymbol {
                    self.inputError = .noSpecSymboldsInPass
                    return false
                } else { return true }
            }
            .assign(to: \.isPasswordValid, on: self)
            .store(in: &cancellableSet)
    }
    
    func saveEditedUserData() {
        isLoading = true
        let currentUserData = dataStorage.gettingUser
        let newUserData = User(
            fullName: currentUserData.fullName != fullname && !fullname.isEmpty ? fullname : currentUserData.fullName,
            password: currentUserData.password != password && !password.isEmpty ? password : currentUserData.password,
            token: currentUserData.token,
            phone: currentUserData.phone != phone && !phone.isEmpty ? phone : currentUserData.phone,
            email: currentUserData.email
        )
        
        Task {
            do {
                try await self.dataStorage.editUserData(newUserData)
                
                self.dataHasBeenSaved.toggle()
            } catch {
                print(error)
                self.showErrorAlert = true
            }
        }
        self.isLoading.toggle()
    }
    
    func loadUser() {
        isLoading = true
        
        Task.detached {
            do {
               try await self.dataStorage.getUser()
            } catch {
                print(error)
            }
        }
    }
    
    private func areAllFieldsFilled() -> Bool {
        !fullname.isEmpty && !password.isEmpty
    }
    
    func fieldsIsValid() -> Bool {
        isPasswordValid && isFullnameValid && areAllFieldsFilled()
    }
}
