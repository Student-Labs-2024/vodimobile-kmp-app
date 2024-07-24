//
//  MainViewModel.swift
//  iosApp
//
//  Created by Sergey Ivanov on 24.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

final class MainViewModel: ObservableObject {
    @Published var cardList = AutoCard.autoCardsList
}

// Model
struct AutoCard: Identifiable {
    let id: Int
    let image: Image
    let title: String
    let price: String?
    let cardType: AutoCardType
    let trailingIcon: Image?
    
    static let autoCardsList: [AutoCard] = [
        AutoCard(id: 1, image: Image(R.image.blackCar), title: "Hyundai Solaris", price: "от 2 500 р/сут.", cardType: .simple, trailingIcon: nil),
        AutoCard(id: 2, image: Image(R.image.redCar), title: "Hyundai Creta", price: "от 2 500 р/сут.", cardType: .simple, trailingIcon: nil),
        AutoCard(id: 3, image: Image(R.image.whiteCar), title: "Volkswagen Polo", price: "от 3 500 р/сут.", cardType: .simple, trailingIcon: nil),
        AutoCard(id: 4, image: Image(R.image.groupCar), title: "Перейти ко всему автопарку", price: nil, cardType: .general, trailingIcon: Image.rightArrowCircleFill)
    ]
}
