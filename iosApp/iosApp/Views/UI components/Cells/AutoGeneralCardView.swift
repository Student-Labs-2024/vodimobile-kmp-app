//
//  AutoGeneralCardView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 30.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct AutoGeneralCardView: View {
    @Binding var selectedAuto: Car
    @Binding var showModalReservation: Bool
    @Binding var showSignSuggestModal: Bool

    var body: some View {
        VStack(spacing: 12) {
            Image(resource: \.more_cars)
                .resizable()
                .aspectRatio(contentMode: .fit)
                .padding(.horizontal, 25)

            HStack {
                Text(R.string.localizable.generalAutoCardTitle).font(.header4)
                Spacer()
                NavigationLink(destination: AutoListView(
                    selectedAuto: $selectedAuto,
                    showModalReservation: $showModalReservation,
                    showSignSuggestModal: $showSignSuggestModal
                )
                ) {
                    Image.rightArrowCircleFill
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 30, height: 30)
                        .foregroundStyle(Color(R.color.blueColor))
                }

            }
        }
        .padding(.horizontal, 32)
        .padding(.vertical, 24)
        .background(RoundedRectangle(cornerRadius: 24).fill(Color(R.color.container)))
    }
}
