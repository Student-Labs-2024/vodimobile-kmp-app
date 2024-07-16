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
public let phoneRegex = #"^\+\d \d{3} \d{3}-\d{2}-\d{2}"#

public let contactPhone = "+7 (950) 783 14-40"
public let contactEmail = "vodimobil@vodimobil.ru"
public let contactLocation = String(localized: String.LocalizationValue(stringLiteral: "officeLocation"))
public let whatsappLink = "https://wa.me/79507831440"
public let vkLink = "https://vk.com/vodimobil"
public let tgLink = "https://t.me/vodimobilomsk"
