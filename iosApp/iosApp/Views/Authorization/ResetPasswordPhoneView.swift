//
//  ResetPasswordView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 31.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ResetPasswordPhoneView: View {
    @State private var isButtonEnabled: Bool = false
    @Binding var showSignSuggestModal: Bool
    @Binding var navPath: NavigationPath
    @State private var pushToNextView = false
    @ObservedObject var viewModel = UserDataViewModel.shared

    @Environment(\.dismiss) private var dismiss

    var body: some View {
        NavigationStack {
            VStack(spacing: AuthAndRegScreensConfig.spacingBetweenGroupAndCheckbox) {
                VStack(spacing: AuthAndRegScreensConfig.spacingBetweenComponents) {
                    VStack(spacing: 12) {
                        Text(R.string.localizable.resetScreenTitle)
                            .font(.header2)

                        Text(R.string.localizable.resetScreenSubtitle)
                            .font(.paragraph2)
                            .foregroundStyle(Color(R.color.grayText))
                    }
                    .padding(.vertical, 24)

                    BorderedTextField(
                        fieldContent: $viewModel.phoneField,
                        isValid: $viewModel.isPhoneValid,
                        fieldType: .phone,
                        inputErrorType: $viewModel.inputError
                    )
                    .onChange(of: viewModel.isPhoneValid) { _ in
                        isButtonEnabled = true
                    }

                    Button(R.string.localizable.nextBtnName()) {
                        Task {
                            await viewModel.checkExistenceOfPhone(viewModel.phoneField.cleanUp())
                            if !viewModel.phoneNotExist {
                                await MainActor.run {
                                    pushToNextView.toggle()
                                }
                            }
                        }
                    }
                    .buttonStyle(FilledBtnStyle())
                    .disabled(!isButtonEnabled)
                    .padding(.vertical, 10)
                }
                Spacer()
            }
            .alert(
                R.string.localizable.authErrorAlertTitle(),
                isPresented: $viewModel.phoneNotExist,
                actions: {
                    Button(R.string.localizable.closeButton(), role: .cancel) {
                        viewModel.phoneNotExist.toggle()
                    }
                },
                message: {
                    Text(R.string.localizable.notExistsPhone())
                }
            )
            .padding(.horizontal, horizontalPadding)
            .padding(.top, аuthScreencontentTopPadding)
            .navigationBarBackButtonHidden()
            .toolbar {
                CustomToolbar(title: R.string.localizable.resetPassScreenTitle)
            }
            .navigationDestination(isPresented: $pushToNextView) {
                PinCodeView(
                    showSignSuggestModal: $showSignSuggestModal,
                    authFlowType: .resetPassword,
                    phoneNumber: viewModel.phoneField,
                    pass: viewModel.passwordField,
                    navPath: $navPath) {
                        viewModel.cleanAllFields()
                    }
            }
        }
    }
}
