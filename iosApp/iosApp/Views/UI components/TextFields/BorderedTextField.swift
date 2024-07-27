//
//  NewTextField.swift
//  iosApp
//
//  Created by Sergey Ivanov on 27.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

enum TextFieldType: String {
    case email
    case phone
    case fullName
    case password
    
    var localizedStr: String {
        switch self {
        case .email:
            return R.string.localizable.email()
        case .phone:
            return R.string.localizable.phone()
        case .fullName:
            return R.string.localizable.fullName()
        case .password:
            return R.string.localizable.password()
        }
    }
}

struct BorderedTextField: View {
    @State private var isSecured: Bool = false
    @State private var errorMessage: String = ""
    @State private var isEditing: Bool = false
    @FocusState private var isFocused: Bool
    @Binding var fieldContent: String
    @Binding var isValid: Bool
    @Binding var inputErrorType: InputErrorType?
    
    private let fieldType: TextFieldType
    private let placeholder: String
    private let keyboardType: UIKeyboardType
    
    init(
        fieldContent: Binding<String>,
        isValid: Binding<Bool>,
        fieldType: TextFieldType,
        inputErrorType: Binding<InputErrorType?>
    ) {
        self._fieldContent = fieldContent
        self._isValid = isValid
        self._inputErrorType = inputErrorType
        self.fieldType = fieldType
        
        switch fieldType {
        case .email:
            placeholder = "example@gmail.com"
            keyboardType = .emailAddress
        case .phone:
            placeholder = "+7"
            keyboardType = .phonePad
        case .password:
            placeholder = R.string.localizable.passwordPlaceholder()
            keyboardType = .default
            isSecured = true
        case .fullName:
            placeholder = R.string.localizable.fullnamePlaceholder()
            keyboardType = .default
        }
    }
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(fieldType.localizedStr).font(.header4).foregroundStyle(Color.black)
            
            if fieldType == .phone {
                IphonePhoneTextField(
                    fieldContent: $fieldContent,
                    isValid: $isValid,
                    isEditing: $isEditing,
                    errorMessage: $errorMessage,
                    isFocused: $isFocused
                )
                .onChange(of: inputErrorType) { _ in
                    handleErrorTypeChanging()
                }
            } else if fieldType == .password {
                PasswordTextField(
                    fieldContent: $fieldContent,
                    isValid: $isValid,
                    isEditing: $isEditing,
                    isSecured: $isSecured,
                    errorMessage: $errorMessage,
                    isFocused: $isFocused
                )
                .onChange(of: inputErrorType) { _ in
                    handleErrorTypeChanging()
                }
            } else {
                HStack {
                    TextField(placeholder, text: $fieldContent)
                        .keyboardType(keyboardType)
                        .textInputAutocapitalization(.never)
                        .onChange(of: fieldContent, perform: { _ in
                            isEditing = true
                        })
                        .focused($isFocused)
                        .onSubmit {
                            isFocused = false
                            isEditing = false
                        }
                        .onChange(of: inputErrorType) { _ in
                            handleErrorTypeChanging()
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
                            !isValid && !fieldContent.isEmpty ? Color(R.color.redColor) : Color(R.color.grayDarkColor),
                            lineWidth: isFocused || (!isValid && !fieldContent.isEmpty) ? 1 : 0
                        )
                )
            }
            
            if let inputErrorType = inputErrorType {
                Text(errorMessage)
                    .font(.paragraph6)
                    .foregroundStyle(Color(R.color.redColor))
                    .padding(.leading, 10)
            }
        }
    }
    
    private func handleErrorTypeChanging() {
        if let inputErrorType = inputErrorType {
            switch inputErrorType {
            case .alreadyExistsPhone:
                if fieldType == .phone {
                    self.errorMessage = InputErrorType.alreadyExistsPhone.localizedString
                } else {
                    self.errorMessage = ""
                }
            case .incorrectFullName:
                if fieldType == .fullName {
                    self.errorMessage = InputErrorType.incorrectFullName.localizedString
                } else {
                    self.errorMessage = ""
                }
            case .incorrectPass:
                if fieldType == .password {
                    self.errorMessage = InputErrorType.incorrectPass.localizedString
                } else {
                    self.errorMessage = ""
                }
            case .incorrectPhone:
                if fieldType == .phone {
                    self.errorMessage = InputErrorType.incorrectPhone.localizedString
                } else {
                    self.errorMessage = ""
                }
            case .noSpecSymboldsInPass:
                if fieldType == .password {
                    self.errorMessage = InputErrorType.noSpecSymboldsInPass.localizedString
                } else {
                    self.errorMessage = ""
                }
            case .noUpperLettersInPass:
                if fieldType == .password {
                    self.errorMessage = InputErrorType.noUpperLettersInPass.localizedString
                } else {
                    self.errorMessage = ""
                }
            case .tooShortPass:
                if fieldType == .password {
                    self.errorMessage = InputErrorType.tooShortPass.localizedString
                } else {
                    self.errorMessage = ""
                }
            }
        }
    }
}
