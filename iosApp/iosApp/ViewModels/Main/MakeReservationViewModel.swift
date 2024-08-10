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
    @Published var placesWithCost = [PlaceShort]()
    @Published var isSuccessed: Bool = true
    @Published var isLoading: Bool = false
    @Published var showDatePicker = false
    @Published var dateRange: ClosedRange<Date>? = nil
    @Published var inputErrorType: InputErrorType?
    @Published var time: Date? = nil
    @Published var showTimePicker: Bool = false
    @Published var selectedPlace: PlaceShort? = nil
    @Published var totalPrice: Int = 0
    @Published var comment: String? = nil
    @FocusState var focuseOnCommentField: Bool
    
    init() {
        Task {
            await fetchPlaceList()
        }
    }
    
    func fetchPlaceList() async {
        isLoading.toggle()
        let places = await KMPApiManager.shared.fetchPlaces()
        
        DispatchQueue.main.async {
            self.handlerPlaceItems(places)
            self.isLoading.toggle()
        }
    }
    
    private func handlerPlaceItems(_ places: [Place]) {
        for place in places where !place.archive {
            if place.deliveryCost > 0 {
                placesWithCost.append(
                    PlaceShort(
                        id: place.placeId,
                        nameWithCost: "\(place.title) - \(Int(place.deliveryCost)) \(R.string.localizable.currencyPriceText())"
                    )
                )
            } else if place.deliveryCost == 0 {
                placesWithCost.append(
                    PlaceShort(
                        id: place.placeId,
                        nameWithCost: "\(place.title) - \(R.string.localizable.freeText().lowercased())"
                    )
                )
            } else {
                continue
            }
        }
    }
}

struct PlaceShort: Identifiable {
    let id: Int32
    let nameWithCost: String
}
