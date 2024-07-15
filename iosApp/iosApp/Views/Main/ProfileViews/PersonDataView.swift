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
    @State private var isLoading: Bool = false
    @State private var showAlert: Bool = false
    
    var body: some View {
        ZStack {
            VStack {
                VStack(alignment: .leading, spacing: 25) {
                    Text("Ваши данные").font(.header3)
                    
                    UnderlineTextField(text: $userInput.fullName, fieldType: .fullName)
                    UnderlineTextField(text: $userInput.email, fieldType: .email)
                    UnderlineTextField(text: $userInput.phone, fieldType: .phone)
                }
                .padding(.horizontal, 32)
                .padding(.vertical, 40)
                .background(Color.white)
                .cornerRadius(20)
                .padding(.horizontal, 16)
                .padding(.top, 50)
                
                Spacer()
            }
            .alert(LocalizedStringKey("alertSavePersonDataText"), isPresented: $showAlert) {
                Button(LocalizedStringKey("closeButton"), role: .cancel) {
                    showAlert.toggle()
                }
            }
            
            if isLoading {
                ZStack {
                    RoundedRectangle(cornerRadius: 8, style: .circular)
                        .opacity(0.5)
                        .frame(width: 160, height: 160)
                    ProgressView()
                        .progressViewStyle(.circular)
                        .tint(.white)
                    .scaleEffect(2)
                }
            }
        }
        .background(Color.grayLightColor.ignoresSafeArea())
        .navigationBarBackButtonHidden()
        .toolbar {
            CustomToolbar(
                title: String.ScreenTitles.profileScreenTitle,
                trailingToolbarItem: TrailingToolbarItem(
                    image: Image(systemName: "checkmark"),
                    control: $userInput,
                    actionAfterTapping: makeFakeNetworkRequest)
            )
        }
    }
    
    private func makeFakeNetworkRequest() {
        isLoading = true
        
        DispatchQueue.main.asyncAfter(deadline: .now() + 3) {
            isLoading = false
            showAlert = true
        }
    }
}

struct UserInputData {
    var fullName: String = ""
    var email: String = ""
    var phone: String = ""
    
    func checkEmpty() -> Bool {
        email.isEmpty && fullName.isEmpty && phone.isEmpty
    }
}

#Preview {
    PersonDataView()
}
