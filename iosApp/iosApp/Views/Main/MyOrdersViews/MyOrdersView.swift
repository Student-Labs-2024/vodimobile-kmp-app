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
    @Binding var selectedMainTab: TabType
    @Binding var showDatePicker: Bool
    @State private var selectedTab: MyOrderTab = .active
    @State var selectedOrder: Order = Order.companion.empty()
    @State var showOrderModal: Bool = false
    @ObservedObject var viewModel = MyOrdersViewModel()

    var body: some View {
        NavigationStack {
            VStack(spacing: 20) {
                OrdersTopPickerView(selectedTab: $selectedTab)

                switch selectedTab {
                case .active:
                    if !viewModel.activeOrderList.isEmpty {
                        OrdersListView(
                            ordersList: $viewModel.activeOrderList,
                            selectedOrder: $selectedOrder,
                            showOrderModal: $showOrderModal
                        ) {
                            await viewModel.getAllOrders()
                        }
                    } else {
                        EmptyOrderListView {
                            await viewModel.getAllOrders()
                        }
                    }
                case .completed:
                    if !viewModel.completedOrderList.isEmpty {
                        OrdersListView(
                            ordersList: $viewModel.completedOrderList,
                            selectedOrder: $selectedOrder,
                            showOrderModal: $showOrderModal
                        ) {
                            Task {
                               await viewModel.getAllOrders()
                            }
                        }
                    } else {
                        EmptyOrderListView {
                            Task {
                               await viewModel.getAllOrders()
                            }
                        }
                    }
                }

                Spacer()
            }
            .loadingOverlay(isLoading: $viewModel.isLoading)
            .fullScreenCover(isPresented: $showOrderModal, content: {
                OrderDetailView(
                    order: selectedOrder,
                    showOrderModal: $showOrderModal,
                    selectedTab: $selectedMainTab,
                    showDatePicker: $showDatePicker
                )
            })
            .padding(.horizontal, horizontalPadding)
            .background(Color(R.color.grayLight))
        }
        .onAppear {
            Task {
                await viewModel.getAllOrders()
            }
        }
    }
}

#Preview {
    MyOrdersView(selectedMainTab: Binding.constant(.main), showDatePicker: Binding.constant(false))
}
