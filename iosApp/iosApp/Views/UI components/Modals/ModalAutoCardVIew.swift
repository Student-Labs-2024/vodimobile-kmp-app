//
//  ModalAutoCardVIew.swift
//  iosApp
//
//  Created by Sergey Ivanov on 25.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct ModalAutoView: View {
    @Binding var carModel: Car
    @Binding var showModalView: Bool
    @Binding var showModalReservation: Bool
    @Binding var showSignSuggestModal: Bool

    init(
        carModel: Binding<Car>,
        showModalView: Binding<Bool>,
        showSignSuggestModal: Binding<Bool>,
        showModalReservation: Binding<Bool>? = nil
    ) {
        self._carModel = carModel
        self._showModalView = showModalView
        self._showSignSuggestModal = showSignSuggestModal
        self._showModalReservation = showModalReservation ?? Binding.constant(false)
    }

    var body: some View {
        let ModalAutoCardView = ModalAutoCardView(
            carModel: $carModel,
            showModal: $showModalView,
            showModalReservation: $showModalReservation,
            showSignSuggestModal: $showSignSuggestModal
        )
        .presentationDetents([.fraction(0.64)])
        .presentationDragIndicator(.visible)

        if #available(iOS 16.4, *) {
            ModalAutoCardView.presentationCornerRadius(24)
        } else {
            ModalAutoCardView
        }
    }
}

struct ModalAutoCardView: View {
    @Binding var showModalView: Bool
    @Binding var showModalReservation: Bool
    @Binding var showSignSuggestModal: Bool
    @ObservedObject var authManager = AuthManager.shared
    @ObservedObject var viewModel: AutoCardViewModel
    private let columns = [
        GridItem(.flexible(), spacing: 20),
        GridItem(.flexible(), spacing: 20)
    ]

    init(
        carModel: Binding<Car>,
        showModal: Binding<Bool>,
        showModalReservation: Binding<Bool>,
        showSignSuggestModal: Binding<Bool>
    ) {
        self.viewModel = .init(carModel: carModel)
        self._showModalReservation = showModalReservation
        self._showModalView = showModal
        self._showSignSuggestModal = showSignSuggestModal
    }

    var body: some View {
        VStack {
            HStack {
                Spacer()
                ZStack {
                    Image.xmark
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 10, height: 10)
                        .fontWeight(.bold)
                        .foregroundStyle(Color(R.color.grayDark))
                }
                .frame(width: 30, height: 30)
                .background(Circle().fill(Color(R.color.grayLight)))
                .onTapGesture {
                    showModalView.toggle()
                }
            }
            .padding(.top, 30)

            Spacer()

            VStack {
                viewModel.carPreview
                    .resizable()
                    .aspectRatio(contentMode: .fill)
                    .frame(maxHeight: 200)
                    .padding(.horizontal, 55)

                HStack {
                    Text(viewModel.carModel.model.resource).font(.header3)
                    Spacer()
                    if let carPrice = viewModel.carModel.tariffs.first?.cost {
                        Text(
                            R.string.localizable.prepositionPriceText() +
                            " \(Int(carPrice)) " +
                            R.string.localizable.currencyPriceText()
                        )
                        .font(.header4)
                        .foregroundStyle(Color(R.color.blueColor))
                        .fontWeight(.bold)
                    }
                }
                .padding(.vertical, 15)

                VStack(alignment: .leading, spacing: 10) {
                    Text(R.string.localizable.characteristicsTitle)
                        .font(.paragraph2)

                    LazyVGrid(columns: columns, alignment: .leading, spacing: 15) {
                        CarGridItem(
                            gridItemType: .transmission,
                            value: viewModel.carModel.transmission.resource
                        )

                        CarGridItem(
                            gridItemType: .gear,
                            value: viewModel.carModel.wheelDrive.resource
                        )

                        if let carYear = viewModel.carModel.year {
                            CarGridItem(
                                gridItemType: .yearDev,
                                value: carYear.stringValue
                            )
                        }

                        CarGridItem(
                            gridItemType: .gasoline,
                            value: viewModel.carModel.tankValue.resource
                        )
                    }
                    .padding(.vertical, 10)
                }
            }

            Button(R.string.localizable.bookButton()) {
                showModalView.toggle()
                if authManager.isAuthenticated {
                    showModalReservation.toggle()
                } else {
                    showSignSuggestModal.toggle()
                }
            }
            .buttonStyle(FilledBtnStyle())

            Spacer()
        }
        .padding(.horizontal, 24)
        .padding(.vertical, 16)
        .background(Color.white)
        .clipShape(RoundedRectangle(cornerRadius: 20))
    }
}
