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
    @Published var isLoading = true

    init() {
        Task {
            await fetchAllCars()
        }
    }
    
    func fetchAllCars() async {
        let carsList = await KMPApiManager.shared.fetchCars()
        
        DispatchQueue.main.async {
            self.listOfAllCar = carsList
        }
    }
    
    func filterCars(by autoType: CarType) -> [Car] {
        listOfAllCar.filter({ $0.carType.contains { $0 == autoType } })
    }
}
