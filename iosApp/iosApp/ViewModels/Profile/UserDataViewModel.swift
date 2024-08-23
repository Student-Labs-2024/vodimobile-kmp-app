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

final class UserDataViewModel: ObservableObject {
    // fields content
    @Published var fullname = ""
    @Published var password = ""
    @Published var phone = ""
    @Published var oldPassword = ""
    private var oldStoragedPassword: String = ""
    private var newUserData: User = User.companion.empty()
    // fields validation
    @Published var isFullnameValid = false
    @Published var isPhoneValid = false
    @Published var isPasswordLengthValid = false
    @Published var isPasswordHasCapitalLetter = false
    @Published var isPasswordHasSpecSymbol = false
    @Published var isPasswordValid = false
    @Published var inputError: InputErrorType?
    // work toggles
    @Published var dataHasBeenSaved: Bool = false
    @Published var dataIsEditing: Bool = false
    @Published var showErrorAlert: Bool = false
    @Published var isLoading: Bool = false
    // data storage
    @ObservedObject var dataStorage = KMPDataStorage.shared
    // observable set
    private var cancellableSet: Set<AnyCancellable> = []

    init() {
        self.fullname = KMPDataStorage.shared.gettingUser?.fullName ?? ""
        self.password = KMPDataStorage.shared.gettingUser?.password ?? ""
        self.phone = KMPDataStorage.shared.gettingUser?.phone ?? ""

        dataStorage.$gettingUser
            .sink { newValue in
                self.fullname = newValue?.fullName ?? ""
                self.phone = newValue?.phone ?? ""
                self.password = newValue?.password ?? ""
            }
            .store(in: &cancellableSet)

        $fullname
            .receive(on: RunLoop.main)
            .map { fullname in
                let pattern = textRegex
                if fullname.range(of: pattern, options: .regularExpression) != nil {
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
                if self.handlePhoneString(phone, pattern: pattern) != nil {
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

        observeToPassword()
    }

    func saveEditedUserData() {
        isLoading = true
        if let storageUser = dataStorage.gettingUser {
            let newUserData = User(
                id: storageUser.id,
                fullName: storageUser.fullName != fullname && !fullname.isEmpty ? fullname : storageUser.fullName,
                password: storageUser.password != password && !password.isEmpty ? password : storageUser.password,
                accessToken: storageUser.accessToken,
                refreshToken: storageUser.refreshToken,
                phone: storageUser.phone != phone && !phone.isEmpty ? phone : storageUser.phone
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
        }
        self.isLoading.toggle()
    }

    func fetchUserData() {
        isLoading.toggle()

        Task {
//            await self.dataStorage.getUser()
        }
        isLoading.toggle()
    }

    func comparePasswords() -> Bool {
        oldStoragedPassword == oldPassword
        // TODO: - Make logic for comparing password, validation and saving
    }

    private func areAllFieldsFilled() -> Bool {
        !fullname.isEmpty && !password.isEmpty
    }

    func fieldsIsValid() -> Bool {
        isPasswordValid && isFullnameValid && areAllFieldsFilled()
    }

    private func handlePhoneString(_ phone: String, pattern: String) -> Range<String.Index>? {
        phone
            .replacingOccurrences(of: "(", with: "")
            .replacingOccurrences(of: ")", with: "")
            .range(of: pattern, options: .regularExpression)
    }

    private func observeToPassword() {
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
                let pattern = capitalizeSymbolRegex
                if password.range(of: pattern, options: .regularExpression) != nil {
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
                let pattern = specialSymbolRegex
                if password.range(of: pattern, options: .regularExpression) != nil {
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
