//
//  EmptyOrderListView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 16.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import SwiftUIPullToRefresh

struct EmptyOrderListView: View {
    let refreshAction: () async -> Void

    var body: some View {
        RefreshableScrollView(
            showsIndicators: false,
            loadingViewBackgroundColor: .clear,
            threshold: 50,
            action: {
                await refreshAction()
            },
            progress: { state in
                RefreshActivityIndicator(isAnimating: state == .loading) {
                    $0.hidesWhenStopped = false
                }
            }) {
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
}

#Preview {
    EmptyOrderListView {
        print("refreshed")
    }
}
