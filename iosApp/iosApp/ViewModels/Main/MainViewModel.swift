//
//  MainViewModel.swift
//  iosApp
//
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

final class MainViewModel: ObservableObject {
    @Published var dateRange: ClosedRange<Date>?
    @Published var selectedAuto: Car = Car.companion.empty()
    @Published var listOfPopularCar: [Car]

    init() {
        self.listOfPopularCar = KoinHelper().getPopularCars()
    }
}
