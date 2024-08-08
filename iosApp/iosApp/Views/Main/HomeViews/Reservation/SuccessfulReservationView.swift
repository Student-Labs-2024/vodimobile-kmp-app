//
//  SuccessfulReservationView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 08.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct SuccessfulReservationView: View {
    var body: some View {
        VStack(spacing: 36) {
            Image(R.image.successCoin)
                .resizable()
                .aspectRatio(contentMode: .fit)
                .foregroundStyle(.black)
                .padding(.horizontal, 110)
                .padding(.top, 20)
            
            VStack(alignment: .center, spacing: 24) {
                Text(R.string.localizable.requestSubmittedTitle)
                    .font(.header1).foregroundStyle(.black)
                    .multilineTextAlignment(.center)
                Text(R.string.localizable.requestSubmittedText)
                    .font(.paragraph2)
                    .foregroundStyle(.black)
                    .multilineTextAlignment(.center)
            }
            .padding(.horizontal, horizontalPadding)
            .padding(.vertical, 12)
            
            NavigationLink(R.string.localizable.myOrdersScreenTitle(), destination: {
                MainTabbarView()
                // TODO: - Make to move back on rootViewController
            })
            .buttonStyle(FilledBtnStyle())
        }
        .padding(.horizontal, horizontalPadding)
        .padding(.vertical, 50)
    }
}

#Preview {
    SuccessfulReservationView()
}
