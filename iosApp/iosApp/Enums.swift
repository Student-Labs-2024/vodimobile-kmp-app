//
//  Enums.swift
//  iosApp
//
//  Created by Sergey Ivanov on 25.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

// Profile
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
    case personalData, conditions, faq, contacts
}

// Main tab view
enum TabType: Int {
    case main
    case myOrders
    case profile
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

// My order view
enum MyOrderTab {
    case active, completed
}

// Errors
enum InputErrorType: String {
    case incorrectFullName
    case incorrectPhone
    case alreadyExistsPhone
    case incorrectPass
    case invalidPass
    case tooShortPass
    case noSpecSymboldsInPass
    case noUpperLettersInPass
    case oldPasswordIsWrong
    case selectDayTime, selectNightTime

    var errorString: String {
        switch self {
        case .incorrectFullName:
            return R.string.localizable.incorrectFullName()
        case .incorrectPhone:
            return R.string.localizable.incorrectPhone()
        case .alreadyExistsPhone:
            return R.string.localizable.alreadyExistsPhone()
        case .incorrectPass:
            return R.string.localizable.incorrectPass()
        case .tooShortPass:
            return R.string.localizable.tooShortPass()
        case .noSpecSymboldsInPass:
            return R.string.localizable.noSpecSymboldsInPass()
        case .noUpperLettersInPass:
            return R.string.localizable.noUpperLettersInPass()
        case .invalidPass:
            return R.string.localizable.invalidPass()
        case .oldPasswordIsWrong:
            return R.string.localizable.oldPassIsWrong()
        case .selectDayTime:
            return R.string.localizable.selectDayTime()
        case .selectNightTime:
            return R.string.localizable.selectNightTime()
        }
    }
}

// State
enum RequestReservationState {
    case success, fail
}

enum AuthFlowType {
    case registration, auth, resetPassword
}

// field types
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

enum ButtonLikeTextFieldType {
    case startPlacePicker, endPlacePicker, startTimePicker, endTimePicker, datePicker

    var localizedStr: String {
        switch self {
        case .datePicker:
            return R.string.localizable.dateTextFieldTitle()
        case .startTimePicker:
            return R.string.localizable.startRentalTime()
        case .endTimePicker:
            return R.string.localizable.endRentalTime()
        case .startPlacePicker:
            return R.string.localizable.startPlaceOfObtainingTitle()
        case .endPlacePicker:
            return R.string.localizable.endPlaceOfObtainingTitle()
        }
    }
}
