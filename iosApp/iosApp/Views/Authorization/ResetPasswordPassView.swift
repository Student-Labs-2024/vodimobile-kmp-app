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
    @StateObject var viewModel = UserDataViewModel.shared

    @Environment(\.dismiss) private var dismiss

    var body: some View {
        VStack(spacing: AuthAndRegScreensConfig.spacingBetweenGroupAndCheckbox) {
            VStack(spacing: AuthAndRegScreensConfig.spacingBetweenComponents) {
                    BorderedTextField(
                        fieldContent: $viewModel.passwordField,
                        isValid: $viewModel.isPasswordValid,
                        fieldType: .password,
                        inputErrorType: $viewModel.inputError
                    )
                    .onChange(of: viewModel.isPasswordValid) { _ in
                        toggleButtonEnabled()
                    }

                Button(R.string.localizable.saveButton(), action: {
                    viewModel.changePassword(to: viewModel.passwordField)
                })
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
                        .foregroundStyle(Color(R.color.grayDark))

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
        .alert(
            R.string.localizable.alertSavePersonDataTitle(),
            isPresented: $viewModel.dataHasBeenSaved) {
                Button(R.string.localizable.closeButton(), role: .cancel) {
                    viewModel.dataHasBeenSaved.toggle()
                }
            }
        .padding(.horizontal, horizontalPadding)
        .padding(.top, аuthScreencontentTopPadding)
        .navigationBarBackButtonHidden()
        .loadingOverlay(isLoading: $viewModel.isLoading)
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
