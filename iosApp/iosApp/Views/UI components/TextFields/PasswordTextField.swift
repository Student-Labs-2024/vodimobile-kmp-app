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
    @Binding var inputErrorType: InputErrorType?
    @State var errorMessage: String = ""
    @State var isSecured: Bool = true
    @FocusState.Binding var isFocused: Bool
    
    let errorHandler: (inout String) -> ()
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
            .onChange(of: inputErrorType) { _ in
                errorHandler(&errorMessage)
            }
            
            if inputErrorType != nil {
                Text(errorMessage)
                    .font(.paragraph6)
                    .foregroundStyle(Color(R.color.redColor))
                    .padding(.leading, 10)
            }
        }
    }
}
