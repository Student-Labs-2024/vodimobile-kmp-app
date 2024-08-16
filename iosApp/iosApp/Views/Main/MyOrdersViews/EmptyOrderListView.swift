//
//  EmptyOrderListView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 16.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct EmptyOrderListView: View {
    var body: some View {
        VStack {
            Image(R.image.ordersTag)
                .resizable()
                .aspectRatio(contentMode: .fit)
                .padding(.horizontal, 100)
            VStack(spacing: 8) {
                Text(R.string.localizable.emptyDataTitle)
                    .font(.paragraph1)
                Text(R.string.localizable.emptyDataText)
                    .font(.paragraph5)
            }
        }
        .padding(.top, screenHeight / 5)
    }
}

#Preview {
    EmptyOrderListView()
}
