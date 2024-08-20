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
    @Binding var isEditing: Bool
    @Binding var inputErrorType: InputErrorType?
    @State var errorMessage: String = ""
    @FocusState.Binding var isFocused: Bool

    let errorHandler: (inout String) -> Void
    let fieldName: String = TextFieldType.phone.localizedStr
    let placeholder: String = R.string.localizable.phonePlaceholder()
    let keyboardType: UIKeyboardType = .phonePad

    var body: some View {
        VStack(alignment: .leading) {
            iPhoneNumberField(placeholder, text: $fieldContent)
                .formatted()
                .prefixHidden(false)
                .clearButtonMode(.never)
                .maximumDigits(14)
                .onEditingBegan { _ in
                    isEditing = true
                    if fieldContent.isEmpty {
                        fieldContent = "+"
                    }
                }
                .onEditingEnded { _ in
                    isEditing = false
                    isFocused = false
                    if fieldContent == "+" {
                        fieldContent = ""
                    }
                }
                .onEdit { _ in
                    if isValid {
                        errorMessage = ""
                        inputErrorType = nil
                    }
                }
                .font(.paragraph4)
                .padding(16)
                .foregroundStyle(Color.black)
                .multilineTextAlignment(.leading)
                .background(Color(R.color.grayLight))
                .cornerRadius(12)
                .overlay(
                    RoundedRectangle(cornerRadius: 12)
                        .stroke(
                            !isValid && !fieldContent.isEmpty ? Color(R.color.redColor) : Color(R.color.grayDark),
                            lineWidth: isFocused || (!isValid && !fieldContent.isEmpty) ? 1 : 0
                        )
                )
                .tint(.black)
                .textFieldStyle(BorderedTextFieldStyle(text: fieldContent, isFocused: isFocused, isValid: isValid))
                .keyboardType(keyboardType)
                .textInputAutocapitalization(.never)
                .onSubmit {
                    isEditing = false
                    isFocused = false
                }
                .overlay(
                    HStack {
                        Spacer()
                        Button(action: {
                            self.fieldContent = ""
                            self.errorMessage = ""
                            self.isFocused = false
                            self.isEditing = false
                        }) {
                            Image.xmark
                                .foregroundColor(Color(R.color.grayDark))
                                .padding(8)
                        }
                    }
                        .padding(.trailing, 8)
                        .opacity(self.isEditing ? 1 : 0)
                )
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
