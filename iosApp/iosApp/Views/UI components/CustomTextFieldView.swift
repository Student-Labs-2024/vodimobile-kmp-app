//
//  CustomTextFieldView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 09.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import iPhoneNumberField
import RswiftResources

enum TextFieldType {
    case email
    case phone
    case fullName
}

extension TextFieldType {
    var localizedStr: String {
        switch self {
        case .email:
            return R.string.localizable.email()
        case .phone:
            return R.string.localizable.phone()
        case .fullName:
            return R.string.localizable.fullName()
        }
    }
}

struct CustomTextFieldView: View {
    @Binding var fieldContent: String
    @Binding var isValid: Bool
    @State private var errorMessage: String = ""
    @State private var isEditing: Bool = false
    @FocusState private var isFocused: Bool
    
    private let fieldName: String
    private let placeholder: String
    private let keyboardType: UIKeyboardType
    private let regex: String
    
    init(fieldContent: Binding<String>, isValid: Binding<Bool>, fieldType: TextFieldType) {
        self._fieldContent = fieldContent
        self._isValid = isValid
        fieldName = fieldType.localizedStr
        
        switch fieldType {
        case .email:
            placeholder = "example@gmail.com"
            keyboardType = .emailAddress
            regex = emailRegex
        case .phone:
            placeholder = "+7"
            keyboardType = .phonePad
            regex = phoneRegex
        default:
            placeholder = ""
            keyboardType = .default
            regex = ""
        }
    }
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(fieldName).font(.header4).foregroundStyle(Color.black)
            
            if fieldName == TextFieldType.phone.localizedStr {
                iPhoneNumberField(placeholder, text: $fieldContent)
                    .formatted()
                    .prefixHidden(false)
                    .clearButtonMode(.never)
                    .maximumDigits(14)
                    .onEditingBegan { _ in
                        if fieldName == TextFieldType.phone.localizedStr && fieldContent.isEmpty {
                            fieldContent = "+"
                        }
                    }
                    .onEditingEnded { _ in
                        if fieldName == TextFieldType.phone.localizedStr && fieldContent == "+" {
                            fieldContent = ""
                        }
                    }
                    .onEdit { _ in
                        validateInput()
                    }
                    .font(.paragraph4)
                    .padding(16)
                    .foregroundStyle(Color.black)
                    .multilineTextAlignment(.leading)
                    .background(Color(R.color.grayLightColor))
                    .cornerRadius(12)
                    .overlay(
                        RoundedRectangle(cornerRadius: 12)
                            .stroke(!isValid && !fieldContent.isEmpty ? Color(R.color.redColor) : Color(R.color.grayDarkColor), lineWidth: isFocused || (!isValid && !fieldContent.isEmpty) ? 1 : 0)
                    )
                    .tint(.black)
                    .textFieldStyle(CustomTextFieldStyle(text: fieldContent, isFocused: isFocused, isValid: isValid))
                    .keyboardType(keyboardType)
                    .textInputAutocapitalization(.never)
                    .focused($isFocused)
                    .onSubmit {
                        isFocused = false
                        validateInput()
                    }
                    .overlay(
                        HStack {
                            Spacer()
                            Button(action: {
                                self.fieldContent = ""
                                validateInput()
                            }) {
                                Image(systemName: "xmark")
                                    .foregroundColor(Color(R.color.grayDarkColor))
                                    .padding(8)
                            }
                        }
                            .padding(.trailing, 8)
                            .opacity(self.isEditing ? 1 : 0)
                    )
            } else {
                TextField(placeholder, text: $fieldContent)
                    .textFieldStyle(CustomTextFieldStyle(text: fieldContent, isFocused: isFocused, isValid: isValid))
                    .keyboardType(keyboardType)
                    .textInputAutocapitalization(.never)
                    .onChange(of: fieldContent, perform: { oldValue in
                        validateInput()
                    })
                    .focused($isFocused)
                    .onSubmit {
                        isFocused = false
                        validateInput()
                    }
                    .overlay(
                        HStack {
                            Spacer()
                            Button(action: {
                                self.fieldContent = ""
                                validateInput()
                            }) {
                                Image(systemName: "xmark")
                                    .foregroundColor(Color(R.color.grayDarkColor))
                                    .padding(8)
                            }
                        }
                            .padding(.trailing, 8)
                            .opacity(self.isEditing ? 1 : 0)
                    )
                    .onTapGesture {
                        self.isEditing = true
                    }
                    .onDisappear {
                        self.isEditing = false
                    }
            }

            
            Text(errorMessage)
                .font(.paragraph6)
                .foregroundStyle(Color(R.color.redColor))
                .padding(.horizontal, 10)
        }
    }
    
    private func validateInput() {
        let predicate = NSPredicate(format: "SELF MATCHES %@", regex)
        let errorResult: String = String(localized: String.LocalizationValue(stringLiteral: "inputErrorMsg"))
        let cleanedStr = fieldContent.replacingOccurrences(of: "(", with: "").replacingOccurrences(of: ")", with: "")

        isValid = predicate.evaluate(with: cleanedStr)
        
        if !isValid && !fieldContent.isEmpty {
            errorMessage = "\(errorResult)\(fieldName.lowercased())"
        } else {
            errorMessage = ""
        }
    }
}
