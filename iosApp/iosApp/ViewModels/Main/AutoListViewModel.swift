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
    @Binding var datesRange: ClosedRange<Date>?
    @Published var isLoading = false
    private let apiManager = KMPApiManager.shared
    
    init() {
        Task {
            await fetchAllCars()
        }
    }
    
    func fetchCarIdsForDateRange() async {
        self.isLoading.toggle()
        let carIdsList = await apiManager.fetchFreeCarIdsForDate(for: CarFreeListParamsDTO(
            begin: Int(datesRange.lowerBound.timeIntervalSince1970)
            end: Int(datesRange.upperBound.timeIntervalSince1970),
            includeReserves: true,
            includeIdles: true,
            cityId: 2
        )
        )

        await MainActor.run {
            self.listOfAllCar = carsList
            self.isLoading.toggle()
        }
    }

    func fetchAllCars() async {
        self.isLoading.toggle()
        let carsList = await apiManager.fetchCars()

        await MainActor.run {
            self.listOfAllCar = carsList
            self.isLoading.toggle()
        }
    }

    func filterCars(by autoType: CarType) -> Binding<[Car]> {
        Binding.constant(listOfAllCar.filter({ $0.carType.contains { $0 == autoType } }))
    }
}
