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
    @Published var orderslist: [Order] = [Order.companion.empty()]
}
