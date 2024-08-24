//
//  PinCodeViewModel.swift
//  iosApp
//
//  Created by Sergey Ivanov on 20.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

final class PinCodeViewModel: ObservableObject {
    @Published var pin: [String] = ["", "", "", ""]
    @Published var isButtonEnabled: Bool = false
    @Binding var showSignSuggestModal: Bool
    let authFlowType: AuthFlowType
    var sendCodeOnPhoneText: String {
        "\(R.string.localizable.sendCodeMsg())\n\(phoneNumber)"
    }
    let fullname: String = ""
    let phoneNumber: String
    let pass: String
    let authManager = AuthManager.shared

    init(
        showSignSuggestModal: Binding<Bool>,
        authFlowType: AuthFlowType,
        phoneNumber: String,
        pass: String
    ) {
        self._showSignSuggestModal = showSignSuggestModal
        self.authFlowType = authFlowType
        self.phoneNumber = phoneNumber
        self.pass = pass
    }
}
