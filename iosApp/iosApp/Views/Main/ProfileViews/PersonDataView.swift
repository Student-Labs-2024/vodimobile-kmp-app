//
//  PersonDataView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 11.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct PersonDataView: View {
    @ObservedObject var viewModel: PersonalDataViewModel
    
    init(viewModel: PersonalDataViewModel = .init()) {
        self.viewModel = viewModel
    }
    
    var body: some View {
        ZStack {
            VStack {
                VStack(alignment: .leading, spacing: 25) {
                    Text(R.string.localizable.yourDataText).font(.header3)
                    
                    UnderlineTextField(text: $viewModel.userInput.fullName, fieldType: .fullName)
                    UnderlineTextField(text: $viewModel.userInput.email, fieldType: .email)
                    UnderlineTextField(text: $viewModel.userInput.phone, fieldType: .phone)
                }
                .padding(.horizontal, 32)
                .padding(.vertical, 40)
                .background(Color.white)
                .cornerRadius(20)
                .padding(.horizontal, 16)
                .padding(.top, 50)
                
                Spacer()
            }
            .alert(R.string.localizable.alertSavePersonDataTitle(), isPresented: $viewModel.showAlert) {
                Button(R.string.localizable.closeButton(), role: .cancel) {
                    $viewModel.showAlert.wrappedValue.toggle()
                }
            } message: {
                Text(R.string.localizable.alertSavePersonDataText)
            }
            
            if $viewModel.isLoading.wrappedValue {
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
        .background(Color(R.color.grayLightColor).ignoresSafeArea())
        .navigationBarBackButtonHidden()
        .toolbar {
            CustomToolbar(
                title: R.string.localizable.profileScreenTitle,
                trailingToolbarItem: TrailingToolbarItem(
                    image: Image(systemName: "checkmark"),
                    control: $viewModel.userInput,
                    actionAfterTapping: viewModel.makeFakeNetworkRequest)
            )
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
