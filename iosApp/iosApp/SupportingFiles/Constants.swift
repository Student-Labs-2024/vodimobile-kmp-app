//
//  Constants.swift
//  iosApp
//
//  Created by Sergey Ivanov on 10.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct CustomEnvironmentKey: EnvironmentKey {
    static let defaultValue: Bool = false
}

extension EnvironmentValues {
    var isAuthorized: Bool {
        get { self[CustomEnvironmentKey.self] }
        set { self[CustomEnvironmentKey.self] = newValue }
    }
}

public let emailRegex = #"^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$"#
public let phoneRegex = #"^\+(?:[0-9] ?-?){6,14}[0-9]$"#
public let textRegex = #"^[a-zA-Zа-яА-ЯёЁ]+(([' -][a-zA-Zа-яА-ЯёЁ ])?[a-zA-Zа-яА-ЯёЁ]*)*$"#

// UI paddings
public let screenWidth = UIScreen.main.bounds.width
public let screenHeight = UIScreen.main.bounds.height

/// UI general paddings
public let horizontalPadding: CGFloat = 16
public let checkboxLeadingPadding: CGFloat = 14
public let spacingBetweenCheckboxAndText: CGFloat = 16
public let checkboxSize: CGFloat = 21
public let аuthScreencontentTopPadding: CGFloat = 120

/// UI screen paddings
struct SignSuggestConfig {
    static let spacingBetweenComponents: CGFloat = 20
    static let xmarkSize: CGFloat = 15
    static let xmarkTopPadding: CGFloat = 10
    static let imageSize: CGFloat = screenWidth / 3.5
    static let topSpacingImage: CGFloat = 50
    static let spacingBetweenTitleAndText: CGFloat = 12
    static let verticalPaddingTextBlock: CGFloat = 20
}

struct AuthAndRegScreensConfig {
    static let spacingBetweenGroupAndCheckbox: CGFloat = 10
    static let spacingBetweenComponents: CGFloat = 18
}

struct ConditionScreenConfig {
    static let paddings: CGFloat = 24
}

struct PinCodeConfig {
    static let spacingBetweenGroupAndResendText: CGFloat = 10
    static let spacingBetweenMainComponents: CGFloat = 25
    static let contentTopPadding: CGFloat = 40
    static let spacingBetweenPincodeCells: CGFloat = 16
    static let pincodeCellsSize: CGFloat = 56
    static let verticalSpacingBetweenPincodeField: CGFloat = 20
}

struct ProfileConfig {
    static let spacingBetweenBlocks: CGFloat = 30
    static let horizontalSpacingAvatarAndText: CGFloat = 10
    static let avatarIconSize: CGFloat = 24
    static let avatarFrameSize: CGFloat = 48
    static let editIconSize: CGFloat = 24
    static let horizontalSpacingBetweenIconAndText: CGFloat = 20
    static let mainIconsSize: CGFloat = 20
    static let insetPaddingBlock: CGFloat = 28
    static let insetPaddingExitButton: CGFloat = 18
}

// Contact info
public let contactPhone = "+7 (950) 783 14-40"
public let contactEmail = "vodimobil@vodimobil.ru"
public let contactLocation = R.string.localizable.officeLocation()
public let whatsappLink = "https://wa.me/79507831440"
public let vkLink = "https://vk.com/vodimobil"
public let tgLink = "https://t.me/vodimobilomsk"
let appVersion = "1.0.0"
