//
//  UnderlinedTextField.swift
//  iosApp
//
//  Created by Sergey Ivanov on 13.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import iPhoneNumberField

struct UnderlineTextField: View {
    @Binding var text: String
    @Binding var isValid: Bool
    @State private var errorMessage: String = ""
    var fieldType: TextFieldType
    private var title: String
    private let keyboardType: UIKeyboardType
    private let regex: String
    
    @FocusState private var isFocused: Bool
    @State private var isPlaceholderVisible: Bool
    
    init(text: Binding<String>, isValid: Binding<Bool>, fieldType: TextFieldType) {
        self._text = text
        self.fieldType = fieldType
        self._isValid = isValid
        
        switch fieldType {
        case .email:
            title = TextFieldType.email.localizedStr
            keyboardType = .emailAddress
            regex = emailRegex
        case .phone:
            title = TextFieldType.phone.localizedStr
            keyboardType = .phonePad
            regex = phoneRegex
        case .fullName:
            title = TextFieldType.fullName.localizedStr
            keyboardType = .default
            regex = textRegex
        case .password:
            title = TextFieldType.password.localizedStr
            keyboardType = .default
            regex = passRegex
        }
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            Text(title)
                .font(.paragraph5)
                .foregroundColor(isFocused || !text.isEmpty ? Color(R.color.grayDarkColor) : .clear)
                .animation(.easeInOut(duration: 0.2), value: isFocused || !text.isEmpty)
            
            ZStack(alignment: .leading) {
                if isPlaceholderVisible {
                    Text(title)
                        .font(.paragraph5)
                        .foregroundColor(Color(R.color.grayTextColor))
                        .onTapGesture {
                            self.isFocused = true
                        }
                }
                
                if fieldType == .phone {
                    iPhoneNumberField(title, text: $text)
                        .formatted()
                        .prefixHidden(false)
                        .clearButtonMode(.never)
                        .maximumDigits(14)
                        .onEditingBegan { _ in
                            if fieldType == .phone && text.isEmpty {
                                text = "+"
                            }
                        }
                        .onEditingEnded { _ in
                            if fieldType == .phone && text == "+" {
                                text = ""
                            }
                        }
                        .onEdit { _ in
                            validateInput()
                        }
                        .font(.paragraph2)
                        .focused($isFocused)
                        .padding(.bottom, 5)
                        .opacity(isPlaceholderVisible ? 0 : 1)
                        .onChange(of: isFocused) { newValue in
                            withAnimation {
                                isPlaceholderVisible = !newValue && text.isEmpty
                            }
                        }
                        .onChange(of: text) { newValue in
                            withAnimation {
                                isPlaceholderVisible = text.isEmpty && !isFocused
                            }
                        }
                        .onChange(of: text) { _ in
                            validateInput()
                        }
                } else {
                    TextField("", text: $text)
                        .font(.paragraph2)
                        .focused($isFocused)
                        .padding(.bottom, 5)
                        .opacity(isPlaceholderVisible ? 0 : 1)
                        .onChange(of: isFocused) { newValue in
                            withAnimation {
                                isPlaceholderVisible = !newValue && text.isEmpty
                            }
                        }
                        .onChange(of: text) { newValue in
                            withAnimation {
                                isPlaceholderVisible = text.isEmpty && !isFocused
                            }
                        }
                        .onChange(of: text) { _ in
                            validateInput()
                        }
                }
            }
            .overlay(
                Rectangle()
                    .frame(height: 1)
                    .foregroundColor(isFocused ? Color(R.color.blueColor) : Color(R.color.grayDarkColor)),
                alignment: .bottom
            )
            
        }
    }
    
    private func validateInput() {
        let predicate = NSPredicate(format: "SELF MATCHES %@", regex)
        let errorResult: String = String(localized: String.LocalizationValue(stringLiteral: "inputErrorMsg"))
        let cleanedStr = text.replacingOccurrences(of: "(", with: "").replacingOccurrences(of: ")", with: "")
        
        isValid = predicate.evaluate(with: cleanedStr)
        
        if !isValid && !text.isEmpty {
            errorMessage = "\(errorResult)\(title.lowercased())"
        } else {
            errorMessage = ""
        }
    }
}
