//
//  PersonDataView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 11.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct PersonDataView: View {
    @ObservedObject private var viewModel: PersonalDataViewModel
    @FocusState private var focusedField: Field?
    
    enum Field {
        case fullname, email, phone
    }
    
    init(viewModel: PersonalDataViewModel = .init()) {
        self.viewModel = viewModel
    }
    
    var body: some View {
        VStack {
            VStack(alignment: .leading, spacing: 25) {
                Text(R.string.localizable.yourDataText).font(.header3)
                
                UnderlineTextField(text: $viewModel.userInput.fullname, isValid: $viewModel.userInput.fullnameIsValid, fieldType: .fullName)
                    .focused($focusedField, equals: .fullname)
                UnderlineTextField(text: $viewModel.userInput.email, isValid: $viewModel.userInput.emailIsValid, fieldType: .email)
                    .focused($focusedField, equals: .email)
                    .textInputAutocapitalization(.never)
                UnderlineTextField(text: $viewModel.userInput.phone, isValid: $viewModel.userInput.phoneIsValid, fieldType: .phone)
                    .focused($focusedField, equals: .phone)
            }
            .padding(.horizontal, 32)
            .padding(.vertical, 40)
            .background(Color.white)
            .cornerRadius(20)
            .padding(.horizontal, 16)
            .padding(.top, 50)
            
            Spacer()
        }
        .onChange(of: $viewModel.userInput.wrappedValue) { _ in
            $viewModel.dataIsEditing.wrappedValue = true
        }
        .loadingOverlay(isLoading: $viewModel.isLoading)
        .alert(R.string.localizable.alertErrorSavingTitle(), isPresented: $viewModel.showAlert) {
            Button(R.string.localizable.closeButton(), role: .cancel) {
                $viewModel.showAlert.wrappedValue.toggle()
            }
        } message: {
            Text(R.string.localizable.alertErrorSavingText)
        }
        .alert(R.string.localizable.alertSavePersonDataTitle(), isPresented: $viewModel.dataHasBeenSaved) {
            Button(R.string.localizable.closeButton(), role: .cancel) {
                $viewModel.dataHasBeenSaved.wrappedValue.toggle()
            }
        }
        .background(Color(R.color.grayLightColor).ignoresSafeArea())
        .navigationBarBackButtonHidden()
        .toolbar {
            CustomToolbar(
                title: R.string.localizable.profileScreenTitle,
                trailingToolbarItem: TrailingToolbarItem(
                    image: Image.checkmark,
                    observedObject: viewModel,
                    actionAfterTapping: {
                        viewModel.makeSavingDataRequest()
                        viewModel.dataIsEditing = false
                        focusedField = nil
                    }
                )
            )
        }
    }
}

#Preview {
    PersonDataView()
}
