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
    @StateObject var viewModel: UserDataViewModel

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
                    fieldContent: $viewModel.passwordField,
                    isValid: $viewModel.isPasswordValid,
                    fieldType: .newPassword,
                    inputErrorType: $viewModel.inputError
                )
                .onChange(of: viewModel.isPasswordValid) { _ in
                    isButtonEnabled.toggle()
                }

                Button(R.string.localizable.nextBtnName()) {
                    Task {
                        await viewModel.changePassword(to: viewModel.passwordField)
                    }
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
        .alert(
            R.string.localizable.alertSavePersonDataTitle(),
            isPresented: $viewModel.dataHasBeenSaved) {
                Button(R.string.localizable.closeButton(), role: .cancel) {
                    dismiss()
                    viewModel.cleanAllFields()
                    viewModel.dataHasBeenSaved = false
                }
            }
            .toolbar {
                CustomToolbar(title: R.string.localizable.resetPassScreenTitle)
        }
    }
}

#Preview {
    ChangePasswordView(viewModel: UserDataViewModel())
}
