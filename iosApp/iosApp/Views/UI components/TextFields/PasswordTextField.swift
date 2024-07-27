//
//  PasswordTextField.swift
//  iosApp
//
//  Created by Sergey Ivanov on 27.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct PasswordTextField: View {
    @Binding var fieldContent: String
    @Binding var isValid: Bool
    @Binding var isEditing: Bool
    @Binding var isSecured: Bool
    @Binding var errorMessage: String
    @FocusState.Binding var isFocused: Bool
    
    let fieldName: String = TextFieldType.password.localizedStr
    let placeholder: String = R.string.localizable.passwordPlaceholder()
    let keyboardType: UIKeyboardType = .default
    
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
                            }
                        
                        Button(action: {
                            isSecured.toggle()
                        }) {
                            Image.eyeSlash
                                .foregroundStyle(Color(R.color.grayDarkColor))
                            
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
                } else {
                    HStack {
                        TextField(placeholder, text: $fieldContent)
                            .keyboardType(keyboardType)
                            .textInputAutocapitalization(.never)
                            .focused($isFocused)
                            .onSubmit {
                                isFocused = false
                            }
                        
                        Button(action: {
                            isSecured.toggle()
                        }) {
                            Image.eye
                                .foregroundStyle(Color(R.color.grayDarkColor))
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
            }
        }

    }
}
