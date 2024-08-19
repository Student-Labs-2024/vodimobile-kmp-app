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
    @Binding var showSignSuggestModal: Bool
    @ObservedObject private var viewModel = UserDataViewModel()
    
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
                    inputErrorType: $viewModel.inputError,
                    isForgetBtnEnabled: false
                )
                .onChange(of: viewModel.password) { _ in
                    toggleButtonEnabled()
                }
                
                NavigationLink(
                    destination: PinCodeView(
                        phoneNumber: $viewModel.phone,
                        showSignSuggestModal: $showSignSuggestModal,
                        isResetPasswordFlow: false
                    )
                ) {
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

#Preview {
    RegistrationScreenView(showSignSuggestModal: Binding.constant(false))
}
