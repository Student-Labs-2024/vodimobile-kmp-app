//
//  OrderTopPickerView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 16.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct OrdersTopPickerView: View {
    @Binding var selectedTab: MyOrderTab
    
    var body: some View {
        HStack {
            tabButton(
                title: R.string.localizable.activeTab(),
                isSelected: selectedTab == .active
            )
            .onTapGesture {
                withAnimation {
                    selectedTab = .active
                }
            }
            
            tabButton(
                title: R.string.localizable.completedTab(),
                isSelected: selectedTab == .completed
            )
            .onTapGesture {
                withAnimation {
                    selectedTab = .completed
                }
            }
        }
        .padding(12)
        .frame(maxWidth: .infinity)
        .background(Capsule().fill(.white))
    }
    
    @ViewBuilder
    private func tabButton(title: String, isSelected: Bool) -> some View {
        Text(title)
            .font(.header5)
            .foregroundColor(isSelected ? Color(R.color.blueColor) : Color(R.color.grayLabel))
            .padding(12)
            .frame(maxWidth: .infinity)
            .background(
                Capsule()
                    .strokeBorder(
                        isSelected ? Color(R.color.blueColor) : Color.clear,
                        lineWidth: 1.5
                    )
                    .background(isSelected ? Capsule().fill(Color.white) : Capsule().fill(Color.clear))
            )
            .buttonStyle(PlainButtonStyle())
    }
}

#Preview {
    OrdersTopPickerView(selectedTab: Binding.constant(.active))
}
