//
//  OrderDetailView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 17.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct OrderDetailView: View {
    @State private var showCancelAlert = false
    @Binding var showOrderModal: Bool
    @Binding var selectedTab: TabType
    @Binding var showDatePicker: Bool
    @ObservedObject var viewModel: OrderDetailViewModel
    private let columns = [
        GridItem(.flexible(), spacing: 20),
        GridItem(.flexible(), spacing: 20)
    ]

    init(
        order: Order,
        showOrderModal: Binding<Bool>? = nil,
        selectedTab: Binding<TabType>? = nil,
        showDatePicker: Binding<Bool>
    ) {
        self.viewModel = .init(order: order)
        self._showOrderModal = showOrderModal ?? Binding.constant(false)
        self._selectedTab = selectedTab ?? Binding.constant(.main)
        self._showDatePicker = showDatePicker
    }

    var body: some View {
        NavigationStack {
            VStack {
                HStack {
                    Button(action: {
                        showOrderModal.toggle()
                    }, label: {
                        Image.chevronLeft
                            .foregroundStyle(Color(R.color.text))
                            .fontWeight(.bold)
                    })
                    Text(R.string.localizable.aboutOrder)
                        .font(.header1)
                        .foregroundStyle(Color(R.color.text))
                        .frame(maxWidth: .infinity)
                }
                ScrollView(.vertical, showsIndicators: false) {
                    VStack(alignment: .leading, spacing: 24) {
                        HStack {
                            viewModel.orderCarPreview
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .frame(maxWidth: screenWidth / 3.5)
                                .padding(.horizontal, 20)

                            VStack(alignment: .leading, spacing: 12) {
                                VStack(alignment: .leading) {
                                    Text(R.string.localizable.autoNameTitle)
                                        .font(.paragraph5)
                                        .foregroundStyle(Color(R.color.grayText))
                                    Text(viewModel.order.car.model.resource)
                                        .font(.header5)
                                }
                            }
                            .multilineTextAlignment(.leading)

                            Spacer()
                        }
                        .padding(.horizontal, horizontalPadding)
                        .padding(.vertical, 24)
                        .background(
                            RoundedRectangle(cornerRadius: 16)
                                .fill(Color(R.color.blueBox))
                        )

                    }

                    VStack(spacing: 25) {
                        HStack {
                            Text("\(R.string.localizable.orderStatus()):")
                                .font(.header3)
                            Spacer()
                            BidStatusText(status: viewModel.order.status)
                        }
                        HStack {
                            Text("\(R.string.localizable.rentalPeriod()):")
                                .font(.paragraph5)
                            Spacer()
                            Text(CustomDateFormatter.shared.formatDates(
                                startDateInMillis: viewModel.order.rentalDatePeriod.startDate,
                                endDateInMillis: viewModel.order.rentalDatePeriod.endDate
                            )
                            )
                        }
                        HStack {
                            Text("\(R.string.localizable.startPlaceOfObtainingTitle()):")
                                .font(.paragraph5)
                            Spacer()
                            Text(viewModel.order.startLocation)
                        }
                        HStack {
                            Text("\(R.string.localizable.startRentalTime()):")
                                .font(.paragraph5)
                            Spacer()
                            Text("\(viewModel.order.rentalTimePeriod.startTime) - \(viewModel.order.rentalTimePeriod.finishTime)"
                            )
                        }

                        VStack(alignment: .leading) {
                            Text(R.string.localizable.characteristicsTitle)
                                .font(.paragraph2)

                            LazyVGrid(columns: columns, alignment: .leading, spacing: 15) {
                                CarGridItem(
                                    gridItemType: .transmission,
                                    value: viewModel.order.car.transmission.resource
                                )

                                CarGridItem(
                                    gridItemType: .gear,
                                    value: viewModel.order.car.wheelDrive.resource
                                )

                                if let carYear = viewModel.order.car.year {
                                    CarGridItem(
                                        gridItemType: .yearDev,
                                        value: carYear.stringValue
                                    )
                                }

                                CarGridItem(
                                    gridItemType: .gasoline,
                                    value: viewModel.order.car.tankValue.resource
                                )
                            }
                            .padding(.vertical, 15)

                            Rectangle()
                                .fill(Color.gray)
                                .frame(height: 1, alignment: .bottom)
                                .opacity(0.5)
                        }

                        HStack {
                            Text(R.string.localizable.totalPriceTitle)
                                .font(.header3)
                            Spacer()
                            Text("\(Int(viewModel.order.bid.cost)) \(R.string.localizable.currencyText())")
                                .font(.header3)
                        }
                    }
                    .multilineTextAlignment(.leading)
                    .padding(.vertical, 20)

                    HStack {
                        switch onEnum(of: viewModel.order.status) {
                        case .approved, .reserve:
                            Button(action: {
                                showCancelAlert.toggle()
                            }, label: {
                                Text(R.string.localizable.cancelBidButton)
                                    .frame(maxWidth: .infinity)
                                    .font(.caption1)
                                    .foregroundStyle(Color(R.color.redColor))
                                    .padding(.vertical, 20)
                                    .padding(.horizontal, 24)
                                    .background(
                                        RoundedRectangle(
                                            cornerRadius: 15
                                        ).fill(Color(R.color.grayLight))
                                    )
                            })
                        case .processing:
                            NavigationLink(R.string.localizable.changeBidData()) {
                                MakeReservationView(
                                    car: viewModel.order.car,
                                    selectedTab: $selectedTab,
                                    showDatePicker: $showDatePicker
                                )
                            }
                            .buttonStyle(FilledBtnWithoutDisabledStyle())
                            .font(.buttonSmall)

                            Button(action: {
                                showCancelAlert.toggle()
                            }, label: {
                                Text(R.string.localizable.cancelShortButton)
                                    .font(.caption1)
                                    .foregroundStyle(Color(R.color.redColor))
                                    .padding(.vertical, 20)
                                    .padding(.horizontal, 24)
                                    .background(
                                        RoundedRectangle(
                                            cornerRadius: 15
                                        ).fill(Color(R.color.grayLight))
                                    )
                            })
                        case .completed:
                            EmptyView()
                        case .cancelled:
                            EmptyView()
                        }

                    }
                    .padding(.top, 30)
                }
            }
            .padding(.horizontal, horizontalPadding)
            .navigationBarBackButtonHidden()
            .alert(
                R.string.localizable.alertCancelBidTitle(),
                isPresented: $showCancelAlert
            ) {
                Button(R.string.localizable.backButton(), role: .cancel) {
                    showCancelAlert.toggle()
                }

                Button(R.string.localizable.cancelShortButton(), role: .destructive) {
                    Task {
                        if let user = viewModel.dataStorage.gettingUser {
                            await viewModel.apiManager.updateOrderStatus(
                                userId: user.id,
                                orderId: viewModel.order.orderId,
                                status: CarStatus.Cancelled().title.resource
                            )
                        }
                    }
                }
            }
        }
    }
}

#Preview {
    OrderDetailView(order: Order.companion.empty(), showDatePicker: Binding.constant(false))
}
