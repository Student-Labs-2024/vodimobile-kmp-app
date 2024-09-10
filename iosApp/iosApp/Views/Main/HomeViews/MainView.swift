//
//  MainView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 11.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct MainView: View {
    @Binding var selectedTab: TabType
    @State private var isExpanded = true
    @Binding var showDatePicker: Bool
    @State private var scrollOffset: CGPoint = .zero
    @State private var headerHeight: CGFloat = 0
    @State private var dragOffset: CGSize = .zero
    @State private var showModalCard: Bool = false
    @State private var showModalReservation: Bool = false
    @State private var showSignSuggestModal: Bool = false
    @ObservedObject var authManager = AuthManager.shared
    @ObservedObject private var viewModel = MainViewModel()

    var body: some View {
        NavigationView {
            ZStack(alignment: .top) {
                ScrollViewWithOffset(onScroll: handleScroll) {
                    LazyVStack(spacing: 20) {
                        HStack {
                            Text(R.string.localizable.popularAuto).font(.header3)
                            Spacer()
                            NavigationLink(R.string.localizable.allAutoButton()) {
                                AutoListView(
                                    selectedAuto: $viewModel.selectedAuto,
                                    showModalReservation: $showModalReservation,
                                    showSignSuggestModal: $showSignSuggestModal,
                                    showDatePicker: $showDatePicker,
                                    dateRange: $viewModel.dateRange
                                )
                            }
                            .font(.buttonTabbar)
                            .foregroundStyle(Color(R.color.blueColor))
                        }
                        .padding(.bottom, 10)

                        ForEach(viewModel.listOfPopularCar.indices, id: \.self) { index in
                            AutoSimpleCardView(
                                carModel: $viewModel.listOfPopularCar[index],
                                showModal: $showModalCard,
                                showSignSuggestModal: $showSignSuggestModal,
                                selectedAuto: $viewModel.selectedAuto
                            )
                        }
                        AutoGeneralCardView(
                            selectedAuto: $viewModel.selectedAuto,
                            dateRange: $viewModel.dateRange,
                            showModalReservation: $showModalReservation,
                            showDatePicker: $showDatePicker,
                            showSignSuggestModal: $showSignSuggestModal
                        )
                    }
                    .padding(.top, headerHeight * 1.75)
                    .padding(.horizontal, 24)
                }

                ExpandableToolbar(
                    selectedAuto: $viewModel.selectedAuto,
                    showModalReservation: $showModalReservation,
                    showSignSuggestModal: $showSignSuggestModal,
                    isExpanded: $isExpanded,
                    dateRange: $viewModel.dateRange,
                    showDatePicker: $showDatePicker,
                    headerHeight: $headerHeight,
                    dragOffset: $dragOffset
                )
            }
            .sheet(isPresented: $showModalCard) {
                ModalAutoView(
                    carModel: $viewModel.selectedAuto,
                    showModalView: $showModalCard,
                    showSignSuggestModal: $showSignSuggestModal,
                    showModalReservation: $showModalReservation
                )
            }
            .ignoresSafeArea(.container, edges: .top)
            .background(Color(R.color.bgContainer))
        }
        .datePickerModalOverlay(
            showDatePicker: $showDatePicker,
            dateRange: $viewModel.dateRange
        )
        .fullScreenCover(
            isPresented: $showModalReservation,
            content: {
                MakeReservationView(
                    car: viewModel.selectedAuto,
                    selectedTab: $selectedTab,
                    dates: viewModel.dateRange,
                    showModal: $showModalReservation,
                    showDatePicker: $showDatePicker
                )
            }
        )
        .fullScreenCover(
            isPresented: $showSignSuggestModal,
            content: {
                SignSuggestView(showSignSuggestModal: $showSignSuggestModal)
            }
        )
    }

    func handleScroll(_ offset: CGPoint) {
        self.scrollOffset = offset
        isExpanded = offset.y < 0 ? false : true
    }
}

#Preview {
    MainView(
        selectedTab: Binding.constant(.main),
        showDatePicker: Binding.constant(false)
    )
}
