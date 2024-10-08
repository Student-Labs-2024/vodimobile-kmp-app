//
//  OrderCell.swift
//  iosApp
//
//  Created by Sergey Ivanov on 17.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct OrderCell: View {
    var order: Order

    var body: some View {
        HStack(spacing: 10) {
            VStack {
                HStack {
                    BidStatusText(status: order.status)
                    Spacer()
                }
                if let carImage = order.car.images.first {
                    Image(
                        ImageResource(
                            name: carImage.assetImageName,
                            bundle: carImage.bundle
                        )
                    )
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .padding(.horizontal, 25)
                }
            }

            VStack(alignment: .leading, spacing: 4) {
                Text(order.car.model.resource)
                    .font(.paragraph3)
                    .foregroundStyle(Color(R.color.text))
                Text(CustomDateFormatter.shared.formatDates(
                    startDateInMillis: order.rentalDatePeriod.startDate,
                    endDateInMillis: order.rentalDatePeriod.endDate)
                )
                .font(.paragraph6)
                .foregroundStyle(Color(R.color.text))
                Text("\(Int(order.bid.cost)) \(R.string.localizable.currencyText())")
                    .font(.paragraph6)
                    .foregroundStyle(Color(R.color.text))
            }

            Image.chevronRight
                .foregroundStyle(Color(R.color.grayDark))
        }
        .padding(.vertical, 20)
        .padding(.horizontal, 16)
        .background(RoundedRectangle(cornerRadius: 10).fill(Color(R.color.container)))

    }
}
