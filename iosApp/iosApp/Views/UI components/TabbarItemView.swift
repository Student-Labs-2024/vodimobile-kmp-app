//
//  TabbarItemView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 11.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import RswiftResources

struct TabBarItem: View {

    let icon: Image
    let title: StringResource
    let isSelected: Bool
    let itemWidth: CGFloat
    let onTap: () -> Void

    var body: some View {
        Button {
            onTap()
        } label: {
            VStack(alignment: .center, spacing: 2.0) {
                icon
                    .resizable()
                    .renderingMode(.template)
                    .frame(width: 20, height: 20)
                    .foregroundColor(isSelected ? Color(R.color.blueColor) : Color(R.color.grayDark))

                Text(title)
                    .font(.buttonTabbar)
                    .foregroundColor(isSelected ? Color(R.color.blueColor) : Color(R.color.grayDark))
            }
            .padding(.top, 15)
            .frame(width: itemWidth)
        }
        .buttonStyle(.plain)
    }
}
