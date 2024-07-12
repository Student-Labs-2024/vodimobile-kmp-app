//
//  RegistrationScreenView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 09.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct RegistrationScreenView: View {
    @State private var emailFieldText = ""
    @State private var emailIsValid = false
    @State private var phoneFieldText = ""
    @State private var phoneIsValid = false
    @State private var checkboxSelected = false
    @State private var isButtonEnabled: Bool = false
    
    @Environment(\.dismiss) private var dismiss
    
    var body: some View {
        VStack(spacing: 10) {
            VStack(spacing: 18) {
                CustomTextFieldView(fieldContent: $emailFieldText, isValid: $emailIsValid, fieldType: .email)
                    .onChange(of: emailIsValid) { _ in
                        toggleButtonEnabled()
                    }
                
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
                CheckboxView(isChecked: $checkboxSelected).padding(.leading, 12)
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
            CustomToolbar(title: String.ScreenTitles.regScreenTitle)
        }
    }
    
    private func toggleButtonEnabled() {
        isButtonEnabled = emailIsValid && phoneIsValid && checkboxSelected
    }
}

#Preview {
    RegistrationScreenView()
}
