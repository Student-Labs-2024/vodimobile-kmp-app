//
//  FailureReservationView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 08.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct FailureReservationView: View {
    var body: some View {
        VStack(spacing: 36) {
            HStack {
                NavigationLink(destination: MainTabbarView()) {
                    Image.chevronLeft
                        .resizable()
                        .foregroundStyle(Color(R.color.text))
                        .fontWeight(.bold)
                        .frame(width: 12, height: 18)
                }
                .padding(.top, 10)
                .padding(.horizontal, 5)
                Spacer()
            }

            Image(R.image.failureCoin)
                .resizable()
                .aspectRatio(contentMode: .fit)
                .foregroundStyle(Color(R.color.text))
                .padding(.horizontal, 110)
                .padding(.top, 40)

            VStack(alignment: .center, spacing: 24) {
                Text(R.string.localizable.requestFailedTitle)
                    .font(.header1).foregroundStyle(.black)
                    .multilineTextAlignment(.center)
                Text(R.string.localizable.requestFailedText)
                    .font(.paragraph2)
                    .foregroundStyle(Color(R.color.text))
                    .multilineTextAlignment(.center)
            }
            .padding(.horizontal, horizontalPadding)
            .padding(.vertical, 12)

            NavigationLink(R.string.localizable.reconnectButton(), destination: {
                MainTabbarView()
                // TODO: - Make to move back on rootViewController
            })
            .buttonStyle(BorderedBtnStyle())

            Spacer()
        }
        .navigationBarBackButtonHidden()
        .padding(.horizontal, horizontalPadding)
    }
}

#Preview {
    FailureReservationView()
}
