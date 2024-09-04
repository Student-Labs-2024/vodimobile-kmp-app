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
    static let shared = UserDataViewModel()
    // fields content
    @Published var fullnameField = ""
    @Published var passwordField = ""
    @Published var phoneField = ""
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
    @Published var phoneNotExist: Bool = false
    // data storage
    @ObservedObject var dataStorage = KMPDataStorage.shared
    @ObservedObject var apiManager = KMPApiManager.shared
    // observable set
    private var cancellableSet: Set<AnyCancellable> = []

    init() {
        fetchUserData()

        dataStorage.$gettingUser
            .receive(on: DispatchQueue.main)
            .sink { [weak self] newValue in
                if let storageUser = newValue {
                    self?.oldStoragedPassword = storageUser.password
                    self?.fullname = storageUser.fullName
                    self?.phone = self?.formatPhoneNumber(storageUser.phone) ?? ""
                }
            }
            .store(in: &cancellableSet)

        $fullnameField
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

        $phoneField
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

    func checkExistenceOfPhone(_ phone: String) async {
        let isPhoneExist = await apiManager.isPhoneAlreadyExists(phone)
        if let isPhoneExist = isPhoneExist {
            await MainActor.run {
                phoneNotExist = !isPhoneExist
            }
        }
    }

    func saveEditedUserData() {
        if let storageUser = dataStorage.gettingUser {
            let newUserData = User(
                id: storageUser.id,
                fullName: storageUser.fullName != fullnameField && !fullnameField.isEmpty ? fullnameField : storageUser.fullName,
                password: storageUser.password != passwordField && !passwordField.isEmpty ? passwordField : storageUser.password,
                accessToken: storageUser.accessToken,
                refreshToken: storageUser.refreshToken,
                phone: storageUser.phone != phoneField && !phoneField.isEmpty ? phoneField : storageUser.phone
            )

            Task {
                do {
                    _ = await apiManager.changeUserFullname(newFullname: fullnameField)
                    try await self.dataStorage.editUserData(newUserData)
                    self.dataHasBeenSaved.toggle()
                } catch {
                    print(error)
                    self.showErrorAlert = true
                }
            }
        }
    }

    @MainActor
    func fetchUserData() {
        Task {
            await MainActor.run {
                isLoading = true
            }
            await self.dataStorage.getUser()
            if let storageUser = dataStorage.gettingUser {
                await MainActor.run {
                    fullname = storageUser.fullName
                    phone = storageUser.phone
                    oldStoragedPassword = storageUser.password
                }
            }
            await MainActor.run {
                isLoading = false
            }
        }
    }

    func changePassword(to newPassword: String, isResetPassFlow: Bool = false, phone: String = "") async {
        var passChanged = false
        await MainActor.run {
            isLoading = true
        }

        if !isResetPassFlow {
            if comparePasswords() {
                passChanged = await apiManager.changeUserPassword(newPassword: newPassword)
            }
        } else {
            passChanged = await apiManager.changeUserPassword(newPassword: newPassword, isResetPassFlow: true, phone: phone)
        }
        await MainActor.run {
            dataHasBeenSaved = passChanged
            isLoading = false
        }
    }

    func comparePasswords() -> Bool {
        if oldStoragedPassword == oldPassword && isPasswordValid {
            return true
        } else {
            inputError = .oldPasswordIsWrong
        }
        return false
    }

    func cleanAllFields() {
        fullnameField = ""
        passwordField = ""
        phoneField = ""
    }

    func fieldsIsValid() -> Bool {
        isPasswordValid && isFullnameValid && areAllFieldsFilled()
    }

    private func areAllFieldsFilled() -> Bool {
        !fullnameField.isEmpty && !passwordField.isEmpty
    }

    private func handlePhoneString(_ phone: String, pattern: String) -> Range<String.Index>? {
        phone
            .replacingOccurrences(of: "(", with: "")
            .replacingOccurrences(of: ")", with: "")
            .range(of: pattern, options: .regularExpression)
    }

    private func formatPhoneNumber(_ phoneNumber: String) -> String {
        let digits = phoneNumber.filter { $0.isNumber }
        guard digits.count == 11, digits.hasPrefix("7") else { return phoneNumber }

        let formatted = "+\(digits.prefix(1)) \(digits.dropFirst(1).prefix(3)) \(digits.dropFirst(4).prefix(3))-\(digits.dropFirst(7).prefix(2))-\(digits.dropFirst(9))"

        return formatted
    }

    private func observeToPassword() {
        $passwordField
            .receive(on: RunLoop.main)
            .map { password in
                return !password.isEmpty ? password.count >= 8 : false
            }
            .assign(to: \.isPasswordLengthValid, on: self)
            .store(in: &cancellableSet)

        $passwordField
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

        $passwordField
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
