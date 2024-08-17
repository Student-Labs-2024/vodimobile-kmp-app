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
    private let statusColor = { (status: CarStatus) -> SwiftUI.Color in
        switch onEnum(of: status) {
        case .approved(let title):
            return Color(R.color.approvedTag)
        case .completed(let title):
            return Color(R.color.completedTag)
        case .cancelled(let title):
            return Color(R.color.rejectedTag)
        case .processing(let title):
            return Color(R.color.processingTag)
        }
    }
    
    var body: some View {
        HStack(spacing: 10) {
            VStack {
                HStack {
                    Text(order.status.title.resource)
                        .font(.caption2)
                        .padding(.horizontal, 12)
                        .padding(.vertical, 6)
                        .background(
                            RoundedRectangle(cornerRadius: 6)
                                .fill(statusColor(order.status))
                        )
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
                Text("\(order.rentalDatePeriod.startDate) - \(order.rentalDatePeriod.endDate)")
                    .font(.paragraph6)
                Text("\(Int(order.bid.cost)) \(R.string.localizable.currencyText())")
                    .font(.paragraph6)
            }
            
            Image.chevronRight
                .foregroundStyle(Color(R.color.grayDark))
        }
        .padding(.vertical, 20)
        .padding(.horizontal, 16)
        .background(RoundedRectangle(cornerRadius: 10).fill(.white))
    }
}

