//
//  UnderlinedTextField.swift
//  iosApp
//
//  Created by Sergey Ivanov on 13.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct UnderlineTextField: View {
    @Binding var text: String
    var fieldType: TextFieldType
    private var title: String
    private let keyboardType: UIKeyboardType
    private let regex: String
    
    @FocusState private var isFocused: Bool
    @State private var isPlaceholderVisible: Bool = true
    
    init(text: Binding<String>, fieldType: TextFieldType) {
        self._text = text
        self.fieldType = fieldType
        
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
            regex = ""
        }
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            Text(title)
                .font(.paragraph5)
                .foregroundColor(isFocused || !text.isEmpty ? .grayDarkColor : .clear)
                .animation(.easeInOut(duration: 0.2), value: isFocused || !text.isEmpty)
            
            ZStack(alignment: .leading) {
                if isPlaceholderVisible {
                    Text(title)
                        .font(.paragraph5)
                        .foregroundColor(.grayTextColor)
                        .onTapGesture {
                            self.isFocused = true
                        }
                }
                
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
            }
            .overlay(
                Rectangle()
                    .frame(height: 1)
                    .foregroundColor(isFocused ? .blueColor : .grayDarkColor),
                alignment: .bottom
            )
        }
    }
}
