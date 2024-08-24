//
//  MainScreenView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 09.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct MainTabbarView: View {
    @State private var selectedTab: TabType = .main
    @ObservedObject var appState = AppState.shared

    var body: some View {
        GeometryReader { geometry in
            let tabWidthSize = geometry.size.width / 3

            ZStack(alignment: Alignment.bottom) {
                TabView(selection: $selectedTab) {
                    MainView().tag(TabType.main)
                    MyOrdersView().tag(TabType.myOrders)
                    ProfileView().tag(TabType.profile)
                }

                HStack {
                    TabBarItem(
                        icon: Image(R.image.home),
                        title: R.string.localizable.homeScreenTitle,
                        isSelected: selectedTab ==  .main,
                        itemWidth: tabWidthSize
                    ) {
                        handleTabSelection(.main)
                    }

                    TabBarItem(
                        icon: Image(R.image.car),
                        title: R.string.localizable.myOrdersScreenTitle,
                        isSelected: selectedTab ==  .myOrders,
                        itemWidth: tabWidthSize
                    ) {
                        handleTabSelection(.myOrders)
                    }

                    TabBarItem(
                        icon: Image.personFill,
                        title: R.string.localizable.profileScreenTitle,
                        isSelected: selectedTab == .profile,
                        itemWidth: tabWidthSize
                    ) {
                        handleTabSelection(.profile)
                    }
                }
                .background(Color(R.color.container))
                .padding(.vertical, 25)
            }
        }
        .ignoresSafeArea()
        .navigationBarBackButtonHidden(true)
        .fullScreenCover(
            isPresented: $appState.isInternetErrorVisible
        ) {
            InternetConnectErrorView()
        }
        .onAppear {
            appState.checkConnectivity()
        }
    }

    private func handleTabSelection(_ tab: TabType) { selectedTab = tab }
}

#Preview {
    MainTabbarView()
}
