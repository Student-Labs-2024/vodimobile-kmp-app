//
//  AuthScreenView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 09.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct AuthScreenView: View {
    @State private var phoneFieldText = ""
    @State private var phoneIsValid: Bool = false
    @State private var checkboxSelected: Bool = false
    @State private var isButtonEnabled: Bool = false
    
    @Environment(\.dismiss) private var dismiss
    
    var body: some View {
        VStack(spacing: 18) {
            VStack(spacing: 18) {
                CustomTextFieldView(fieldContent: $phoneFieldText, isValid: $phoneIsValid, fieldType: .phone)
                    .onChange(of: phoneIsValid) { _ in
                        toggleButtonEnabled()
                    }
                
                NavigationLink(destination: PinCodeView(phoneNumber: $phoneFieldText)) {
                    Text(String.Buttons.nextButton)
                }
                .buttonStyle(FilledBtnStyle())
                .disabled(!isButtonEnabled)
            }
            
            HStack(spacing: 16) {
                CheckboxView(isChecked: $checkboxSelected)
                    .padding(.leading, 12)
                    .onChange(of: checkboxSelected) { _ in
                        toggleButtonEnabled()
                    }
                
                VStack(alignment: .leading) {
                    Text(LocalizedStringKey("conditionText"))
                        .font(.paragraph5)
                        .foregroundStyle(Color.grayDarkColor)
                    
                    NavigationLink(destination: ConditionScreenView()) {
                        Text(LocalizedStringKey("conditionLink"))
                            .foregroundColor(.blueColor)
                            .font(.buttonCheckBox)
                    }
                }
                Spacer()
            }
            Spacer()
        }
        .padding(.horizontal, 16)
        .padding(.top, 120)
        .navigationBarBackButtonHidden()
        .toolbar {
            CustomToolbar(title: String.ScreenTitles.authScreenTitle)
        }
    }
    
    private func toggleButtonEnabled() {
        isButtonEnabled = phoneIsValid && checkboxSelected
    }
}

#Preview {
    AuthScreenView()
}
