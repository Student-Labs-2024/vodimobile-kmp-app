//
//  MyOrdersView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 11.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import SwiftUIPullToRefresh
import shared

struct MyOrdersView: View {
    @State private var selectedTab: MyOrderTab = .active
    @ObservedObject var viewModel = MyOrdersViewModel()
    
    var body: some View {
        VStack(spacing: 20) {
            OrdersTopPickerView(selectedTab: $selectedTab)
            
            switch selectedTab {
            case .active:
                if !viewModel.activeOrderList.isEmpty {
                    ActiveOrdersView(
                        ordersList: $viewModel.activeOrderList) {
                            viewModel.getAllOrders()
                        }
                } else {
                    EmptyOrderListView()
                }
            case .completed:
                if !viewModel.completedOrderList.isEmpty {
                    CompletedOrdersView()
                } else {
                    EmptyOrderListView()
                }
            }
            
            
            Spacer()
        }
        .padding(.horizontal, horizontalPadding)
        .background(Color(R.color.grayLight))
    }
}

struct ActiveOrdersView: View {
    @Binding var ordersList: [OrderDTO]
    var onRefresh: () -> ()
    
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
                    }
                }
            }
    }
}

struct CompletedOrdersView: View {
    var body: some View {
        Text("Завершенные заказы")
            .font(.largeTitle)
            .padding()
    }
}


#Preview {
    MyOrdersView()
}

struct OrderCell: View {
    var order: OrderDTO
    private let statusColor: (String) -> SwiftUI.Color = { (status: String) -> SwiftUI.Color in
        switch status {
        case CarStatus.Approved().title.resource:
            return Color(R.color.approvedTag)
        case CarStatus.Completed().title.resource:
            return Color(R.color.completedTag)
        case CarStatus.Cancelled().title.resource:
            return Color(R.color.rejectedTag)
        case CarStatus.Processing().title.resource:
            return Color(R.color.processingTag)
        default:
            return Color.clear
        }
    }
    
    var body: some View {
        HStack(spacing: 12) {
            VStack {
                HStack {
                    Text(order.bid_status)
//                        .font(.caption2)
                        .padding(.horizontal, 12)
                        .padding(.vertical, 6)
                        .background(
                            RoundedRectangle(cornerRadius: 6)
                                .fill(statusColor(order.bid_status))
                        )
                    Spacer()
                }
                if let carImage = order.carsMap[order.car_id].images.first {
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
        }
        .padding(.vertical, 20)
        .padding(.horizontal, 16)
        .background(RoundedRectangle(cornerRadius: 10).fill(.white))
    }
}
