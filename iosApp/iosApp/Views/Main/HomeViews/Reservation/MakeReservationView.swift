//
//  MakeReservationView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 07.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct MakeReservationView: View {
    @Binding private var showModal: Bool
    @ObservedObject var viewModel: MakeReservationViewModel
    @State private var navigationPath = NavigationPath()
    @Environment(\.dismiss) private var dismiss

    enum Destination: Hashable {
        case successView
        case failureView
    }

    init(
        car: Car,
        dates: String?,
        showModal: Binding<Bool>? = nil
    ) {
        self.viewModel = .init(car: car, dates: dates)
        self._showModal = showModal ?? Binding.constant(false)
    }

    var body: some View {
        NavigationView {
            ZStack(alignment: .top) {
                VStack {
                    HStack {
                        Button(action: {
                            showModal.toggle()
                            dismiss()
                        }, label: {
                            Image.chevronLeft
                                .foregroundStyle(Color(R.color.text))
                                .fontWeight(.bold)
                        })
                        Text(R.string.localizable.reservationScreenTitle)
                            .font(.header1)
                            .foregroundStyle(Color(R.color.text))
                            .frame(maxWidth: .infinity)
                    }
                    ScrollView(.vertical, showsIndicators: false) {
                        VStack(alignment: .leading, spacing: 24) {
                            HStack {
                                viewModel.carPreview
                                    .resizable()
                                    .aspectRatio(contentMode: .fit)
                                    .frame(maxWidth: screenWidth / 2.3)

                                Spacer()

                                VStack(alignment: .leading, spacing: 12) {
                                    VStack(alignment: .leading) {
                                        Text(R.string.localizable.autoNameTitle)
                                            .font(.paragraph5)
                                            .foregroundStyle(Color(R.color.grayText))
                                        Text(viewModel.car.model.resource)
                                            .font(.header5)
                                    }

                                    if let dates = viewModel.dates {
                                        VStack(alignment: .leading) {
                                            Text(R.string.localizable.autoDatesTitle)
                                                .font(.paragraph5)
                                                .foregroundStyle(Color(R.color.grayText))
                                            Text(dates).font(.header5)
                                        }
                                    }
                                }
                                .multilineTextAlignment(.leading)
                            }
                            .padding(.horizontal, horizontalPadding)
                            .padding(.vertical, 24)
                            .background(
                                RoundedRectangle(cornerRadius: 16)
                                    .fill(Color(R.color.blueBox))
                            )

                            if viewModel.dates == nil {
                                ButtonLikeBorderedTextField(
                                    fieldType: .datePicker,
                                    showDatePicker: $viewModel.showDatePicker,
                                    inputErrorType: $viewModel.inputErrorType,
                                    dateRange: $viewModel.dateRange
                                )
                            }

                            ButtonLikeBorderedTextField(
                                fieldType: .startPlacePicker,
                                inputErrorType: $viewModel.inputErrorType,
                                selectedPlace: $viewModel.startPlace,
                                placesDataSource: $viewModel.placesWithCost
                            )

                            ButtonLikeBorderedTextField(
                                fieldType: .startTimePicker,
                                inputErrorType: $viewModel.inputErrorType,
                                time: $viewModel.startTime,
                                showTimePicker: $viewModel.showStartTimePicker
                            )

                            ButtonLikeBorderedTextField(
                                fieldType: .endPlacePicker,
                                inputErrorType: $viewModel.inputErrorType,
                                selectedPlace: $viewModel.endPlace,
                                placesDataSource: $viewModel.placesWithCost
                            )

                            ButtonLikeBorderedTextField(
                                fieldType: .endTimePicker,
                                inputErrorType: $viewModel.inputErrorType,
                                time: $viewModel.endTime,
                                showTimePicker: $viewModel.showEndTimePicker
                            )

                            HorizontalServicesScrollView(
                                servicesList: $viewModel.servicesList,
                                selectedServicesList: $viewModel.selectedServices
                            )
                            Spacer()
                        }
                    }

                    VStack(spacing: 20) {
                        HStack {
                            Text(R.string.localizable.totalPriceTitle)
                                .font(.header3)
                            Spacer()
                            Text("\(viewModel.totalPrice) \(R.string.localizable.currencyPriceText())")
                                .font(.header3)
                        }

                        NavigationStack(path: $navigationPath) {
                            VStack {
                                Button(R.string.localizable.leaveReuqestButton(), action: {
                                    Task {
                                        await viewModel.createBidToReserve()
                                    }
                                })
                                .buttonStyle(FilledBtnStyle())
                                .disabled(
                                    viewModel.startPlace == nil &&
                                    viewModel.endPlace == nil &&
                                    viewModel.dateRange == nil
                                )
                            }
                            .navigationDestination(for: Destination.self) { destination in
                                switch destination {
                                case .successView:
                                    SuccessfulReservationView()
                                case .failureView:
                                    FailureReservationView()
                                }
                            }
                        }
                    }
                    .padding(.horizontal, 10)
                    .padding(.vertical, 20)
                }
                .padding(.horizontal, horizontalPadding)

                if viewModel.showDatePicker {
                    ModalDatePickerView(
                        showDatePicker: $viewModel.showDatePicker,
                        dateRange: $viewModel.dateRange
                    )
                } else if viewModel.showStartTimePicker {
                    ModalTimePicker(
                        selectedTime: $viewModel.startTime,
                        showTimePicker: $viewModel.showStartTimePicker
                    )
                } else if viewModel.showEndTimePicker {
                    ModalTimePicker(
                        selectedTime: $viewModel.endTime,
                        showTimePicker: $viewModel.showEndTimePicker
                    )
                }
            }
        }
        .loadingOverlay(isLoading: $viewModel.isLoading)
        .navigationBarBackButtonHidden()
    }
}

#Preview {
    MakeReservationView(
        car: Car.companion.empty(),
        dates: nil
    )
}

struct HorizontalServicesScrollView: View {
    @Binding var servicesList: [ServicesDTO]
    @Binding var selectedServicesList: [ServicesDTO]

    var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            LazyHStack(spacing: 16) {
                ForEach(servicesList, id: \.service_id) { service in
                    AdditionalServiceCell(
                        service: service,
                        selectedServicesList: $selectedServicesList
                    )
                }
            }
        }
    }
}

struct AdditionalServiceCell: View {
    let service: ServicesDTO
    @State var isSelected: Bool = false
    @Binding var selectedServicesList: [ServicesDTO]

    var body: some View {
        Button(action: {
            if !selectedServicesList.contains(where: { $0 == service }) {
                isSelected = true
                selectedServicesList.append(service)
            } else {
                isSelected = false
                if let selectedServiceIndex = selectedServicesList.firstIndex(of: service) {
                    selectedServicesList.remove(at: selectedServiceIndex)
                }
            }
        }, label: {
            if isSelected {
                VStack(spacing: 5) {
                    Text(service.title)
                        .font(.buttonTabbar)
                        .foregroundStyle(.white)
                    Text("+\(Int(service.cost)) \(R.string.localizable.currencyText())")
                        .font(.buttonTabbar)
                        .foregroundStyle(.white)
                }
                .padding(.horizontal, 24)
                .padding(.vertical, 8)
                .background(RoundedRectangle(cornerRadius: 10)
                    .fill(Color(R.color.blueColor)))
            } else {
                VStack(spacing: 5) {
                    Text(service.title)
                        .font(.buttonTabbar)
                        .foregroundStyle(Color(R.color.text))
                    Text("+\(Int(service.cost)) \(R.string.localizable.currencyText())")
                        .font(.buttonTabbar)
                        .foregroundStyle(Color(R.color.blueColor))
                }
                .padding(.horizontal, 24)
                .padding(.vertical, 8)
                .background(RoundedRectangle(cornerRadius: 10)
                    .fill(Color(R.color.blueBox)))
            }
        })
    }
}
