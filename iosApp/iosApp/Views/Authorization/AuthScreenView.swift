//
//  AuthScreenView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 09.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct AuthScreenView: View {
    @State private var phoneFieldText = ""
    @State private var phoneIsValid: Bool = false
    @State private var checkboxSelected: Bool = false
    @State private var isButtonEnabled: Bool = false
    @ObservedObject private var viewModel = AuthViewModel()
    
    @Environment(\.dismiss) private var dismiss
    
    var body: some View {
        VStack(spacing: AuthAndRegScreensConfig.spacingBetweenGroupAndCheckbox) {
            VStack(spacing: AuthAndRegScreensConfig.spacingBetweenComponents) {
                BorderedTextField(fieldContent: $phoneFieldText, isValid: $phoneIsValid, fieldType: .phone, inputErrorType: $viewModel.inputError)
                    .onChange(of: phoneIsValid) { _ in
                        toggleButtonEnabled()
                    }
                
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
            CustomToolbar(title: R.string.localizable.authScreenTitle)
        }
    }
    
    private func toggleButtonEnabled() {
        isButtonEnabled = phoneIsValid && checkboxSelected
    }
}

#Preview {
    AuthScreenView()
}
