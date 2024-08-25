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
    @Binding var dateRange: ClosedRange<Date>?
    @State private var selectedTab: Int = 0
    @State private var showModalCard: Bool = false
    @State private var dragOffset: CGSize = .zero
    @ObservedObject private var viewModel = AutoListViewModel()

    init(
        selectedAuto: Binding<Car>,
        showModalReservation: Binding<Bool>,
        showSignSuggestModal: Binding<Bool>,
        dateRange: Binding<ClosedRange<Date>?>? = nil
    ) {
        self._selectedAuto = selectedAuto
        self._showModalReservation = showModalReservation
        self._showSignSuggestModal = showSignSuggestModal
        self._dateRange = dateRange ?? nil
    }

    var body: some View {
        VStack {
            if dateRange != nil {
                HStack(spacing: 10) {
                    Image(R.image.calendar)
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 30, height: 30)
                        .foregroundColor(Color(R.color.grayDark))
                    Text(formatDateRange())
                        .foregroundColor(
                            dateRange == nil
                            ? Color(R.color.grayDark)
                            : Color(R.color.background)
                        )
                    Spacer()
                }
                .frame(alignment: .leading)
                .padding(.all, 16)
                .background(
                    RoundedRectangle(cornerRadius: 10)
                        .fill(Color(R.color.containerItem))
                )
                .overlay {
                    RoundedRectangle(cornerRadius: 10)
                        .stroke(Color(R.color.grayDark), lineWidth: 1)
                }
            }
            TabBarView(index: $selectedTab)
                .background(
                    RoundedRectangle(cornerRadius: 20)
                        .fill(Color(R.color.background))
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
            .sheet(isPresented: $showModalCard) {
                ModalAutoView(
                    carModel: $selectedAuto,
                    showModalView: $showModalCard,
                    showSignSuggestModal: $showSignSuggestModal,
                    showModalReservation: $showModalReservation
                )
            }
        }
        .onAppear {
            Task {
                await viewModel.fetchAllCars()
            }
        }
        .loadingOverlay(isLoading: $viewModel.isLoading)
        .background(Color(R.color.bgContainer))
        .navigationBarBackButtonHidden()
        .toolbar {
            CustomToolbar(title: R.string.localizable.carParkScreenTitle)
        }
    }
    
    func formatDateRange() -> String {
        guard let dateRange = dateRange else {
            return R.string.localizable.dateTextFieldPlaceholder()
        }
        
        let formatter = DateFormatter()
        formatter.dateFormat = "dd MMMM yyyy"
        
        let startDate = formatter.string(from: dateRange.lowerBound)
        let endDate = formatter.string(from: dateRange.upperBound)
        
        if startDate == endDate {
            return startDate
        } else if calendar.compare(dateRange.lowerBound, to: dateRange.upperBound, toGranularity: .day) == .orderedAscending {
            return "\(startDate) - \(endDate)"
        } else {
            return "\(endDate) - \(startDate)"
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
