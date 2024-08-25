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
    @Published var isSuccessed = false
    @Published var showDatePicker = false
    @Published var dateRange: ClosedRange<Date>?
    @Published var inputErrorType: InputErrorType?
    @Published var startTime: Date? {
        didSet {
            handlerFieldsChanged()
        }
    }
    @Published var endTime: Date? {
        didSet {
            handlerFieldsChanged()
        }
    }
    @Published var showStartTimePicker: Bool = false
    @Published var showEndTimePicker: Bool = false
    @Published var startPlace: PlaceShort? {
        didSet {
            handlerFieldsChanged()
        }
    }
    @Published var endPlace: PlaceShort? {
        didSet {
            handlerFieldsChanged()
        }
    }
    @Published var servicesList = [ServicesDTO]()
    @Published var selectedServices = [ServicesDTO]()
    @Published var totalPrice: Int = 0
    @Published var bidCost: Double = 0
    @Published var isLoading = false
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

        if placesWithCost.isEmpty && servicesList.isEmpty {
            Task {
                await fetchPlaceList()
                await fetchServiceList()
            }
        }
    }

    func createBidToReserve() async -> BidCreateDTO? {
        var createdBid: BidCreateDTO?
        if let storageUser = apiManager.dataStorage.gettingUser,
           let startPlace = startPlace,
           let endPlace = endPlace {
            createdBid = await apiManager.createBidToReserving(for: BidCreateParams(
                fio: storageUser.fullName,
                phone: storageUser.phone,
                car_id: car.carId,
                begin: startTime?.formatted() ?? "",
                end: endTime?.formatted() ?? "",
                begin_place_id: startPlace.id,
                end_place_id: endPlace.id,
                services: selectedServices.map({ KotlinInt(value: $0.service_id) }),
                prepayment: nil,
                files: nil
            )
            )
        }
        isSuccessed = createdBid != nil ? true : false
        return createdBid
    }

    func fetchPlaceList() async {
        isLoading = true
        let places = await apiManager.fetchPlaces()

        await MainActor.run {
            self.handlerPlaceItems(places)
        }
        isLoading = false
    }

    func fetchServiceList() async {
        isLoading = true
        let services = await apiManager.fetchServices()

        await MainActor.run {
            servicesList = services
        }
        isLoading = false
    }

    func fetchBigCost() async {
        isLoading = true
        if let startPlace = startPlace, let endPlace = endPlace {
            let bidInfo = await apiManager.fetchBidCost(for: BidCostParams(
                car_id: car.carId,
                begin: startTime?.formatted() ?? "",
                end: endTime?.formatted() ?? "",
                begin_place_id: startPlace.id,
                end_place_id: endPlace.id,
                services: convertSwiftArrayToKotlinArray(swiftArray: selectedServices)
            )
            )
            bidCost = bidInfo?.cost ?? 0
        }
        isLoading = false
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

    private func convertSwiftArrayToKotlinArray(swiftArray: [ServicesDTO]) -> KotlinArray<KotlinInt> {
        let size = Int32(swiftArray.count)
        return KotlinArray(size: size) { index in
            return KotlinInt(int: Int32(swiftArray[Int(truncating: index)].service_id))
        }
    }

    private func handlerFieldsChanged() {
        if startPlace != nil && endPlace != nil {
            Task {
                await self.fetchBigCost()
            }
        }
    }
}

struct PlaceShort: Identifiable {
    let id: Int32
    let nameWithCost: String
}
