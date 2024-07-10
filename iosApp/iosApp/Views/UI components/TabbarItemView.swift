//
//  TabbarItemView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 11.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct TabBarItem: View {
    
    let icon: Image
    let title: String
    let badgeCount: Int
    let isSelected: Bool
    let itemWidth: CGFloat
    let onTap: () -> ()
    
    var body: some View {
        Button {
            onTap()
        } label: {
            VStack(alignment: .center, spacing: 2.0) {
                ZStack(alignment: .bottomLeading) {
                    ZStack {
                        icon
                            .resizable()
                            .renderingMode(.template)
                            .frame(width: 20, height: 20)
                            .foregroundColor(isSelected ? Color.blueColor : Color.grayDarkColor)
                        Text("\(badgeCount > 99 ? "99+" : "\(badgeCount)")")
                            .kerning(0.3)
                            .lineLimit(1)
                            .truncationMode(.tail)
                            .foregroundColor(Color.white)
                            .padding(.horizontal, 4)
                            .background(Color.redColor)
                            .cornerRadius(100)
                            .opacity(badgeCount == 0 ? 0.0 : 1.0)
                            .offset(x: 16.0, y: -8.0)
                    }
                }
                Text(title)
                    .font(.buttonTabbar)
                    .foregroundColor(isSelected ? Color.blueColor : Color.grayDarkColor)
            }
            .frame(width: itemWidth)
        }
        .buttonStyle(.plain)
    }
}
