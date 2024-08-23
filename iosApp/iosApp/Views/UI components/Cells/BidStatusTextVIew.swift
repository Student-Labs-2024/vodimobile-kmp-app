//
//  BidStatusTextVIew.swift
//  iosApp
//
//  Created by Sergey Ivanov on 18.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct BidStatusText: View {
    let status: CarStatus
    private let statusColor = { (status: CarStatus) -> SwiftUI.Color in
        switch onEnum(of: status) {
        case .approved(let title):
            return Color(R.color.approvedTag)
        case .reserve(let title):
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
        Text(status.title.resource)
            .font(.caption2)
            .foregroundStyle(.black)
            .padding(.horizontal, 12)
            .padding(.vertical, 6)
            .background(
                RoundedRectangle(cornerRadius: 6)
                    .fill(statusColor(status))
            )
    }
}
