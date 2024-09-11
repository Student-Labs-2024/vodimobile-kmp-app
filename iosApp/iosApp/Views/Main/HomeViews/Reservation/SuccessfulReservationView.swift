//
//  SuccessfulReservationView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 08.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct SuccessfulReservationView: View {
    @Binding var showModal: Bool
    @Binding var selectedTab: TabType

    var body: some View {
        VStack(spacing: 36) {
            HStack {
                Button(action: {
                    showModal.toggle()
                }, label: {
                    Image.xmark
                        .resizable()
                        .foregroundStyle(Color(R.color.text))
                        .fontWeight(.bold)
                        .frame(width: 18, height: 18)
                })
                .padding(.top, 10)
                .padding(.horizontal, 5)
                Spacer()
            }

            Image(R.image.successCoin)
                .resizable()
                .aspectRatio(contentMode: .fit)
                .foregroundStyle(Color(R.color.text))
                .padding(.horizontal, 110)
                .padding(.top, 40)

            VStack(alignment: .center, spacing: 24) {
                Text(R.string.localizable.requestSubmittedTitle)
                    .font(.header1)
                    .foregroundStyle(Color(R.color.text))
                    .multilineTextAlignment(.center)
                Text(R.string.localizable.requestSubmittedText)
                    .font(.paragraph2)
                    .foregroundStyle(Color(R.color.text))
                    .multilineTextAlignment(.center)
            }
            .padding(.horizontal, horizontalPadding)
            .padding(.vertical, 12)

            Button(R.string.localizable.myOrdersScreenTitle(), action: {
                showModal.toggle()
                selectedTab = .myOrders
            })
            .buttonStyle(FilledBtnWithoutDisabledStyle())

            Spacer()
        }
        .navigationBarBackButtonHidden()
        .padding(.horizontal, horizontalPadding)
    }
}

#Preview {
    SuccessfulReservationView(showModal: Binding.constant(true), selectedTab: Binding.constant(.main))
}
