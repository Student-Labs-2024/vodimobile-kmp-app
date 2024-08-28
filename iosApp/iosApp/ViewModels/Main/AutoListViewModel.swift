//
//  AutoListViewModel.swift
//  iosApp
//
//  Created by Sergey Ivanov on 30.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import shared
import SwiftUI

final class AutoListViewModel: ObservableObject {
    @Published var listOfAllCar = [Car]()
    @Binding var dateRange: ClosedRange<Date>? {
        didSet {
            Task {
                await fetchCars()
            }
        }
    }
    @Published var isLoading = false
    private let apiManager = KMPApiManager.shared
    private let DateFormatter = CustomDateFormatter.shared

    init(dateRange: Binding<ClosedRange<Date>?>) {
        self._dateRange = dateRange
    }

    func fetchCars() async {
        await MainActor.run {
            self.isLoading = true
        }
        let fetchedCars: [Car]
        let freeCarIds: Set<Int>
        let filteredCars: [Car]

        if let datesRange = dateRange {
            async let fetchedCarsResult = apiManager.fetchCars()
            async let carIdsResult = apiManager.fetchFreeCarIdsForDate(for: CarFreeListParamsDTO(
                begin: DateFormatter.transformDateToString(date: dateRange?.lowerBound, time: nil),
                end: DateFormatter.transformDateToString(date: dateRange?.upperBound, time: nil),
                includeReserves: true,
                includeIdles: true,
                cityId: 2
            ))

            fetchedCars = await fetchedCarsResult
            freeCarIds = Set(await carIdsResult)
            filteredCars = fetchedCars.filter { freeCarIds.contains(Int($0.carId)) }
        } else {
            fetchedCars = await apiManager.fetchCars()
            freeCarIds = Set()
            filteredCars = fetchedCars
        }

        await MainActor.run {
            self.listOfAllCar = filteredCars
            self.isLoading = false
        }
    }

    func filterCars(by autoType: CarType) -> Binding<[Car]> {
        Binding.constant(listOfAllCar.filter({ $0.carType.contains { $0 == autoType } }))
    }
}
