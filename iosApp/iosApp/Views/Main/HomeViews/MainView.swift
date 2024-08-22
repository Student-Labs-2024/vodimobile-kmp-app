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
    @State private var isExpanded = true
    @State private var showDatePicker = false
    @State private var dateRange: ClosedRange<Date>?
    @State private var scrollOffset: CGPoint = .zero
    @State private var headerHeight: CGFloat = 0
    @State private var dragOffset: CGSize = .zero
    @State private var showModalCard: Bool = false
    @State private var selectedAuto: Car = Car.companion.empty()
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
                                    selectedAuto: $selectedAuto,
                                    showModalReservation: $showModalReservation,
                                    showSignSuggestModal: $showSignSuggestModal
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
                                selectedAuto: $selectedAuto
                            )
                        }
                        AutoGeneralCardView(
                            selectedAuto: $selectedAuto,
                            showModalReservation: $showModalReservation,
                            showSignSuggestModal: $showSignSuggestModal
                        )
                    }
                    .padding(.top, headerHeight * 1.75)
                    .padding(.horizontal, 24)
                }

                // Expandable Toolbar
                ExpandableToolbar(
                    isExpanded: $isExpanded,
                    dateRange: $dateRange,
                    showDatePicker: $showDatePicker,
                    headerHeight: $headerHeight,
                    dragOffset: $dragOffset
                )

                // Date Picker Modal
                if showDatePicker {
                    ModalDatePickerView(
                        showDatePicker: $showDatePicker,
                        dateRange: $dateRange
                    )
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
            .ignoresSafeArea(.container, edges: .top)
            .background(Color(R.color.bgContainer))
        }
        .fullScreenCover(
            isPresented: $showModalReservation,
            content: {
                MakeReservationView(
                    car: selectedAuto,
                    dates: nil,
                    showModal: $showModalReservation
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
    MainView()
}
