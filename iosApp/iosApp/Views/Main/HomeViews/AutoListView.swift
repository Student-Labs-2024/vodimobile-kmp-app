//
//  AutoListView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 24.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct AutoListView: View {
    @State private var selectedTab: Int = 0
    @State private var showModalCard: Bool = false
    @State private var selectedAuto: AutoCard = AutoCard.empty
    @State private var dragOffset: CGSize = .zero
    
    var body: some View {
        VStack {
            TabBarView(index: $selectedTab)
                .background(
                    RoundedRectangle(cornerRadius: 20)
                        .fill(.white)
                        .ignoresSafeArea(.all)
                )
            
            TabView(selection: $selectedTab) {
                if selectedTab == 0 {
                    ScrollView(.vertical, showsIndicators: false) {
                        LazyVStack(spacing: 20) {
                            ForEach(AutoCard.autoSimpleCardsList.indices, id: \.self) { index in
                                AutoCardWithButtonView(
                                    autoCard: AutoCard.autoCardsList[index],
                                    showModal: $showModalCard,
                                    selectedAuto: $selectedAuto
                                )
                            }
                        }
                        .padding(.horizontal, horizontalPadding)
                        .padding(.vertical, 24)
                    }
                } else {
                    Text("Page \(selectedTab)").tag(selectedTab)
                }
                
            }
            .ignoresSafeArea(.container)
            .tabViewStyle(PageTabViewStyle(indexDisplayMode: .never))
            .gesture(
                DragGesture()
                    .onEnded { value in
                        let horizontalAmount = value.translation.width
                        let verticalAmount = value.translation.height
                        
                        if abs(horizontalAmount) > abs(verticalAmount) {
                            if horizontalAmount < -50 {
                                withAnimation {
                                    if selectedTab < AutoListType.allCases.count - 1 {
                                        selectedTab += 1
                                    }
                                }
                            } else if horizontalAmount > 50 {
                                withAnimation {
                                    if selectedTab > 0 {
                                        selectedTab -= 1
                                    }
                                }
                            }
                        }
                    }
            )
        }
        .background(Color(R.color.grayLightColor))
        .navigationBarBackButtonHidden()
        .toolbar {
            CustomToolbar(title: R.string.localizable.carParkScreenTitle)
        }
        .sheet(isPresented: $showModalCard) {
            ModalAutoView(autoData: $selectedAuto, showModalView: $showModalCard)
        }
    }
}

#Preview {
    AutoListView()
}
