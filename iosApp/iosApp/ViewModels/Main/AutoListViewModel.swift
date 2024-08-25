//
//  AutoListViewModel.swift
//  iosApp
//
//  Created by Sergey Ivanov on 30.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared
import SwiftUI

@MainActor
final class AutoListViewModel: ObservableObject {
    @Published var listOfAllCar = [Car]()
    @Binding var datesRange: ClosedRange<Date>?
    @Published var isLoading = false
    private let apiManager = KMPApiManager.shared

    init(datesRange: Binding<ClosedRange<Date>?>) {
        self._datesRange = datesRange
        Task {
            await self.fetchCars()
        }
    }

    func fetchCars() async {
        self.isLoading = true
        let fetchedCars: [Car]
        let freeCarIds: Set<Int>

        if let datesRange = datesRange {
            async let fetchedCarsResult = apiManager.fetchCars()
            async let carIdsResult = apiManager.fetchFreeCarIdsForDate(for: CarFreeListParamsDTO(
                begin: Int64(datesRange.lowerBound.timeIntervalSince1970),
                end: Int64(datesRange.upperBound.timeIntervalSince1970),
                includeReserves: true,
                includeIdles: true,
                cityId: 2
            ))

            fetchedCars = await fetchedCarsResult
            freeCarIds = Set(await carIdsResult)
        } else {
            fetchedCars = await apiManager.fetchCars()
            freeCarIds = Set()
        }

        let filteredCars = fetchedCars.filter { freeCarIds.contains(Int($0.carId)) }

        self.listOfAllCar = filteredCars
        self.isLoading = false
    }

    func filterCars(by autoType: CarType) -> Binding<[Car]> {
        Binding.constant(listOfAllCar.filter({ $0.carType.contains { $0 == autoType } }))
    }
}
