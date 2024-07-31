//
//  RegistrationScreenView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 09.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct RegistrationScreenView: View {
    @State private var checkboxSelected = false
    @State private var isButtonEnabled: Bool = false
    @ObservedObject private var viewModel = RegistrationViewModel()
    
    @Environment(\.dismiss) private var dismiss
    
    var body: some View {
        VStack(spacing: AuthAndRegScreensConfig.spacingBetweenGroupAndCheckbox) {
            VStack(spacing: AuthAndRegScreensConfig.spacingBetweenComponents) {
                BorderedTextField(
                    fieldContent: $viewModel.fullname,
                    isValid: $viewModel.isFullnameValid,
                    fieldType: .fullName,
                    inputErrorType: $viewModel.inputError
                )
                .onChange(of: viewModel.fullname) { _ in
                    toggleButtonEnabled()
                }
                
                BorderedTextField(
                    fieldContent: $viewModel.phone,
                    isValid: $viewModel.isPhoneValid,
                    fieldType: .phone,
                    inputErrorType: $viewModel.inputError
                )
                .onChange(of: viewModel.phone) { _ in
                    toggleButtonEnabled()
                }
                
                BorderedTextField(
                    fieldContent: $viewModel.password,
                    isValid: $viewModel.isPasswordValid,
                    fieldType: .password,
                    inputErrorType: $viewModel.inputError
                )
                .onChange(of: viewModel.password) { _ in
                    toggleButtonEnabled()
                }
                
                NavigationLink(destination: PinCodeView(phoneNumber: $viewModel.phone)) {
                    Text(R.string.localizable.nextBtnName)
                }
                .buttonStyle(FilledBtnStyle())
                .disabled(!isButtonEnabled)
            }
            
            HStack(spacing: spacingBetweenCheckboxAndText) {
                CheckboxView(isChecked: $checkboxSelected)
                    .onChange(of: checkboxSelected) { _ in
                        toggleButtonEnabled()
                    }
                
                VStack(alignment: .leading) {
                    Text(R.string.localizable.conditionText)
                        .font(.paragraph5)
                        .foregroundStyle(Color(R.color.grayDarkColor))
                    
                    NavigationLink(destination: ConditionScreenView()) {
                        Text(R.string.localizable.conditionLink)
                            .foregroundColor(Color(R.color.blueColor))
                            .font(.buttonCheckBox)
                    }
                }
                Spacer()
            }
            Spacer()
        }
        .padding(.horizontal, horizontalPadding)
        .padding(.top, аuthScreencontentTopPadding)
        .navigationBarBackButtonHidden()
        .toolbar {
            CustomToolbar(title: R.string.localizable.regScreenTitle)
        }
    }
    
    private func toggleButtonEnabled() {
        isButtonEnabled = viewModel.isFullnameValid &&
        viewModel.isPhoneValid &&
        viewModel.isPasswordValid &&
        checkboxSelected
    }
}

enum InputErrorType: String {
    case incorrectFullName
    case incorrectPhone
    case alreadyExistsPhone
    case incorrectPass
    case invalidPass
    case tooShortPass
    case noSpecSymboldsInPass
    case noUpperLettersInPass
    
    var localizedString: String {
        switch self {
        case .incorrectFullName:
            return R.string.localizable.incorrectFullName()
        case .incorrectPhone:
            return R.string.localizable.incorrectPhone()
        case .alreadyExistsPhone:
            return R.string.localizable.alreadyExistsPhone()
        case .incorrectPass:
            return R.string.localizable.incorrectPass()
        case .tooShortPass:
            return R.string.localizable.tooShortPass()
        case .noSpecSymboldsInPass:
            return R.string.localizable.noSpecSymboldsInPass()
        case .noUpperLettersInPass:
            return R.string.localizable.noUpperLettersInPass()
        case .invalidPass:
            return R.string.localizable.invalidPass()
        }
    }
}

#Preview {
    RegistrationScreenView()
}
