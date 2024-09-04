//
//  NewTextField.swift
//  iosApp
//
//  Created by Sergey Ivanov on 27.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct BorderedTextField: View {
    @State private var errorMessage: String = ""
    @State private var isEditing: Bool = false
    @FocusState private var isFocused: Bool
    @Binding var fieldContent: String
    @Binding var showSignSuggetsModal: Bool
    @Binding var isValid: Bool
    @Binding var inputErrorType: InputErrorType?
    @Binding var navPath: NavigationPath

    private let isForgetBtnEnabled: Bool
    private let fieldType: TextFieldType
    private let placeholder: String
    private let keyboardType: UIKeyboardType
    private var contentIsNotValid: Bool {
        !isValid && !fieldContent.isEmpty
    }

    init(
        fieldContent: Binding<String>,
        isValid: Binding<Bool>,
        fieldType: TextFieldType,
        inputErrorType: Binding<InputErrorType?>,
        showSignSuggestModal: Binding<Bool>? = nil,
        isForgetBtnEnabled: Bool = false,
        navPath: Binding<NavigationPath>? = nil
    ) {
        self._fieldContent = fieldContent
        self._isValid = isValid
        self._inputErrorType = inputErrorType
        self._showSignSuggetsModal = showSignSuggestModal ?? Binding.constant(true)
        self._navPath = navPath ?? Binding.constant(NavigationPath())
        self.fieldType = fieldType
        self.isForgetBtnEnabled = isForgetBtnEnabled

        switch fieldType {
        case .email:
            placeholder = "example@gmail.com"
            keyboardType = .emailAddress
        case .phone:
            placeholder = R.string.localizable.phonePlaceholder()
            keyboardType = .phonePad
        case .password, .oldPassword:
            placeholder = R.string.localizable.passwordPlaceholder()
            keyboardType = .default
        case .newPassword:
            placeholder = R.string.localizable.newPasswordPlaceholder()
            keyboardType = .default
        case .fullName:
            placeholder = R.string.localizable.fullnamePlaceholder()
            keyboardType = .default
        }
    }

    var body: some View {
        VStack(alignment: .leading) {
            Text(fieldType.localizedStr).font(.header4)
                .foregroundStyle(Color(R.color.text))

            switch fieldType {
            case .phone:
                IphonePhoneTextField(
                    fieldContent: $fieldContent,
                    isValid: $isValid,
                    isEditing: $isEditing,
                    inputErrorType: $inputErrorType,
                    isFocused: $isFocused,
                    errorHandler: handleErrorTypeChanging
                )
            case .password, .oldPassword, .newPassword:
                PasswordTextField(
                    showSignSuggestModal: $showSignSuggetsModal,
                    fieldContent: $fieldContent,
                    isValid: $isValid,
                    isEditing: $isEditing,
                    inputErrorType: $inputErrorType,
                    isFocused: $isFocused,
                    errorHandler: handleErrorTypeChanging,
                    isForgetButtonEnabled: isForgetBtnEnabled,
                    fieldType: fieldType,
                    navPath: $navPath
                )
            case .email, .fullName:
                HStack {
                    TextField(placeholder, text: $fieldContent)
                        .keyboardType(keyboardType)
                        .textInputAutocapitalization(.words)
                        .onChange(of: fieldContent, perform: { _ in
                            isEditing = true
                            if isValid {
                                errorMessage = ""
                                inputErrorType = nil
                            }
                        })
                        .focused($isFocused)
                        .onSubmit {
                            isFocused = false
                            isEditing = false
                        }
                        .onChange(of: inputErrorType) { _ in
                            handleErrorTypeChanging(errorMsg: &errorMessage)
                        }
                    if isEditing {
                        Button(action: {
                            self.fieldContent = ""
                            self.errorMessage = ""
                            self.isFocused = false
                            self.isEditing = false
                        }) {
                            Image.xmark
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .frame(width: 14, height: 14)
                                .foregroundColor(Color(R.color.grayDark))
                        }
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

                if inputErrorType != nil {
                    Text(errorMessage)
                        .font(.paragraph6)
                        .foregroundStyle(Color(R.color.redColor))
                        .padding(.leading, 10)
                }
            }
        }
    }

    private func handleErrorTypeChanging(errorMsg: inout String) {
        let errorMappings: [InputErrorType: [(TextFieldType, String)]] = [
            .alreadyExistsPhone: [(.phone, InputErrorType.alreadyExistsPhone.errorString)],
            .incorrectFullName: [(.fullName, InputErrorType.incorrectFullName.errorString)],
            .incorrectPhone: [(.phone, InputErrorType.incorrectPhone.errorString)],
            .incorrectPass: [(.password, InputErrorType.incorrectPass.errorString)],
            .noSpecSymboldsInPass: [
                (.password, InputErrorType.noSpecSymboldsInPass.errorString),
                (.newPassword, InputErrorType.noSpecSymboldsInPass.errorString)
            ],
            .noUpperLettersInPass: [
                (.password, InputErrorType.noUpperLettersInPass.errorString),
                (.newPassword, InputErrorType.noUpperLettersInPass.errorString)
            ],
            .tooShortPass: [
                (.password, InputErrorType.tooShortPass.errorString),
                (.newPassword, InputErrorType.tooShortPass.errorString)
            ],
            .invalidPass: [(.password, InputErrorType.invalidPass.errorString)],
            .oldPasswordIsWrong: [(.oldPassword, InputErrorType.oldPasswordIsWrong.errorString)]
        ]

        if let inputErrorType = inputErrorType,
           let mappings = errorMappings[inputErrorType] {
            for mapping in mappings where fieldType == mapping.0 {
                errorMsg = mapping.1
                return
            }
        } else if inputErrorType == .selectDayTime || inputErrorType == .selectNightTime {
            errorMsg = ""
        }
    }
}
