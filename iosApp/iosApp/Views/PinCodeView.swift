//
//  ConfirmationScreenView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 10.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct PinCodeView: View {
    @State private var pin: [String] = ["", "", "", ""]
    @FocusState private var focusedField: Int?
    @State private var isButtonEnabled: Bool = false
    @Binding var phoneNumber: String
    
    @Environment(\.dismiss) private var dismiss
    
    private var sendCodeOnPhoneText: String {
        "\(R.string.localizable.sendCodeMsg.callAsFunction()) \n \(phoneNumber)"
    }
    
    var body: some View {
        VStack(spacing: PinCodeConfig.spacingBetweenGroupAndResendText) {
            VStack(spacing: PinCodeConfig.spacingBetweenMainComponents) {
                Text(R.string.localizable.inputCodeText)
                    .font(.header2)
                    .padding(.top, PinCodeConfig.contentTopPadding)
                    .foregroundColor(Color.black)
                    .multilineTextAlignment(.center)
                
                Text(sendCodeOnPhoneText)
                    .font(.paragraph2)
                    .foregroundColor(Color.grayTextColor)
                    .multilineTextAlignment(.center)
                
                HStack(spacing: PinCodeConfig.spacingBetweenPincodeCells) {
                    ForEach(0..<4) { index in
                        TextField("", text: $pin[index])
                            .keyboardType(.numberPad)
                            .multilineTextAlignment(.center)
                            .font(.paragraph1)
                            .frame(width: PinCodeConfig.pincodeCellsSize, height: PinCodeConfig.pincodeCellsSize)
                            .background(
                                RoundedRectangle(cornerRadius: 12, style: .continuous).fill(Color.grayLightColor)
                            )
                            .overlay(
                                RoundedRectangle(cornerRadius: 12)
                                    .stroke(focusedField == index ? Color.blueColor : Color.grayDarkColor, lineWidth: pin[index].isEmpty && focusedField != index ? 0 : 2)
                            )
                            .focused($focusedField, equals: index)
                            .onChange(of: pin[index]) { newValue in
                                if newValue.count == 1, index < 3 {
                                    focusedField = index + 1
                                } else if newValue.isEmpty, index > 0 {
                                    focusedField = index - 1
                                }
                            }
                    }
                }
                .padding(.vertical, PinCodeConfig.verticalSpacingBetweenPincodeField)
                .onChange(of: pin) { _ in
                    toggleButtonEnabled()
                }
                
                NavigationLink(destination: MainScreenView()) {
                    Text(R.string.localizable.nextBtnName)
                }
                .buttonStyle(FilledBtnStyle())
                .disabled(!isButtonEnabled)
            }
            
            HStack {
                Text(R.string.localizable.notGetCodeText)
                    .foregroundColor(.black)
                    .font(.paragraph4)
                Button(action: {
                    print("Отправить код повторно нажат")
                }) {
                    Text(R.string.localizable.resendBtnText)
                        .foregroundColor(.blueColor)
                        .font(.buttonText)
                        .underline()
                }
            }
            
            Spacer()
        }
        .padding()
        .onAppear {
            focusedField = 0
        }
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
                Text(R.string.localizable.confirmScreenTitle)
                    .font(.header1)
                    .foregroundColor(Color.black)
            }
        }
    }
    
    private func toggleButtonEnabled() {
        isButtonEnabled = pin.joined().count == 4
    }
}
