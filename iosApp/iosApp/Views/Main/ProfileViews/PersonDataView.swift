//
//  PersonDataView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 11.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct PersonDataView: View {
    @ObservedObject private var viewModel: PersonalDataViewModel
    @FocusState private var focusedField: Field?
    
    enum Field {
        case fullname, email, phone
    }
    
    init(viewModel: PersonalDataViewModel = .init()) {
        self.viewModel = viewModel
    }
    
    var body: some View {
        VStack {
            VStack(alignment: .leading, spacing: 25) {
                Text(R.string.localizable.yourDataText).font(.header3)
                
                UnderlineTextField(
                    text: $viewModel.fullname,
                    isValid: $viewModel.isFullnameValid,
                    fieldType: .fullName
                )
                .focused($focusedField, equals: .fullname)
                
                ButtonLikeTextFieldView()
                
                UnderlineTextField(
                    title: R.string.localizable.phone(),
                    text: viewModel.phone,
                    fieldType: .mock
                )
            }
            .padding(.horizontal, 32)
            .padding(.vertical, 40)
            .background(Color.white)
            .cornerRadius(20)
            .padding(.horizontal, 16)
            .padding(.top, 50)
            
            Spacer()
        }
        .onChange(of: viewModel.fullname) { _ in
            $viewModel.dataIsEditing.wrappedValue = true
        }
        .loadingOverlay(isLoading: $viewModel.isLoading)
        .alert(R.string.localizable.alertErrorSavingTitle(), isPresented: $viewModel.showErrorAlert) {
            Button(R.string.localizable.closeButton(), role: .cancel) {
                viewModel.showErrorAlert.toggle()
            }
        } message: {
            Text(R.string.localizable.alertErrorSavingText)
        }
        .alert(R.string.localizable.alertSavePersonDataTitle(), isPresented: $viewModel.dataHasBeenSaved) {
            Button(R.string.localizable.closeButton(), role: .cancel) {
                viewModel.dataHasBeenSaved.toggle()
            }
        }
        .background(Color(R.color.grayLightColor).ignoresSafeArea())
        .navigationBarBackButtonHidden()
        .toolbar {
            CustomToolbar(
                title: R.string.localizable.profileScreenTitle,
                trailingToolbarItem: TrailingToolbarItem(
                    image: Image.checkmark,
                    observedObject: viewModel,
                    actionAfterTapping: {
                        Task {
                            viewModel.dataIsEditing = false
                            focusedField = nil
                            await viewModel.saveEditedUserData()
                            await viewModel.loadUserData()
                        }
                    }
                )
            )
        }
    }
}

#Preview {
    PersonDataView()
}
