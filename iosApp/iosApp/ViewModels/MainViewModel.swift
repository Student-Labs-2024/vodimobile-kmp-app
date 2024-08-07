//
//  MainViewModel.swift
//  iosApp
//
//  Created by Sergey Ivanov on 05.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

final class MainViewModel: ObservableObject {
    @Published var listOfPopularCar: [Car] = KoinHelper().getPopularCars()
}
