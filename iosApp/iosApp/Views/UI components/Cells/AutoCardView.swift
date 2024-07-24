//
//  AutoCardView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 24.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

enum AutoCardType {
    case simple, general
}

struct AutoCardView: View {
    let image: Image
    let title: String
    let price: String?
    let cardType: AutoCardType
    let trailingIcon: Image?
    
    init(
        image: Image,
        title: String,
        price: String?,
        cardType: AutoCardType,
        trailingIcon: Image? = nil
    ) {
        self.image = image
        self.title = title
        self.price = price
        self.cardType = cardType
        self.trailingIcon = trailingIcon
    }
    
    var body: some View {
        VStack(spacing: 12) {
            image
                .resizable()
                .aspectRatio(contentMode: .fit)
            
            HStack {
                VStack(alignment: .leading, spacing: 8) {
                    Text(title).font(cardType == .simple ? .header3 : .header4)
                    if let autoPrice = price {
                        Text(autoPrice)
                            .font(.header4)
                            .fontWeight(.bold)
                            .foregroundStyle(Color(R.color.blueColor))
                            .multilineTextAlignment(.leading)
                    }
                }
                Spacer()
                if let trailingIcon = trailingIcon {
                    trailingIcon
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 32, height: 32)
                        .foregroundStyle(Color(R.color.blueColor))
                } else {
                    
                    ZStack {
                        Image.infoCircleFill
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                            .foregroundStyle(Color(R.color.grayDarkColor))
                            .frame(width: 20, height: 20)
                    }
                    .frame(width: 40, height: 40)
                    .background(RoundedRectangle(cornerRadius: 10).fill(Color(R.color.grayLightColor)))
                }
            }
        }
        .padding(.horizontal, 32)
        .padding(.vertical, 24)
        .background(RoundedRectangle(cornerRadius: 24).fill(.white))
    }
}
