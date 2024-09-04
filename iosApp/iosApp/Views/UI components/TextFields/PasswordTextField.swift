//
//  PasswordTextField.swift
//  iosApp
//
//  Created by Sergey Ivanov on 27.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct PasswordTextField: View {
    @Binding var showSignSuggestModal: Bool
    @Binding var fieldContent: String
    @Binding var isValid: Bool
    @Binding var isEditing: Bool
    @Binding var inputErrorType: InputErrorType?
    @Binding var navPath: NavigationPath
    @State var errorMessage: String = ""
    @State var isSecured: Bool = true
    @FocusState.Binding var isFocused: Bool

    let errorHandler: (inout String) -> Void
    let isForgetButtonEnabled: Bool
    let fieldType: TextFieldType
    let fieldName: String = TextFieldType.password.localizedStr
    let placeholder: String = R.string.localizable.passwordPlaceholder()
    let keyboardType: UIKeyboardType = .default
    private var contentIsNotValid: Bool {
        !isValid && !fieldContent.isEmpty
    }

    init(
        showSignSuggestModal: Binding<Bool>,
        fieldContent: Binding<String>,
        isValid: Binding<Bool>,
        isEditing: Binding<Bool>,
        inputErrorType: Binding<InputErrorType?> = Binding.constant(nil),
        isFocused: FocusState<Bool>.Binding,
        errorHandler: @escaping (inout String) -> Void,
        isForgetButtonEnabled: Bool,
        fieldType: TextFieldType,
        navPath: Binding<NavigationPath>? = nil
    ) {
        self._showSignSuggestModal = showSignSuggestModal
        self._isFocused = isFocused
        self._fieldContent = fieldContent
        self._isValid = isValid
        self._isEditing = isEditing
        self._inputErrorType = inputErrorType
        self._navPath = navPath ?? Binding.constant(NavigationPath())
        self.errorHandler = errorHandler
        self.isForgetButtonEnabled = isForgetButtonEnabled
        self.fieldType = fieldType
    }

    var body: some View {
        VStack(alignment: .leading) {
            Group {
                if isSecured {
                    HStack {
                        SecureField(placeholder, text: $fieldContent)
                            .keyboardType(keyboardType)
                            .textInputAutocapitalization(.never)
                            .focused($isFocused)
                            .onSubmit {
                                isFocused = false
                                isEditing = false
                            }
                            .onChange(of: fieldContent) { _ in
                                isEditing = true
                                if isValid {
                                    errorMessage = ""
                                    inputErrorType = nil
                                }
                            }

                        Button(action: {
                            isSecured.toggle()
                        }) {
                            Image.eyeSlash
                                .foregroundStyle(Color(R.color.grayDark))
                        }
                    }
                    .frame(alignment: .leading)
                    .font(.paragraph4)
                    .padding(16)
                    .foregroundStyle(Color(R.color.text))
                    .multilineTextAlignment(.leading)
                    .background(Color(R.color.grayTheme))
                    .cornerRadius(12)
                    .overlay(
                        RoundedRectangle(cornerRadius: 12)
                            .stroke(
                                contentIsNotValid ? Color(R.color.redColor) : Color(R.color.grayDark),
                                lineWidth: isFocused || contentIsNotValid ? 1 : 0
                            )
                    )
                } else {
                    HStack {
                        TextField(placeholder, text: $fieldContent)
                            .keyboardType(keyboardType)
                            .textInputAutocapitalization(.never)
                            .focused($isFocused)
                            .onSubmit {
                                isFocused = false
                                isEditing = false
                            }
                            .onChange(of: fieldContent) { _ in
                                isEditing = true
                                if isValid {
                                    errorMessage = ""
                                    inputErrorType = nil
                                }
                            }

                        Button(action: {
                            isSecured.toggle()
                        }) {
                            Image.eye
                                .foregroundStyle(Color(R.color.grayDark))
                        }
                    }
                    .frame(alignment: .leading)
                    .font(.paragraph4)
                    .padding(16)
                    .foregroundStyle(Color(R.color.text))
                    .multilineTextAlignment(.leading)
                    .background(Color(R.color.grayTheme))
                    .cornerRadius(12)
                    .overlay(
                        RoundedRectangle(cornerRadius: 12)
                            .stroke(
                                contentIsNotValid ? Color(R.color.redColor) : Color(R.color.grayDark),
                                lineWidth: isFocused || contentIsNotValid ? 1 : 0
                            )
                    )
                }
            }
            .onChange(of: inputErrorType) { _ in
                errorHandler(&errorMessage)
            }

            HStack {
                if inputErrorType != nil {
                    Text(errorMessage)
                        .font(.paragraph6)
                        .foregroundStyle(Color(R.color.redColor))
                        .padding(.leading, 10)
                }
                Spacer()

                if isForgetButtonEnabled {
                    NavigationLink(R.string.localizable.forgetPassword()) {
                        // TODO: - Make modal ResetPasswordPhoneView
                        ResetPasswordPhoneView(
                            showSignSuggestModal: $showSignSuggestModal,
                            navPath: $navPath
                        )
                    }
                    .font(.paragraph5)
                    .foregroundStyle(Color(R.color.grayText))
                    .multilineTextAlignment(.trailing)
                }
            }
        }
    }
}
