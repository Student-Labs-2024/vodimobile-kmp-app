//
//  ActiveOrderView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 17.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import SwiftUIPullToRefresh
import shared

struct OrdersListView: View {
    @Binding var ordersList: [Order]
    @Binding var selectedOrder: Order
    @Binding var showOrderModal: Bool
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
                LazyVStack(spacing: 20) {
                    ForEach(ordersList, id: \.self) { order in
                        OrderCell(order: order)
                            .onTapGesture {
                                selectedOrder = order
                                showOrderModal.toggle()
                            }
                    }
                }
            }
    }
}
