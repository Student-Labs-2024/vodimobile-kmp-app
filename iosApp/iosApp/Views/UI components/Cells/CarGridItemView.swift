//
//  CarGridItemView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 18.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct CarGridItem: View {
    enum CarGridItemType {
        case transmission, gear, yearDev, gasoline
    }
    private let gridItemType: CarGridItemType
    private let icon: Image
    private let title: String
    private var value: String

    init(gridItemType: CarGridItemType, value: String) {
        self.gridItemType = gridItemType
        self.value = value

        switch gridItemType {
        case .transmission:
            self.icon = Image(R.image.transmission)
            self.title = R.string.localizable.transmissionTitle()
        case .gear:
            self.icon = Image(R.image.gear)
            self.title = R.string.localizable.driveTypeTitle()
        case .yearDev:
            self.icon = Image(R.image.calendarYear)
            self.title = R.string.localizable.yearOfManufactureTitle()
            self.value = "\(value)".replacingOccurrences(of: " ", with: "")
        case .gasoline:
            self.icon = Image(R.image.gasoline)
            self.title = R.string.localizable.tankTitle()
            self.value = "\(value) \(R.string.localizable.literText())"
        }
    }

    var body: some View {
        HStack(spacing: 18) {
            icon
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 35, height: 35)
            VStack(alignment: .leading, spacing: 5) {
                Text(title)
                    .font(.caption1)
                    .fontWeight(.bold)
                    .foregroundStyle(Color(R.color.text))
                Text(value)
                    .foregroundStyle(Color(R.color.grayText))
                    .font(.caption1)
                    .fontWeight(.bold)
            }
        }
    }
}
