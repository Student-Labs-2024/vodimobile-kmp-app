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
    @ObservedObject var viewModel: PinCodeViewModel
    @FocusState var focusedField: Int?
    @Environment(\.dismiss) private var dismiss

    init(
        showSignSuggestModal: Binding<Bool>,
        authFlowType: AuthFlowType,
        phoneNumber: String,
        pass: String,
        fullname: String? = nil
    ) {
        self.viewModel = .init(
            showSignSuggestModal: showSignSuggestModal,
            authFlowType: authFlowType,
            phoneNumber: phoneNumber,
            pass: pass
        )
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
                        }
                        viewModel.showSignSuggestModal.toggle()
                    })
                    .buttonStyle(FilledBtnStyle())
                    .disabled(!viewModel.isButtonEnabled)
                case .auth:
                    Button(R.string.localizable.nextBtnName(), action: {
                        Task {
                            await viewModel.authManager.login(phone: viewModel.phoneNumber, pass: viewModel.pass)
                        }
                        viewModel.showSignSuggestModal.toggle()
                    })
                    .buttonStyle(FilledBtnStyle())
                    .disabled(!viewModel.isButtonEnabled)
                case .resetPassword:
                    NavigationLink(destination: ResetPasswordPassView()) {
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

    private func toggleButtonEnabled() {
        viewModel.isButtonEnabled = viewModel.pin.joined().count == 4
    }
}
