//
//  MyOrdersViewModel.swift
//  iosApp
//
//  Created by Sergey Ivanov on 16.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared
import SwiftUI

final class MyOrdersViewModel: ObservableObject {
    @Published var activeOrderList = [Order]()
    @Published var completedOrderList = [Order]()
    private var orderslist = [Order]() {
        didSet {
            activeOrderList = filterOrderList(by: .active)
            completedOrderList = filterOrderList(by: .completed)
        }
    }
    private var apiManager = KMPApiManager.shared
    
    init() {
        let empty = Order.companion.empty()
        activeOrderList = [
            Order(
                userId: 0,
                orderId: 16,
                bidNumber: 1223432,
                bid: empty.bid,
                status: empty.status,
                rentalDatePeriod: empty.rentalDatePeriod,
                startLocation: empty.startLocation,
                finishLocation: empty.finishLocation,
                rentalTimePeriod: empty.rentalTimePeriod,
                car: empty.car,
                services: empty.services
            )
        ]
    }
    
    func getAllOrders() {
        Task {
            let orders = await apiManager.fetchUserOrders()
            await MainActor.run {
                print(orders)
                self.orderslist = orders
            }
        }
    }
    
    func filterOrderList(by type: MyOrderTab) -> [Order] {
        switch type {
        case .active:
            return orderslist.filter {
                $0.status == CarStatus.Approved() ||
                $0.status == CarStatus.Processing()
            }
        case .completed:
            return orderslist.filter {
                $0.status == CarStatus.Cancelled() ||
                $0.status == CarStatus.Completed()
            }
        }
    }
}
