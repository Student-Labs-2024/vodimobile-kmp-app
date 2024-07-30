//
//  PersonalDataViewModel.swift
//  iosApp
//
//  Created by Sergey Ivanov on 17.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared
import Combine

final class PersonalDataViewModel: ObservableObject {
    @Published var userInput = UserInputData()
    @Published var dataHasBeenSaved: Bool = false
    @Published var dataIsEditing: Bool = false
    @Published var showErrorAlert: Bool = false
    @Published var isLoading: Bool = false
    @ObservedObject var dataStorage: KMPDataStorage

    private var cancellables = Set<AnyCancellable>()
    
    init(dataStorage: KMPDataStorage = KMPDataStorage()) {
        self.dataStorage = dataStorage
        setupBindings()
        Task {
            await loadUserData()
        }
    }
    
    private func setupBindings() {
        dataStorage.$gettingUser
            .receive(on: DispatchQueue.main)
            .sink { [weak self] user in
                self?.updateUserInput(with: user)
            }
            .store(in: &cancellables)
    }
    
    private func updateUserInput(with user: User) {
        self.userInput.email = user.email ?? ""
        self.userInput.fullname = user.fullName
        self.userInput.phone = user.phone
        self.userInput.password = user.password
    }

    @MainActor
    func loadUserData() async {
        isLoading = true
        await dataStorage.getUser()
        isLoading = false
    }
    
    func saveEditedUserData() async {
        isLoading = true
        let newUserData = User(
            fullName: userInput.fullname,
            password: userInput.password,
            token: dataStorage.gettingUser.token,
            phone: userInput.phone,
            email: userInput.email
        )
        
        do {
            try await self.dataStorage.editUserData(newUserData)
            self.isLoading = false
            self.dataHasBeenSaved = true
        } catch {
            print(error)
            self.isLoading = false
            showErrorAlert.toggle()
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
        !fullname.isEmpty && !phone.isEmpty
    }
    
    func fieldsIsValid() -> Bool {
        phoneIsValid && fullnameIsValid && areAllFieldsFilled()
    }
}
