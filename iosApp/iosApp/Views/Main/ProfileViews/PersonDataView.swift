//
//  PersonDataView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 11.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct PersonDataView: View {
    @State private var userInput = UserInputData()
    
    var body: some View {
        VStack {
            VStack(alignment: .leading, spacing: 25) {
                Text("Ваши данные").font(.header3)
                
                UnderlineTextField(text: $userInput.fullName, title: "ФИО", placeholder: "ФИО")
                UnderlineTextField(text: $userInput.email, title: "Почта", placeholder: "Почта")
                UnderlineTextField(text: $userInput.phone, title: "Телефон", placeholder: "Номер телефона")
            }
            .padding(.horizontal, 32)
            .padding(.vertical, 40)
            .background(Color.white)
            .cornerRadius(20)
            .padding(.horizontal, 16)
            .padding(.top, 50)
            
            Spacer()
        }
        .background(Color.grayLightColor.ignoresSafeArea())
        .navigationBarBackButtonHidden()
        .toolbar {
            CustomToolbar(title: String.ScreenTitles.profileScreenTitle)
            
            ToolbarItem(placement: .topBarTrailing) {
                Button(action: {}) {
                    Image(systemName: "checkmark")
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 18, height: 18)
                        .fontWeight(.bold)
                        .foregroundStyle(!userInput.phone.isEmpty && !userInput.email.isEmpty  && !userInput.fullName.isEmpty  ? Color.blueColor : Color.grayDarkColor)
                }
            }
        }
    }
}

struct UserInputData {
    var fullName: String = ""
    var email: String = ""
    var phone: String = ""
}

#Preview {
    PersonDataView()
}
