//
//  AutoCardView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 24.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct AutoCardWithButtonView: View {
    @Binding var showModal: Bool
    @Binding var selectedAuto: Car
    @Binding var showModalReservation: Bool
    @ObservedObject var viewModel: AutoCardViewModel
    private let columns = [
        GridItem(.flexible(), spacing: 20),
        GridItem(.flexible(), spacing: 20)
    ]
    
    init(
        carModel: Binding<Car>,
        selectedAuto: Binding<Car>,
        showModal: Binding<Bool>,
        showModalReservation: Binding<Bool>
    ) {
        self.viewModel = .init(carModel: carModel)
        self._selectedAuto = selectedAuto
        self._showModalReservation = showModalReservation
        self._showModal = showModal
    }
    
    var body: some View {
        VStack(spacing: 12) {
            viewModel.carPreview
                .resizable()
                .aspectRatio(contentMode: .fit)
                .padding(.horizontal, 25)
            
            HStack {
                VStack(alignment: .leading, spacing: 8) {
                    Text(viewModel.carModel.model.resource).font(.header3)
                    if let carPrice = viewModel.carModel.tariffs.first?.cost {
                        Text("\(R.string.localizable.prepositionPriceText()) \(Int(carPrice)) \(R.string.localizable.currencyPriceText())")
                            .font(.header4)
                            .fontWeight(.bold)
                            .foregroundStyle(Color(R.color.blueColor))
                            .multilineTextAlignment(.leading)
                    }
                }
                Spacer()
            }
            
            HStack {
                
                Button(R.string.localizable.bookButton()) {
                    showModalReservation.toggle()
                }
                .buttonStyle(FilledBtnStyle(heightButton: 40))
                .padding(.trailing, 20)
                
                ZStack {
                    Image.infoCircleFill
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .foregroundStyle(Color(R.color.grayDarkColor))
                        .frame(width: 20, height: 20)
                }
                .frame(width: 40, height: 40)
                .background(RoundedRectangle(cornerRadius: 10).fill(Color(R.color.grayLightColor)))
                .onTapGesture {
                    selectedAuto = viewModel.carModel
                    showModal = true
                }
            }
        }
        .padding(.horizontal, 32)
        .padding(.vertical, 24)
        .background(RoundedRectangle(cornerRadius: 24).fill(.white))
    }
}
