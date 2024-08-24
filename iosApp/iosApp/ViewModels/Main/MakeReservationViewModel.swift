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
    @Published var isSuccessed: RequestReservationState = .success
    @Published var showDatePicker = false
    @Published var dateRange: ClosedRange<Date>?
    @Published var inputErrorType: InputErrorType?
    @Published var time: Date?
    @Published var showTimePicker: Bool = false
    @Published var selectedPlace: PlaceShort?
    @Published var totalPrice: Int = 0
    @Published var comment: String?
    @ObservedObject var apiManager = KMPApiManager.shared
    @FocusState var focuseOnCommentField: Bool

    let car: Car
    let dates: String?
    var carPreview: Image {
        if let image = car.images.first {
            return Image(ImageResource(name: image.assetImageName, bundle: image.bundle))
        } else {
            return Image.questionFolder
        }
    }

    init(
        car: Car,
        dates: String?
    ) {
        self.car = car
        self.dates = dates

        Task {
            await fetchPlaceList()
        }
    }

    func fetchPlaceList() async {
        let places = await KMPApiManager.shared.fetchPlaces()

        await MainActor.run {
            self.handlerPlaceItems(places)
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
