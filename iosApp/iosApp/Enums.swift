//
//  Enums.swift
//  iosApp
//
//  Created by Sergey Ivanov on 25.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

enum CellType {
    case email
    case phone
    case officeLocation
}

enum MediaType {
    case vk
    case whatsapp
    case tg
}

enum ProfileCellType {
    case conditions, faq, contacts
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

enum TabType: Int {
    case main
    case myOrders
    case profile
}

enum TextFieldType: String {
    case email, phone, fullName, password, oldPassword, newPassword
    
    var localizedStr: String {
        switch self {
        case .email:
            return R.string.localizable.email()
        case .phone:
            return R.string.localizable.phone()
        case .fullName:
            return R.string.localizable.fullName()
        case .password:
            return R.string.localizable.password()
        case .oldPassword:
            return R.string.localizable.oldPassword()
        case .newPassword:
            return R.string.localizable.newPassword()
        }
    }
}

enum AutoCardType {
    case simple, general
}

enum ScrollOffsetNamespace {
    static let namespace = "scrollView"
}

enum AutoListType {
    case allCars, economy, comfort, premium, sedans, jeeps
    
    static var allCases: [String] {
        [
            AutoListType.allCars.localizedStr,
            AutoListType.economy.localizedStr,
            AutoListType.comfort.localizedStr,
            AutoListType.premium.localizedStr,
            AutoListType.sedans.localizedStr,
            AutoListType.jeeps.localizedStr
        ]
    }
}
extension AutoListType {
    var localizedStr: String {
        switch self {
        case .allCars:
            return R.string.localizable.allCarsScreenTitle()
        case .economy:
            return R.string.localizable.economyScreenTitle()
        case .comfort:
            return R.string.localizable.comfortScreenTitle()
        case .premium:
            return R.string.localizable.premiumScreenTitle()
        case .sedans:
            return R.string.localizable.sedansScreenTitle()
        case .jeeps:
            return R.string.localizable.jeepsScreenTitle()
        }
    }
}
