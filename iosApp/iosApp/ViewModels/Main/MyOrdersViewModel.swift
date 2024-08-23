//
//  MyOrdersViewModel.swift
//  iosApp
//
//  Created by Sergey Ivanov on 16.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared
import SwiftUI
import Combine

final class MyOrdersViewModel: ObservableObject {
    @Published var activeOrderList = [Order]()
    @Published var completedOrderList = [Order]()
    @Published var orderslist = [Order]()
    @Published var isLoading: Bool = false
    private var apiManager = KMPApiManager.shared
    private var cancellableSet: Set<AnyCancellable> = []

    @MainActor
    init() {
        $orderslist
            .receive(on: DispatchQueue.main)
            .sink { _ in
                self.activeOrderList = self.filterOrderList(by: .active)
                self.completedOrderList = self.filterOrderList(by: .completed)
            }
            .store(in: &cancellableSet)

        Task {
            await getAllOrders()
        }
    }

    func getAllOrders() async {
        await MainActor.run {
            isLoading = true
        }

        let orders = await apiManager.fetchUserOrders()

        await MainActor.run {
            self.orderslist = orders
            self.isLoading = false
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
