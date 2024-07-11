//
//  MainScreenView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 09.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

enum TabType: Int {
    case main
    case myOrders
    case profile
}

struct MainTabbarView: View {
    @State private var selectedTab: TabType = .main
    
    var body: some View {
        GeometryReader { geometry in
            ZStack(alignment: Alignment.bottom) {
                TabView(selection: $selectedTab) {
                    MainView().tag(TabType.main)
                    MyOrdersView().tag(TabType.myOrders)
                    ProfileView().tag(TabType.profile)
                }
                
                HStack(spacing: 0) {
                    TabBarItem(icon: Image("home"),
                               title: String(localized: String.LocalizationValue(stringLiteral: "homeTabItem")),
                               badgeCount: 0,
                               isSelected: selectedTab ==  .main,
                               itemWidth: geometry.size.width / 3) {
                        selectedTab = .main
                    }
                    
                    TabBarItem(icon: Image("car"),
                               title: String(localized: String.LocalizationValue(stringLiteral: "myOrdersTabItem")),
                               badgeCount: 0,
                               isSelected: selectedTab ==  .myOrders,
                               itemWidth: geometry.size.width / 3) {
                        selectedTab = .myOrders
                    }
                    
                    TabBarItem(icon: Image(systemName: "person.fill"),
                               title: String(localized: String.LocalizationValue(stringLiteral: "profileTabItem")),
                               badgeCount: 0,
                               isSelected: selectedTab == .profile,
                               itemWidth: geometry.size.width / 3) {
                        selectedTab = .profile
                    }
                }
                .background(Color.white)
                .overlay {
                    Rectangle()
                        .fill(Color.gray)
                        .frame(height: 0.5, alignment: .top)
                        .offset(y: -26)
                        .opacity(0.5)
                }
                .padding(.vertical, 25)
            }
        }
        .ignoresSafeArea()
        .navigationBarBackButtonHidden(true)
    }
}

#Preview {
    MainTabbarView()
}
