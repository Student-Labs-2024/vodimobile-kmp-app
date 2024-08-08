//
//  AutoListView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 24.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct AutoListView: View {
    @Binding var showModalReservation: Bool
    @State private var selectedTab: Int = 0
    @State private var showModalCard: Bool = false
    @State private var selectedAuto: Car = Car.companion.empty()
    @State private var dragOffset: CGSize = .zero
    @ObservedObject private var viewModel: AutoListViewModel = AutoListViewModel()
    
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
                            ForEach(viewModel.listOfAllCar.indices, id: \.self) { index in
                                AutoCardWithButtonView(
                                    carModel: viewModel.listOfAllCar[index],
                                    selectedAuto: $selectedAuto,
                                    showModal: $showModalCard,
                                    showModalReservation: $showModalReservation
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
            .onAppear {
                viewModel.fetchAllCars()
            }
            .sheet(isPresented: $showModalCard) {
                ModalAutoView(
                    carModel: $selectedAuto,
                    showModalView: $showModalCard,
                    showModalReservation: $showModalReservation
                )
            }
        }
        .background(Color(R.color.grayLightColor))
        .navigationBarBackButtonHidden()
        .toolbar {
            CustomToolbar(title: R.string.localizable.carParkScreenTitle)
        }
    }
}

#Preview {
    AutoListView(showModalReservation: Binding.constant(false))
}
