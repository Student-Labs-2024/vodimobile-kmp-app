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
    @Binding var isValid: Bool
    @Binding var inputErrorType: InputErrorType?
    
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
        isForgetBtnEnabled: Bool = false
    ) {
        self._fieldContent = fieldContent
        self._isValid = isValid
        self._inputErrorType = inputErrorType
        self.fieldType = fieldType
        self.isForgetBtnEnabled = isForgetBtnEnabled
        
        switch fieldType {
        case .email:
            placeholder = "example@gmail.com"
            keyboardType = .emailAddress
        case .phone:
            placeholder = "+7"
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
            Text(fieldType.localizedStr).font(.header4).foregroundStyle(Color.black)
            
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
                    fieldContent: $fieldContent,
                    isValid: $isValid,
                    isEditing: $isEditing,
                    inputErrorType: $inputErrorType,
                    isFocused: $isFocused,
                    errorHandler: handleErrorTypeChanging,
                    isForgetButtonEnabled: isForgetBtnEnabled,
                    fieldType: fieldType
                )
            case .email, .fullName:
                HStack {
                    TextField(placeholder, text: $fieldContent)
                        .keyboardType(keyboardType)
                        .textInputAutocapitalization(.never)
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
                                .foregroundColor(Color(R.color.grayDarkColor))
                        }
                    }
                }
                .frame(alignment: .leading)
                .font(.paragraph4)
                .padding(16)
                .foregroundStyle(Color.black)
                .multilineTextAlignment(.leading)
                .background(Color(R.color.grayLightColor))
                .cornerRadius(12)
                .overlay(
                    RoundedRectangle(cornerRadius: 12)
                        .stroke(
                            contentIsNotValid ? Color(R.color.redColor) : Color(R.color.grayDarkColor),
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
        if let inputErrorType = inputErrorType {
            switch inputErrorType {
            case .alreadyExistsPhone:
                if fieldType == .phone {
                    errorMsg = InputErrorType.alreadyExistsPhone.errorString
                }
            case .incorrectFullName:
                if fieldType == .fullName {
                    errorMsg = InputErrorType.incorrectFullName.errorString
                }
            case .incorrectPass:
                if fieldType == .password {
                    errorMsg = InputErrorType.incorrectPass.errorString
                }
            case .incorrectPhone:
                if fieldType == .phone {
                    errorMsg = InputErrorType.incorrectPhone.errorString
                }
            case .noSpecSymboldsInPass:
                if fieldType == .password {
                    errorMsg = InputErrorType.noSpecSymboldsInPass.errorString
                }
            case .noUpperLettersInPass:
                if fieldType == .password {
                    errorMsg = InputErrorType.noUpperLettersInPass.errorString
                }
            case .tooShortPass:
                if fieldType == .password {
                    errorMsg = InputErrorType.tooShortPass.errorString
                }
            case .invalidPass:
                if fieldType == .password {
                    errorMsg = InputErrorType.invalidPass.errorString
                }
            case .selectDayTime, .selectNightTime:
                errorMsg = ""
            case .oldPasswordIsWrong:
                if fieldType == .oldPassword {
                    errorMsg = InputErrorType.oldPasswordIsWrong.errorString
                }
            }
        }
    }
}
