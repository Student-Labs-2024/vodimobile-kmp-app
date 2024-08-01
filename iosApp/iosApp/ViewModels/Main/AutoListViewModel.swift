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
    @Published var listOfAllCar: [Car]

    init() {
        self.listOfAllCar = KoinHelper().getPopularCars()
    }
}
