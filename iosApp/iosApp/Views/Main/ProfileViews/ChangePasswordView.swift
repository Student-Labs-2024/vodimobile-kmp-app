//
//  ChangePasswordView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 31.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ChangePasswordView: View {
    @State private var isButtonEnabled: Bool = false
    @ObservedObject private var viewModel = UserDataViewModel()
    
    @Environment(\.dismiss) private var dismiss
    
    var body: some View {
        VStack(spacing: AuthAndRegScreensConfig.spacingBetweenGroupAndCheckbox) {
            VStack(spacing: AuthAndRegScreensConfig.spacingBetweenComponents) {
            
                BorderedTextField(
                    fieldContent: $viewModel.oldPassword,
                    isValid: $viewModel.isPasswordValid,
                    fieldType: .oldPassword,
                    inputErrorType: $viewModel.inputError,
                    isForgetBtnEnabled: true
                )
                
                BorderedTextField(
                    fieldContent: $viewModel.password,
                    isValid: $viewModel.isPasswordValid,
                    fieldType: .newPassword,
                    inputErrorType: $viewModel.inputError
                )
                .onChange(of: viewModel.isPasswordValid) { _ in
                    isButtonEnabled.toggle()
                }
                
                Button(R.string.localizable.nextBtnName()) {
                    viewModel.saveEditedUserData()
                    dismiss()
                }
                .buttonStyle(FilledBtnStyle())
                .disabled(!isButtonEnabled)
                .padding(.vertical, 10)
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
}

#Preview {
    ChangePasswordView()
}
