//
//  RegistrationScreenView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 09.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct RegistrationScreenView: View {
    @State private var fullnameFieldText = ""
    @State private var fullnameIsValid = false
    @State private var phoneFieldText = ""
    @State private var phoneIsValid = false
    @State private var passFieldText = ""
    @State private var passIsValid = false
    @State private var checkboxSelected = false
    @State private var isButtonEnabled: Bool = false
    @ObservedObject private var viewModel = RegistrationViewModel()
    
    @Environment(\.dismiss) private var dismiss
    
    var body: some View {
        VStack(spacing: AuthAndRegScreensConfig.spacingBetweenGroupAndCheckbox) {
            VStack(spacing: AuthAndRegScreensConfig.spacingBetweenComponents) {
                CustomTextFieldView(
                    fieldContent: $viewModel.fullname,
                    isValid: $viewModel.isFullnameValid,
                    fieldType: .fullName
                )
                
                CustomTextFieldView(
                    fieldContent: $viewModel.phone,
                    isValid: $viewModel.isPhoneValid,
                    fieldType: .phone,
                    isPasswordVisible: $viewModel.isPasswordVisible
                )
                
                CustomTextFieldView(
                    fieldContent: $viewModel.password,
                    isValid: $viewModel.isPasswordValid,
                    fieldType: .password
                )

                NavigationLink(destination: PinCodeView(phoneNumber: $phoneFieldText)) {
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
        isButtonEnabled = fullnameIsValid && phoneIsValid && passIsValid && checkboxSelected
    }
}

#Preview {
    RegistrationScreenView()
}
