//
//  MainViewModel.swift
//  iosApp
//
//  Created by Sergey Ivanov on 24.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

final class MainViewModel: ObservableObject {
    @Published var cardList = AutoCard.autoCardsList
}

// Model
struct AutoCard: Identifiable {
    let id: Int
    let auto: Auto
    let cardType: AutoCardType
    let trailingIcon: Image?
    
    static let empty = AutoCard(
        id: 0,
        auto: Auto(
            image: Image.bell,
            title: "",
            price: "",
            transmisson: .auto,
            yearOfManufacture: 0,
            driveType: .allWheels,
            tankVolume: ""
        ),
        cardType: .simple,
        trailingIcon: nil
    )
    
    static let autoCardsList: [AutoCard] = [
        AutoCard(
            id: 1,
            auto: Auto(
                image: Image(R.image.blackCar),
                title: "Hyundai Solaris",
                price: "от 2 500 р/сут.",
                transmisson: .auto,
                yearOfManufacture: 2023,
                driveType: .frontWheels,
                tankVolume: "43 литра"
            ),
            cardType: .simple,
            trailingIcon: nil
        ),
        AutoCard(
            id: 1,
            auto: Auto(
                image: Image(R.image.redCar), 
                title: "Hyundai Creta",
                price: "от 2 500 р/сут.",
                transmisson: .auto,
                yearOfManufacture: 2019,
                driveType: .frontWheels,
                tankVolume: "50 литра"
            ),
            cardType: .simple,
            trailingIcon: nil
        ),
        AutoCard(
            id: 1,
            auto: Auto(
                image: Image(R.image.whiteCar), 
                title: "Volkswagen Polo",
                price: "от 3 500 р/сут.",
                transmisson: .manual,
                yearOfManufacture: 2013,
                driveType: .rearWheels,
                tankVolume: "43 литра"
            ),
            cardType: .simple,
            trailingIcon: nil
        ),
        AutoCard(
            id: 1,
            auto: Auto(
                image: Image(R.image.groupCar), 
                title: "Перейти ко всему автопарку",
                price: nil,
                transmisson: .manual,
                yearOfManufacture: 2023,
                driveType: .frontWheels,
                tankVolume: "43 литра"
            ),
            cardType: .general,
            trailingIcon: Image.rightArrowCircleFill
        ),
    ]
}

struct Auto {
    let image: Image
    let title: String
    let price: String?
    let transmisson: TransmissonType
    let yearOfManufacture: Int
    let driveType: DriveType
    let tankVolume: String
    
    
}

enum TransmissonType {
    case auto
    case manual
}

enum DriveType: String {
    case frontWheels, rearWheels, allWheels
}

extension TransmissonType {
    var localizedStr: String {
        switch self {
        case .auto:
            return R.string.localizable.autoTransmissionType()
        case .manual:
            return R.string.localizable.mechTransmissionType()
        }
    }
}

extension DriveType {
    var localizedStr: String {
        switch self {
        case .frontWheels:
            return R.string.localizable.frontDriveType()
        case .rearWheels:
            return R.string.localizable.realDrivetype()
        case .allWheels:
            return R.string.localizable.allDriveType()
        }
    }
}
