//
//  ConfirmationScreenView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 10.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct PinCodeView: View {
    @Binding var navPath: NavigationPath
    @ObservedObject var viewModel: PinCodeViewModel
    @FocusState var focusedField: Int?
    @ObservedObject var userViewModel = UserDataViewModel.shared
    private let cleanUpField: () -> Void
    @Environment(\.dismiss) private var dismiss

    @State private var alertErrorTitle = ""
    @State private var alertErrorText = ""

    init(
        showSignSuggestModal: Binding<Bool>,
        authFlowType: AuthFlowType,
        phoneNumber: String,
        pass: String,
        navPath: Binding<NavigationPath>? = nil,
        fullname: String? = nil,
        cleanUpFieldClosure: @escaping () -> Void
    ) {
        self.viewModel = .init(
            showSignSuggestModal: showSignSuggestModal,
            authFlowType: authFlowType,
            phoneNumber: phoneNumber,
            pass: pass,
            fullname: fullname
        )
        self._navPath = navPath ?? Binding.constant(NavigationPath())
        self.cleanUpField = cleanUpFieldClosure
    }

    var body: some View {
        VStack(spacing: PinCodeConfig.spacingBetweenGroupAndResendText) {
            VStack(spacing: PinCodeConfig.spacingBetweenMainComponents) {
                Text(R.string.localizable.inputCodeText)
                    .font(.header2)
                    .padding(.top, PinCodeConfig.contentTopPadding)
                    .foregroundStyle(Color(R.color.text))
                    .multilineTextAlignment(.center)

                Text(viewModel.sendCodeOnPhoneText)
                    .font(.paragraph2)
                    .foregroundColor(Color(R.color.grayText))
                    .multilineTextAlignment(.center)

                HStack(spacing: PinCodeConfig.spacingBetweenPincodeCells) {
                    ForEach(0..<4) { index in
                        PinCodeTextField(index: index)
                    }
                }
                .padding(.vertical, PinCodeConfig.verticalSpacingBetweenPincodeField)
                .onChange(of: viewModel.pin) { _ in
                    toggleButtonEnabled()
                }

                switch viewModel.authFlowType {
                case .registration:
                    Button(R.string.localizable.nextBtnName(), action: {
                        Task {
                            await viewModel.authManager.signUp(
                                fullname: viewModel.fullname,
                                phone: viewModel.phoneNumber,
                                password: viewModel.pass
                            )
                            if viewModel.authManager.isAuthenticated {
                                await MainActor.run {
                                    viewModel.showSignSuggestModal.toggle()
                                    cleanUpField()
                                }
                            } else {
                                handleError(viewModel.authManager.errorType)
                            }
                        }
                    })
                    .buttonStyle(FilledBtnStyle())
                    .disabled(!viewModel.isButtonEnabled)
                case .auth:
                    Button(R.string.localizable.nextBtnName(), action: {
                        Task {
                            await viewModel.authManager.login(phone: viewModel.phoneNumber, pass: viewModel.pass)
                            if viewModel.authManager.isAuthenticated {
                                await MainActor.run {
                                    viewModel.showSignSuggestModal.toggle()
                                    cleanUpField()
                                }
                            } else {
                                handleError(viewModel.authManager.errorType)
                            }
                        }
                    })
                    .buttonStyle(FilledBtnStyle())
                    .disabled(!viewModel.isButtonEnabled)
                case .resetPassword:
                    NavigationLink(destination: ResetPasswordPassView(
                        showSignSuggestModal: $viewModel.showSignSuggestModal, navPath: $navPath)) {
                        Text(R.string.localizable.nextBtnName)
                    }
                    .buttonStyle(FilledBtnStyle())
                    .disabled(!viewModel.isButtonEnabled)
                }
            }

            HStack {
                Text(R.string.localizable.notGetCodeText)
                    .foregroundStyle(Color(R.color.text))
                    .font(.paragraph4)
                Button(
                    action: {
                        print("Отправить код повторно нажат")
                    }
                ) {
                    Text(R.string.localizable.resendBtnText)
                        .foregroundColor(Color(R.color.blueColor))
                        .font(.buttonText)
                        .underline()
                }
            }

            Spacer()
        }
        .alert(
            alertErrorTitle,
            isPresented: $viewModel.showErrorAlert,
            actions: {
                Button(R.string.localizable.closeButton(), role: .cancel) { }
            },
            message: {
                Text(alertErrorText)
            }
        )
        .padding()
        .onAppear {
            focusedField = 0
        }
        .navigationBarBackButtonHidden()
        .toolbar {
            CustomToolbar(title: R.string.localizable.confirmScreenTitle)
        }
    }

    @ViewBuilder
    private func PinCodeTextField(index: Int) -> some View {
        let isFieldFocused = focusedField == index
        let strokeColor = isFieldFocused ? Color(R.color.blueColor) : Color(R.color.grayDark)
        let lineWidth: CGFloat = viewModel.pin[index].isEmpty && !isFieldFocused ? 0 : 2

        TextField("", text: $viewModel.pin[index])
            .keyboardType(.numberPad)
            .multilineTextAlignment(.center)
            .font(.paragraph1)
            .frame(width: 56, height: 56)
            .background(
                RoundedRectangle(cornerRadius: 12, style: .continuous)
                    .fill(Color(R.color.grayTheme))
            )
            .overlay(
                RoundedRectangle(cornerRadius: 12)
                    .stroke(strokeColor, lineWidth: lineWidth)
            )
            .focused($focusedField, equals: index)
            .onChange(of: viewModel.pin[index]) { newValue in
                if newValue.count == 1, index < 3 {
                    focusedField = index + 1
                } else if newValue.isEmpty, index > 0 {
                    focusedField = index - 1
                } else if newValue.count > 1 {
                    viewModel.pin[index] = String(newValue.prefix(1))
                } else if index == viewModel.pin.count - 1 && !viewModel.pin[index].isEmpty {
                    focusedField = nil
                }
            }
    }

    private func handleError(_ error: InputErrorType?) {
        if let error = error {
            switch error {
            case .incorrectPass:
                alertErrorText = R.string.localizable.authErrorAlertText()
                alertErrorTitle = R.string.localizable.authErrorAlertTitle()
            case .alreadyExistsPhone:
                alertErrorText = R.string.localizable.alreadyExistsPhone()
                alertErrorTitle = R.string.localizable.authErrorAlertTitle()
            case .notExistsPhone:
                alertErrorText = R.string.localizable.notExistsPhone()
                alertErrorTitle = R.string.localizable.authErrorAlertTitle()
            default:
                alertErrorText = ""
                alertErrorTitle = ""
            }
            viewModel.showErrorAlert = true
        }
    }

    private func toggleButtonEnabled() {
        viewModel.isButtonEnabled = viewModel.pin.joined().count == 4
    }
}
