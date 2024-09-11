//
//  OrderDetailViewModel.swift
//  iosApp
//
//  Created by Sergey Ivanov on 17.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

final class OrderDetailViewModel: ObservableObject {
    var order: Order
    var orderCarPreview: Image {
        if let image = order.car.images.first {
            return Image(ImageResource(name: image.assetImageName, bundle: image.bundle))
        } else {
            return Image.questionFolder
        }
    }
    let apiManager = KMPApiManager.shared
    let dataStorage = KMPDataStorage.shared

    init(order: Order) {
        self.order = order
    }
}
