//
//  ResetPasswordView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 31.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ResetPasswordPhoneView: View {
    @State private var isButtonEnabled: Bool = false
    @Binding var showSignSuggestModal: Bool
    @ObservedObject private var viewModel = UserDataViewModel()
    
    @Environment(\.dismiss) private var dismiss
    
    var body: some View {
        VStack(spacing: AuthAndRegScreensConfig.spacingBetweenGroupAndCheckbox) {
            VStack(spacing: AuthAndRegScreensConfig.spacingBetweenComponents) {
                VStack(spacing: 12) {
                    Text(R.string.localizable.resetScreenTitle)
                        .font(.header2)
                    
                    Text(R.string.localizable.resetScreenSubtitle)
                        .font(.paragraph2)
                        .foregroundStyle(Color(R.color.grayTextColor))
                }
                .padding(.vertical, 24)
            
                BorderedTextField(
                    fieldContent: $viewModel.phone,
                    isValid: $viewModel.isPhoneValid,
                    fieldType: .phone,
                    inputErrorType: $viewModel.inputError
                )
                .onChange(of: viewModel.isPhoneValid) { _ in
                    isButtonEnabled = true
                }
                
                NavigationLink(
                    destination: PinCodeView(
                        showSignSuggestModal: $showSignSuggestModal,
                        authFlowType: .resetPassword,
                        phoneNumber: viewModel.phone,
                        pass: viewModel.password
                    )
                ) {
                    Text(R.string.localizable.nextBtnName)
                }
                .buttonStyle(FilledBtnStyle())
                .disabled(!isButtonEnabled)
                .padding(.vertical, 10)
            }
            Spacer()
        }
        .padding(.horizontal, horizontalPadding)
        .padding(.top, аuthScreencontentTopPadding)
        .navigationBarBackButtonHidden()
        .toolbar {
            CustomToolbar(title: R.string.localizable.resetPassScreenTitle)
        }
    }
}

#Preview {
    ResetPasswordPhoneView(showSignSuggestModal: Binding.constant(false))
}
