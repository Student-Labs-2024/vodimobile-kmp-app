//
//  MainViewModel.swift
//  iosApp
//
//  Created by Sergey Ivanov on 30.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

final class MainViewModel: ObservableObject {
    @Published var listOfPopularCar: [Car]
    
    init() {
        self.listOfPopularCar = KoinHelper().getPopularCars()
    }
}
