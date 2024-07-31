//
//  ResetPasswordView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 30.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ResetPasswordView: View {
    var body: some View {
        VStack {
            Spacer()
            Text("Hello, ResetPasswordView!")
                .frame(maxWidth: .infinity)
            Spacer()
        }
        .background(Color(R.color.grayLightColor))
        .navigationBarBackButtonHidden()
        .toolbar {
            CustomToolbar(title: R.string.localizable.resetPassScreenTitle)
        }
    }
}

#Preview {
    ResetPasswordView()
}
