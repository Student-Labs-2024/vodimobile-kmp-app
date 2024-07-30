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
    @Binding var autoData: Car
    @Binding var showModalView: Bool

    var body: some View {
        if #available(iOS 16.4, *) {
            ModalAutoCardView(autoData: $autoData, showModal: $showModalView)
                .presentationDetents([.fraction(0.64)])
                .presentationDragIndicator(.visible)
                .presentationCornerRadius(24)
        } else {
            ModalAutoCardView(autoData: $autoData, showModal: $showModalView)
                .presentationDetents([.fraction(0.64)])
                .presentationDragIndicator(.visible)
        }
    }
}


struct ModalAutoCardView: View {
    @Binding var autoData: Car
    @Binding var showModalView: Bool
    private var carPreview: Image
    private var carPrice: Float
    
    let columns = [
        GridItem(.flexible(), spacing: 20),
        GridItem(.flexible(), spacing: 20)
    ]
    
    init(autoData: Binding<Car>, showModal: Binding<Bool>) {
        self._autoData = autoData
        if let image = autoData.wrappedValue.images.first, let tariff = autoData.wrappedValue.tariffs.first {
            self.carPreview = Image(ImageResource(name: image.assetImageName, bundle: image.bundle))
            self.carPrice = tariff.cost
        } else {
            self.carPreview = Image.bell
            self.carPrice = 1999
        }
        self._showModalView = showModal
    }
    
    var body: some View {
        VStack(spacing: 16) {
            
            Spacer()
            
            HStack {
                Spacer()
                ZStack {
                    Image.xmark
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 10, height: 10)
                        .fontWeight(.bold)
                        .foregroundStyle(Color(R.color.grayDarkColor))
                }
                .frame(width: 30, height: 30)
                .background(Circle().fill(Color(R.color.grayLightColor)))
                .onTapGesture {
                    showModalView.toggle()
                }
            }
            .padding(.top, 10)
            
            VStack {
                carPreview
                    .resizable()
                    .aspectRatio(contentMode: .fill)
                    .padding(.horizontal, 55)
                
                HStack {
                    Text(autoData.model.resource).font(.header3)
                    Spacer()
                    Text("от \(Int(carPrice)) руб.")
                        .font(.header4)
                        .foregroundStyle(Color(R.color.blueColor))
                        .fontWeight(.bold)
                }
                .padding(.vertical, 15)
                
                VStack(alignment: .leading, spacing: 10) {
                    Text(R.string.localizable.characteristicsTitle)
                        .font(.paragraph2)
                    
                    LazyVGrid(columns: columns, alignment: .leading, spacing: 20) {
                        HStack(spacing: 18) {
                            Image(R.image.transmission)
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .frame(width: 35, height: 35)
                            VStack(alignment: .leading, spacing: 5) {
                                Text(R.string.localizable.transmissionTitle)
                                    .font(.caption1)
                                    .fontWeight(.bold)
                                Text(autoData.transmission.resource)
                                    .foregroundStyle(Color(R.color.grayTextColor))
                                    .font(.caption1)
                                    .fontWeight(.bold)
                            }
                        }
                        
                        HStack(spacing: 18) {
                            Image(R.image.gear)
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .frame(width: 35, height: 35)
                            VStack(alignment: .leading, spacing: 5) {
                                Text(R.string.localizable.driveTypeTitle)
                                    .font(.caption1)
                                    .fontWeight(.bold)
                                Text(autoData.wheelDrive.resource)
                                    .foregroundStyle(Color(R.color.grayTextColor))
                                    .font(.caption1)
                                    .fontWeight(.bold)
                            }
                        }
                        
                        HStack(spacing: 18) {
                            Image(R.image.calendarYear)
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .frame(width: 35, height: 35)
                            VStack(alignment: .leading, spacing: 5) {
                                Text(R.string.localizable.yearOfManufactureTitle)
                                    .font(.caption1)
                                    .fontWeight(.bold)
                                Text("\(autoData.year)".replacingOccurrences(of: " ", with: ""))
                                    .foregroundStyle(Color(R.color.grayTextColor))
                                    .font(.caption1)
                                    .fontWeight(.bold)
                            }
                        }
                        
                        HStack(spacing: 18) {
                            Image(R.image.gasoline)
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .frame(width: 35, height: 35)
                            VStack(alignment: .leading, spacing: 5) {
                                Text(R.string.localizable.tankTitle)
                                    .font(.caption1)
                                    .fontWeight(.bold)
                                Text(autoData.tankValue.resource)
                                    .foregroundStyle(Color(R.color.grayTextColor))
                                    .font(.caption1)
                                    .fontWeight(.bold)
                            }
                        }
                    }
                    .padding(.vertical, 10)
                }
            }
            
            Button(R.string.localizable.bookButton()) {
                // TODO: - Make the auto reserve logic
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
