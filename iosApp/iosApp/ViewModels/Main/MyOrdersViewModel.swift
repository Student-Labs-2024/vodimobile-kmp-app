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
    @Published var activeOrderList = [OrderDTO]()
    @Published var completedOrderList = [OrderDTO]()
    private var orderslist = [OrderDTO]() {
        didSet {
            activeOrderList = filterOrderList(by: .active)
            completedOrderList = filterOrderList(by: .completed)
        }
    }
    private var apiManager = KMPApiManager.shared
    
    init() {
        getAllOrders()
    }
    
    func getAllOrders() {
        Task {
            let orders = await apiManager.fetchUserOrders()
            await MainActor.run {
                self.orderslist = orders
            }
        }
    }
    
    func filterOrderList(by type: MyOrderTab) -> [OrderDTO] {
        switch type {
        case .active:
            return orderslist.filter {
                $0.bid_status == CarStatus.Approved().title.resource ||
                $0.bid_status == CarStatus.Processing().title.resource
            }
        case .completed:
            return orderslist.filter {
                $0.bid_status == CarStatus.Cancelled().title.resource ||
                $0.bid_status == CarStatus.Completed().title.resource
            }
        }
    }
}
