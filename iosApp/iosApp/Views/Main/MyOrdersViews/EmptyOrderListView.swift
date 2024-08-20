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
    var onRefresh: () -> Void

    var body: some View {
        RefreshableScrollView(
            showsIndicators: false,
            shouldTriggerHapticFeedback: true,
            loadingViewBackgroundColor: .clear,
            threshold: 50,
            onRefresh: { _ in
                onRefresh()
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
