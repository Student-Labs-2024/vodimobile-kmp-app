//
//  RegistrationViewModel.swift
//  iosApp
//
//  Created by Sergey Ivanov on 26.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Combine

class RegistrationViewModel: ObservableObject {
    // Ввод
    @Published var fullname = ""
    @Published var phone = ""
    @Published var password = ""

    // Вывод
    @Published var isFullnameValid = false
    @Published var isPhoneValid = false
    @Published var isPasswordLengthValid = false
    @Published var isPasswordHasCapitalLetter = false
    @Published var isPasswordHasSpecSymbol = false
    @Published var isPasswordValid = false
    @Published var inputError: InputErrorType? = nil

    private var cancellableSet: Set<AnyCancellable> = []

    init() {
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
                return password.count >= 8 && !password.isEmpty
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
                let pattern = "[!@#$%^&+-=*(),.?\":{}|<>]"
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
}
