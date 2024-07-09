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
    case other
}

extension TextFieldType {
    var localizedStr: LocalizedStringKey {
        switch self {
        case .email:
            return LocalizedStringKey("email")
        case .phone:
            return LocalizedStringKey("phone")
        default:
            return ""
        }
    }
}

struct CustomTextFieldView: View {
    @State var fieldContent: String = ""
    @State var isEditing: Bool = false
    @FocusState private var isFocused: Bool
    
    private let fieldName: LocalizedStringKey
    private let placeholder: LocalizedStringKey
    
    init(fieldType: TextFieldType) {
        switch fieldType {
        case .email:
            fieldName = fieldType.localizedStr
            placeholder = "example@gmail.com"
        case .phone:
            fieldName = fieldType.localizedStr
            placeholder = "+7"
        default:
            fieldName = fieldType.localizedStr
            placeholder = ""
        }
    }
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(fieldName).font(.header4).foregroundStyle(Color.black)
            
            TextField(placeholder, text: $fieldContent)
                .textFieldStyle(CustomTextFieldStyle(isFocused: isFocused))
                .focused($isFocused)
                .onSubmit {
                    isFocused = false
                }.overlay(
                    HStack {
                        Spacer()
                        Button(action: {
                            self.fieldContent = ""
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
            
            
        }
        .padding(.all, 0)
    }
}

struct CustomTextFieldStyle: TextFieldStyle {
    var isFocused: Bool
    
    func _body(configuration: TextField<_Label>) -> some View {
        configuration
            .frame(alignment: .leading)
            .font(.paragraph4)
            .padding(16)
            .foregroundStyle(Color.black)
            .multilineTextAlignment(.leading)
            .background(Color.grayLightColor)
            .cornerRadius(12)
            .overlay(
                RoundedRectangle(cornerRadius: 10)
                    .stroke(Color.grayDarkColor, lineWidth: isFocused ? 1 : 0)
            )
    }
}

#Preview {
    CustomTextFieldView(fieldType: .email)
}
