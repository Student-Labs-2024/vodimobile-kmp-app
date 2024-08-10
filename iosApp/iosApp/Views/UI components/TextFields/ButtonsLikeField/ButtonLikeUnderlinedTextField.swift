//
//  ButtonLikeTextFieldView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 01.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ButtonLikeUnderlinedTextField: View {
    var body: some View {
        NavigationLink(destination: ChangePasswordView()) {
            HStack {
                HStack {
                    Text(R.string.localizable.changePassword)
                        .font(.paragraph5)
                        .foregroundColor(Color(R.color.grayTextColor))
                        .padding(.bottom, 5)
                    Spacer()
                }
                .overlay(
                    Rectangle()
                        .frame(height: 1)
                        .foregroundColor(Color(R.color.grayDarkColor)),
                    alignment: .bottom
                )
                .frame(maxWidth: .infinity)
                
                Image.chevronRight
                    .padding(.vertical, 16)
                    .padding(.leading, 16)
                    .foregroundStyle(Color(R.color.grayDarkColor))
            }
        }
    }
}

#Preview {
    ButtonLikeUnderlinedTextField()
}
