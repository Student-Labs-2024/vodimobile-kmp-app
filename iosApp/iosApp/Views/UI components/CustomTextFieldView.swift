//
//  CustomTextFieldView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 09.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

enum TextFieldType {
    case email
    case phone
    case fullName
}

extension TextFieldType {
    var localizedStr: String {
        switch self {
        case .email:
            return String(localized: String.LocalizationValue(stringLiteral: "email"))
        case .phone:
            return String(localized: String.LocalizationValue(stringLiteral: "phone"))
        case .fullName:
            return String(localized: String.LocalizationValue(stringLiteral: "fullName"))
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
            
            TextField(placeholder, text: $fieldContent)
                .textFieldStyle(BorderedTextFieldStyle(text: fieldContent, isFocused: isFocused, isValid: isValid))
                .keyboardType(keyboardType)
                .textInputAutocapitalization(.never)
                .onChange(of: fieldContent, perform: { oldValue in
                    if fieldName == TextFieldType.phone.localizedStr {
                        fieldContent = format(with: "+X XXX XXX-XX-XX", phone: oldValue)
                        validateInput()
                    } else {
                        validateInput()
                    }
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
                                .foregroundColor(Color.grayDarkColor)
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
            
            Text(errorMessage)
                .font(.paragraph6)
                .foregroundStyle(Color.redColor)
                .padding(.horizontal, 10)
        }
        .padding(.all, 0)
    }
    
    func format(with mask: String, phone: String) -> String {
        let numbers = phone.replacingOccurrences(of: "[^0-9]", with: "", options: .regularExpression)
        var result = ""
        var index = numbers.startIndex
        
        for ch in mask where index < numbers.endIndex {
            if ch == "X" {
                result.append(numbers[index])
                
                index = numbers.index(after: index)
                
            } else {
                result.append(ch)
            }
        }
        return result
    }
    
    private func validateInput() {
        let predicate = NSPredicate(format: "SELF MATCHES %@", regex)
        let errorResult: String = String(localized: String.LocalizationValue(stringLiteral: "inputErrorMsg"))
        
        isValid = predicate.evaluate(with: fieldContent)
        
        if !isValid && !fieldContent.isEmpty {
            errorMessage = "\(errorResult)\(fieldName.lowercased())"
        } else {
            errorMessage = ""
        }
    }
}
