//
//  AutoCardView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 24.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct AutoCardWithButtonView: View {
    let autoCard: AutoCard
    @Binding var showModal: Bool
    @Binding var selectedAuto: AutoCard
    
    var body: some View {
        VStack(spacing: 12) {
            autoCard.auto.image
                .resizable()
                .aspectRatio(contentMode: .fit)
            
            HStack {
                VStack(alignment: .leading, spacing: 8) {
                    Text(autoCard.auto.title).font(autoCard.cardType == .simple ? .header3 : .header4)
                    if let autoPrice = autoCard.auto.price {
                        Text(autoPrice)
                            .font(.header4)
                            .fontWeight(.bold)
                            .foregroundStyle(Color(R.color.blueColor))
                            .multilineTextAlignment(.leading)
                    }
                }
                Spacer()
            }
            
            HStack {
                Button(R.string.localizable.bookButton()) { }
                    .buttonStyle(FilledBtnStyle(heightButton: 40))
                    .padding(.trailing, 20)
                ZStack {
                    Image.infoCircleFill
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .foregroundStyle(Color(R.color.grayDarkColor))
                        .frame(width: 20, height: 20)
                }
                .frame(width: 40, height: 40)
                .background(RoundedRectangle(cornerRadius: 10).fill(Color(R.color.grayLightColor)))
                .onTapGesture {
                    selectedAuto = autoCard
                    showModal = true
                }
            }
        }
        .padding(.horizontal, 32)
        .padding(.vertical, 24)
        .background(RoundedRectangle(cornerRadius: 24).fill(.white))
    }
}
