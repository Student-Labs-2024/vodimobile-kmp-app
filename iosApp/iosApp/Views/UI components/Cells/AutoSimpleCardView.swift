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
    let car: Car
    private var carPreview: Image
    private var carPrice: Float
    @Binding var showModal: Bool
    @Binding var selectedAuto: Car
    
    init(car: Car, showModal: Binding<Bool>, selectedAuto: Binding<Car>) {
        self.car = car
        if let image = car.images.first, let tariff = car.tariffs.first {
            self.carPreview = Image(ImageResource(name: image.assetImageName, bundle: image.bundle))
            self.carPrice = tariff.cost
        } else {
            self.carPreview = Image.bell
            self.carPrice = 1999
        }
        self._showModal = showModal
        self._selectedAuto = selectedAuto
    }
    
    var body: some View {
        VStack(spacing: 12) {
            carPreview
                .resizable()
                .aspectRatio(contentMode: .fit)
                .padding(.horizontal, 25)
            
            HStack {
                VStack(alignment: .leading, spacing: 8) {
                    Text(car.model.resource).font(.header3)
                    Text("от \(Int(carPrice)) руб.")
                        .font(.header4)
                        .fontWeight(.bold)
                        .foregroundStyle(Color(R.color.blueColor))
                        .multilineTextAlignment(.leading)
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
                    selectedAuto = car
                    showModal = true
                }
            }
        }
        .padding(.horizontal, 32)
        .padding(.vertical, 24)
        .background(RoundedRectangle(cornerRadius: 24).fill(.white))
    }
}