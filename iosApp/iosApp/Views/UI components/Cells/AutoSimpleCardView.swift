//
//  AutoCardView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 24.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct AutoSimpleCardView: View {
    @Binding var showModal: Bool
    @Binding var selectedAuto: Car
    @ObservedObject var viewModel: AutoCardViewModel
    private let columns = [
        GridItem(.flexible(), spacing: 20),
        GridItem(.flexible(), spacing: 20)
    ]
    
    init(
        carModel: Binding<Car>,
        showModal: Binding<Bool>,
        selectedAuto: Binding<Car>
    ) {
        self.viewModel = .init(carModel: carModel)
        self._showModal = showModal
        self._selectedAuto = selectedAuto
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
