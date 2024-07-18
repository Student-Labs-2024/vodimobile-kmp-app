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
        VStack(spacing: AuthAndRegScreensConfig.spacingBetweenGroupAndCheckbox) {
            VStack(spacing: AuthAndRegScreensConfig.spacingBetweenComponents) {
                CustomTextFieldView(fieldContent: $phoneFieldText, isValid: $phoneIsValid, fieldType: .phone)
                    .onChange(of: phoneIsValid) { _ in
                        toggleButtonEnabled()
                    }
                
                NavigationLink(destination: PinCodeView(phoneNumber: $phoneFieldText)) {
                    Text(R.string.localizable.nextBtnName)
                }
                .buttonStyle(FilledBtnStyle())
                .disabled(!isButtonEnabled)
            }
            
            HStack(spacing: spacingBetweenCheckboxAndText) {
                CheckboxView(isChecked: $checkboxSelected)
                    .onChange(of: checkboxSelected) { _ in
                        toggleButtonEnabled()
                    }
                
                VStack(alignment: .leading) {
                    Text(R.string.localizable.conditionText)
                        .font(.paragraph5)
                        .foregroundStyle(Color.grayDarkColor)
                    
                    NavigationLink(destination: ConditionScreenView()) {
                        Text(R.string.localizable.conditionLink)
                            .foregroundColor(.blueColor)
                            .font(.buttonCheckBox)
                    }
                }
                Spacer()
            }
            Spacer()
        }
        .padding(.horizontal, horizontalPadding)
        .padding(.top, аuthScreencontentTopPadding)
        .navigationBarBackButtonHidden()
        .toolbar {
            ToolbarItem(placement: .navigationBarLeading){
                Button(action: {
                    dismiss()
                }, label: {
                    Image(systemName: "chevron.left").foregroundStyle(Color.black).fontWeight(.bold)
                })
            }
            
            ToolbarItem(placement: .principal) {
                Text(R.string.localizable.authScreenTitle)
                    .font(.header1)
                    .foregroundColor(Color.black)
            }
        }
    }
    
    private func toggleButtonEnabled() {
        isButtonEnabled = phoneIsValid && checkboxSelected
    }
}

#Preview {
    AuthScreenView()
}