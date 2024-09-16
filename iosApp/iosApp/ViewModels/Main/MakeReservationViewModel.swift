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
    @Published var dateRange: ClosedRange<Date>? {
        didSet {
            handlerFieldsChanged()
        }
    }
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
    @Published var selectedServices = [ServicesDTO]() {
        didSet {
            handlerFieldsChanged()
        }
    }
    @Published var servicesList = [ServicesDTO]()
    @Published var showStartTimePicker: Bool = false
    @Published var showEndTimePicker: Bool = false
    @Published var inputErrorType: InputErrorType?
    @Published var bidCost: Double = 0
    @Published var isLoading = false
    @Published var showSuccessModal = false
    @Published var showErrorModal = false
    @ObservedObject var apiManager = KMPApiManager.shared
    private let DateFormatter = CustomDateFormatter.shared

    let car: Car
    let dates: ClosedRange<Date>?
    var carPreview: Image {
        if let image = car.images.first {
            return Image(ImageResource(name: image.assetImageName, bundle: image.bundle))
        } else {
            return Image.questionFolder
        }
    }

    init(
        car: Car,
        dates: ClosedRange<Date>? = nil
    ) {
        self.car = car
        self.dates = dates

        if placesWithCost.isEmpty && servicesList.isEmpty {
            Task {
                await fetchServiceList()
                await fetchPlaceList()
            }
        }
    }

    func createBidToReserve() async -> BidCreateDTO? {
        var createdBid: BidCreateDTO?
        if let storageUser = apiManager.dataStorage.gettingUser,
           let startPlace = startPlace,
           let endPlace = endPlace {
            let beginDateTime = DateFormatter.transformDateToString(date: dateRange?.lowerBound, time: startTime)
            let endDateTime = DateFormatter.transformDateToString(date: dateRange?.upperBound, time: endTime)

            createdBid = await apiManager.createBidToReserving(for: BidCreateParams(
                fio: storageUser.fullName,
                phone: storageUser.phone,
                car_id: car.carId,
                begin: beginDateTime,
                end: endDateTime,
                begin_place_id: startPlace.id,
                end_place_id: endPlace.id,
                services: selectedServices.map({ KotlinInt(value: $0.service_id) }),
                prepayment: nil,
                files: nil
            ),
            inputDate: ReservationInputData(
                startDate: DateFormatter.transformDateToShortString(date: dateRange?.lowerBound),
                endDate: DateFormatter.transformDateToShortString(date: dateRange?.upperBound),
                startTime: DateFormatter.transformDateToTimeString(time: startTime),
                endTime: DateFormatter.transformDateToTimeString(time: endTime),
                startPlace: startPlace.title,
                endPlace: endPlace.title,
                cost: Float(bidCost),
                services: selectedServices.map({ $0.service_id.description }).joined(separator: ", ")
            )
            )
        }
        await MainActor.run {
            showSuccessModal = createdBid != nil ? true : false
            showErrorModal = createdBid == nil ? true : false
        }
        return createdBid
    }

    func fetchPlaceList() async {
        await MainActor.run {
            isLoading = true
        }
        let places = await apiManager.fetchPlaces()

        await MainActor.run {
            self.handlerPlaceItems(places)
            isLoading = false
        }
    }

    func fetchServiceList() async {
        await MainActor.run {
            isLoading = true
        }
        let services = await apiManager.fetchServices()

        await MainActor.run {
            servicesList = services
            isLoading = false
        }
    }

    func fetchBigCost() async {
        await MainActor.run {
            isLoading = true
        }
        if let startPlace = startPlace, let endPlace = endPlace {
            let bidInfo = await apiManager.fetchBidCost(for: BidCostParams(
                car_id: car.carId,
                begin: DateFormatter.transformDateToString(date: dateRange?.lowerBound, time: startTime),
                end: DateFormatter.transformDateToString(date: dateRange?.upperBound, time: endTime),
                begin_place_id: startPlace.id,
                end_place_id: endPlace.id,
                services: convertSwiftArrayToKotlinArray(swiftArray: selectedServices)
            )
            )
            await MainActor.run {
                bidCost = bidInfo?.cost ?? 0
            }
        }
        await MainActor.run {
            isLoading = false
        }
    }

    private func handlerPlaceItems(_ places: [Place]) {
        for place in places where !place.archive {
            if place.deliveryCost > 0 {
                placesWithCost.append(
                    PlaceShort(
                        id: place.placeId,
                        nameWithCost: "\(place.title) - \(Int(place.deliveryCost)) \(R.string.localizable.currencyPriceText())",
                        title: place.title
                    )
                )
            } else if place.deliveryCost == 0 {
                placesWithCost.append(
                    PlaceShort(
                        id: place.placeId,
                        nameWithCost: "\(place.title) - \(R.string.localizable.freeText().lowercased())",
                        title: place.title
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
        if dateRange != nil && startPlace != nil &&
            startTime != nil && endPlace != nil {
            Task {
                await self.fetchBigCost()
            }
        }
    }
}

struct PlaceShort: Identifiable {
    let id: Int32
    let nameWithCost: String
    let title: String
}

struct ReservationInputData {
    let startDate: String
    let endDate: String
    let startTime: String
    let endTime: String
    let startPlace: String
    let endPlace: String
    let cost: Float
    let services: String
}
