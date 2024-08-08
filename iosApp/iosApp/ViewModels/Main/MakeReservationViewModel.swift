//
//  MakeReservationViewModel.swift
//  iosApp
//
//  Created by Sergey Ivanov on 08.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

final class MakeReservationViewModel: ObservableObject {
    // TODO: - Add network logic for request all place for pick up cars
    @Published var placesWithCost = [String]()
    @Published var isSuccessed: Bool = true
    private var placeList = [Place]()
    
    
    func fetchPlaceList() async {
        let places = await KMPApiManager.shared.fetchPlaces()
        
        DispatchQueue.main.async {
            self.handlerPlaceItems(places)
        }
    }
    
    private func handlerPlaceItems(_ places: [Place]) {
        for place in places where !place.archive {
            if place.deliveryCost > 0 {
                placesWithCost.append("\(place.title) - \(place.deliveryCost) \(R.string.localizable.currencyPriceText())")
            } else if place.deliveryCost == 0 {
                placesWithCost.append("\(place.title) - \(R.string.localizable.freeText().lowercased())")
            } else {
                continue
            }
        }
    }
}
