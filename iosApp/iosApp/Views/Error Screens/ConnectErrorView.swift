//
//  ClientErrorView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 22.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ConnectErrorView: View {
    var body: some View {
        VStack(spacing: 36) {
            Image(R.image.clientError)
                .resizable()
                .aspectRatio(contentMode: .fit)
                .foregroundStyle(.black)
                .padding(.horizontal, 110)
                .padding(.top, 20)
            
            VStack(alignment: .center, spacing: 24) {
                Text(R.string.localizable.connectErrorTitle).font(.header1).foregroundStyle(.black)
                    .multilineTextAlignment(.center)
                Text(R.string.localizable.connectErrorText)
                    .font(.paragraph2).foregroundStyle(.black)
                    .multilineTextAlignment(.center)
            }
            .padding(.horizontal, horizontalPadding)
            .padding(.vertical, 12)
            
            Spacer()
            
            Button(R.string.localizable.reconnectButton()) {
                // TODO: - add logic for repeat to connect with server
            }
            .buttonStyle(BorderedBtnStyle())
        }
        .padding(.horizontal, horizontalPadding)
        .padding(.vertical, 50)
    }
}

#Preview {
    ConnectErrorView()
}
