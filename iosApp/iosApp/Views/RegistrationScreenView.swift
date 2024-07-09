//
//  RegistrationScreenView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 09.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct RegistrationScreenView: View {
    @State private var emailFieldFocused = false
    @State private var phoneFieldFocused = false
    @State private var emailFieldText = ""
    @State private var phoneFieldText = ""
    @State private var checkboxSelected = false
    
    @Environment(\.dismiss) private var dismiss
    
    var body: some View {
        NavigationLink(destination: MainScreenView()) {
            VStack(spacing: 18) {
                VStack(spacing: 28) {
                    CustomTextFieldView(fieldType: .email)
                        .onTapGesture {
                            emailFieldFocused = true
                        }
                    
                    CustomTextFieldView(fieldType: .phone)
                        .onTapGesture {
                            phoneFieldFocused = true
                        }
                    
                    NavigationLink(destination: RegistrationScreenView()) {
                        Text(LocalizedStringKey("nextBtnName"))
                    }
                    .buttonStyle(FilledBtnStyle())
                    .disabled(!emailFieldFocused || !phoneFieldFocused || !checkboxSelected)
                }
                
                HStack(spacing: 16) {
                    CheckboxView(isChecked: $checkboxSelected).padding(.leading, 12)
                    
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
            }
            .padding(.horizontal, 16)
            .navigationBarBackButtonHidden()
            .toolbar {
                ToolbarItem(placement: .navigationBarLeading){
                    Button(action: {
                        dismiss()
                    }, label: {
                        Image(systemName: "chevron.left").foregroundStyle(Color.black).fontWidth(.standard)
                    })
                }
                
                ToolbarItem(placement: .principal) {
                    Text(LocalizedStringKey("regScreenTitle"))
                        .font(.header1)
                        .foregroundColor(Color.black)
                }
            }
        }
    }
}

#Preview {
    RegistrationScreenView()
}
