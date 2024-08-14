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
    @Binding var selectedAuto: Car
    @Binding var showModalReservation: Bool
    @Binding var showSignSuggestModal: Bool
    @State private var selectedTab: Int = 0
    @State private var showModalCard: Bool = false
    @State private var dragOffset: CGSize = .zero
    @ObservedObject private var viewModel = AutoListViewModel()
    
    var body: some View {
        VStack {
            TabBarView(index: $selectedTab)
                .background(
                    RoundedRectangle(cornerRadius: 20)
                        .fill(.white)
                        .ignoresSafeArea(.all)
                )
            
            TabView(selection: $selectedTab) {
                switch selectedTab {
                case 1:
                    ScrollableAutoListView(
                        carList: viewModel.filterCars(by: .economy),
                        selectedAuto: $selectedAuto,
                        showModalCard: $showModalCard,
                        showModalReservation: $showModalReservation,
                        showSignSuggestModal: $showSignSuggestModal,
                        refreshAction: viewModel.fetchAllCars
                    )
                case 2:
                    ScrollableAutoListView(
                        carList: viewModel.filterCars(by: .comfort),
                        selectedAuto: $selectedAuto,
                        showModalCard: $showModalCard,
                        showModalReservation: $showModalReservation,
                        showSignSuggestModal: $showSignSuggestModal,
                        refreshAction: viewModel.fetchAllCars
                    )
                case 3:
                    ScrollableAutoListView(
                        carList: viewModel.filterCars(by: .premium),
                        selectedAuto: $selectedAuto,
                        showModalCard: $showModalCard,
                        showModalReservation: $showModalReservation,
                        showSignSuggestModal: $showSignSuggestModal,
                        refreshAction: viewModel.fetchAllCars
                    )
                case 4:
                    ScrollableAutoListView(
                        carList: viewModel.filterCars(by: .sedans),
                        selectedAuto: $selectedAuto,
                        showModalCard: $showModalCard,
                        showModalReservation: $showModalReservation,
                        showSignSuggestModal: $showSignSuggestModal,
                        refreshAction: viewModel.fetchAllCars
                    )
                case 5:
                    ScrollableAutoListView(
                        carList: viewModel.filterCars(by: .jeeps),
                        selectedAuto: $selectedAuto,
                        showModalCard: $showModalCard,
                        showModalReservation: $showModalReservation,
                        showSignSuggestModal: $showSignSuggestModal,
                        refreshAction: viewModel.fetchAllCars
                    )
                default:
                    ScrollableAutoListView(
                        carList: $viewModel.listOfAllCar,
                        selectedAuto: $selectedAuto,
                        showModalCard: $showModalCard,
                        showModalReservation: $showModalReservation,
                        showSignSuggestModal: $showSignSuggestModal,
                        refreshAction: viewModel.fetchAllCars
                    )
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
                Task {
                    await viewModel.fetchAllCars()
                }
            }
            .sheet(isPresented: $showModalCard) {
                ModalAutoView(
                    carModel: $selectedAuto,
                    showModalView: $showModalCard,
                    showSignSuggestModal: $showSignSuggestModal,
                    showModalReservation: $showModalReservation
                )
            }
        }
        .loadingOverlay(isLoading: $viewModel.isLoading)
        .background(Color(R.color.grayLightColor))
        .navigationBarBackButtonHidden()
        .toolbar {
            CustomToolbar(title: R.string.localizable.carParkScreenTitle)
        }
    }
}

#Preview {
    AutoListView(
        selectedAuto: Binding.constant(Car.companion.empty()),
        showModalReservation: Binding.constant(false),
        showSignSuggestModal: Binding.constant(false)
    )
}
