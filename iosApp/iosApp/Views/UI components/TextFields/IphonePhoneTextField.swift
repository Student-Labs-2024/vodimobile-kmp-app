//
//  IphonePhoneTextField.swift
//  iosApp
//
//  Created by Sergey Ivanov on 26.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import iPhoneNumberField

struct IphonePhoneTextField: View {
    @Binding var fieldContent: String
    @Binding var isValid: Bool
    @Binding var errorMessage: String
    @Binding var isEditing: Bool
    @FocusState.Binding var isFocused: Bool
    let validateFunc: () -> ()
    
    private let fieldName: String = TextFieldType.phone.localizedStr
    private let placeholder: String = "+7"
    private let keyboardType: UIKeyboardType = .phonePad
    private let regex: String = phoneRegex
    
    var body: some View {
        iPhoneNumberField(placeholder, text: $fieldContent)
            .formatted()
            .prefixHidden(false)
            .clearButtonMode(.never)
            .maximumDigits(14)
            .onEditingBegan { _ in
                if fieldContent.isEmpty {
                    fieldContent = "+"
                }
            }
            .onEditingEnded { _ in
                if fieldContent == "+" {
                    fieldContent = ""
                }
            }
            .onEdit { _ in
                validateFunc()
            }
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
            .tint(.black)
            .textFieldStyle(BorderedTextFieldStyle(text: fieldContent, isFocused: isFocused, isValid: isValid))
            .keyboardType(keyboardType)
            .textInputAutocapitalization(.never)
            .focused($isFocused)
            .onSubmit {
                isFocused = false
                validateFunc()
            }
            .overlay(
                HStack {
                    Spacer()
                    Button(action: {
                        self.fieldContent = ""
                        validateFunc()
                    }) {
                        Image.xmark
                            .foregroundColor(Color(R.color.grayDarkColor))
                            .padding(8)
                    }
                }
                .padding(.trailing, 8)
                .opacity(self.isEditing ? 1 : 0)
            )
    }
}
