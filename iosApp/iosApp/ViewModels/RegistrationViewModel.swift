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
    @Published var isPasswordCapitalLetter = false
    @Published var isPasswordSpecSymbol = false
    @Published var isPasswordVisible = false
    @Published var isPasswordValid = false

    private var cancellableSet: Set<AnyCancellable> = []

    init() {
        $fullname
            .receive(on: RunLoop.main)
            .map { password in
                let pattern = textRegex
                if let _ = password.range(of: pattern, options: .regularExpression) {
                    return true
                } else {
                    return false
                }
            }
            .assign(to: \.isFullnameValid, on: self)
            .store(in: &cancellableSet)

        $password
            .receive(on: RunLoop.main)
            .map { password in
                return password.count >= 8
            }
            .assign(to: \.isPasswordLengthValid, on: self)
            .store(in: &cancellableSet)

        $password
            .receive(on: RunLoop.main)
            .map { password in
                let pattern = passRegex
                if let _ = password.range(of: pattern, options: .regularExpression) {
                    return true
                } else {
                    return false
                }
            }
            .assign(to: \.isPasswordCapitalLetter, on: self)
            .store(in: &cancellableSet)
        
        $phone
            .receive(on: RunLoop.main)
            .map { phone in
                let pattern = phoneRegex
                if let _ = phone.range(of: pattern, options: .regularExpression) {
                    return true
                } else {
                    return false
                }
            }
            .assign(to: \.isPhoneValid, on: self)
            .store(in: &cancellableSet)
        
        $password
            .receive(on: RunLoop.main)
            .map { password in
                let pattern = "[!@#$%^&*(),.?\":{}|<>]"
                if let _ = password.range(of: pattern, options: .regularExpression) {
                    return true
                } else {
                    return false
                }
            }
            .assign(to: \.isPasswordSpecSymbol, on: self)
            .store(in: &cancellableSet)

        Publishers.CombineLatest3($isPasswordLengthValid, $isPasswordCapitalLetter, $isPasswordSpecSymbol)
            .receive(on: RunLoop.main)
            .map { (isPasswordLengthValid, isPasswordCapitalLetter, isPasswordSpecSymbol) in
                return isPasswordLengthValid && isPasswordCapitalLetter && isPasswordSpecSymbol
            }
            .assign(to: \.isPasswordValid, on: self)
            .store(in: &cancellableSet)
    }
}
