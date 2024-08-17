//
//  MyOrdersView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 11.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct MyOrdersView: View {
    @State private var selectedTab: MyOrderTab = .active
    @State var selectedOrder: Order = Order.companion.empty()
    @State var showOrderModal: Bool = false
    @ObservedObject var viewModel = MyOrdersViewModel()
    
    var body: some View {
        VStack(spacing: 20) {
            OrdersTopPickerView(selectedTab: $selectedTab)
            
            switch selectedTab {
            case .active:
                if !viewModel.activeOrderList.isEmpty {
                    OrdersListView(
                        ordersList: $viewModel.activeOrderList
                    ) {
                        viewModel.getAllOrders()
                    }
                } else {
                    EmptyOrderListView()
                }
            case .completed:
                if !viewModel.completedOrderList.isEmpty {
                    OrdersListView(
                        ordersList: $viewModel.completedOrderList
                    ) {
                        viewModel.getAllOrders()
                    }
                } else {
                    EmptyOrderListView()
                }
            }
            
            Spacer()
        }
        .fullScreenCover(isPresented: $showOrderModal, content: {
            OrderDetailView(
                order: selectedOrder,
                showOrderModal: $showOrderModal
            )
        })
        .padding(.horizontal, horizontalPadding)
        .background(Color(R.color.grayLight))
    }
}

#Preview {
    MyOrdersView()
}
