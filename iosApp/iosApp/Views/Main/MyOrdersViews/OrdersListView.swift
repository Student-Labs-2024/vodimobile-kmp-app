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
    @ObservedObject var dataStorage = KMPDataStorage.shared
    let onRefresh: () async -> Void

    var body: some View {
        RefreshableScrollView(
            showsIndicators: false,
            loadingViewBackgroundColor: .clear,
            threshold: 50,
            action: {
                await onRefresh()
            },
            progress: { state in
                RefreshActivityIndicator(isAnimating: state == .loading) {
                    $0.hidesWhenStopped = false
                }
            }) {
                LazyVStack(spacing: 20) {
                    ForEach(ordersList, id: \.orderId) { order in
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
