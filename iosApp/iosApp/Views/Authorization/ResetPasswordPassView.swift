//
//  ResetPasswordPassView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 01.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ResetPasswordPassView: View {
    @State private var checkboxSelected: Bool = false
    @State private var isButtonEnabled: Bool = false
    @ObservedObject private var viewModel = ResetPasswordViewModel()
    
    @Environment(\.dismiss) private var dismiss
    
    var body: some View {
        VStack(spacing: AuthAndRegScreensConfig.spacingBetweenGroupAndCheckbox) {
            VStack(spacing: AuthAndRegScreensConfig.spacingBetweenComponents) {
                    BorderedTextField(
                        fieldContent: $viewModel.password,
                        isValid: $viewModel.isPasswordValid,
                        fieldType: .password,
                        inputErrorType: $viewModel.inputError
                    )
                    .onChange(of: viewModel.isPasswordValid) { _ in
                        toggleButtonEnabled()
                    }
                
                NavigationLink(destination: MainTabbarView()) {
                    Text(R.string.localizable.saveButton)
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
            CustomToolbar(title: R.string.localizable.resetPassScreenTitle)
        }
    }
    
    private func toggleButtonEnabled() {
        isButtonEnabled = viewModel.isPasswordValid && checkboxSelected
    }
}

#Preview {
    ResetPasswordPassView()
}

