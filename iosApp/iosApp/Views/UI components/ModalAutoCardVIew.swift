//
//  ModalAutoCardVIew.swift
//  iosApp
//
//  Created by Sergey Ivanov on 25.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ModalAutoCardVIew: View {
    @Binding var autoData: AutoCard
    @Binding var showModalView: Bool
    
    var body: some View {
        VStack(spacing: 16) {
            Image.minus
                .foregroundStyle(Color(R.color.grayDarkColor))
                .frame(width: 50, height: 10)
            
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
            
            VStack {
                autoData.auto.image
                
                HStack {
                    Text(autoData.auto.title).font(.header3)
                    Spacer()
                    Text(autoData.auto.price ?? "")
                        .font(.header4)
                        .foregroundStyle(Color(R.color.blueColor))
                        .fontWeight(.bold)
                }
                .padding(.vertical, 20)
                
                VStack(alignment: .leading) {
                    Text(R.string.localizable.characteristicsTitle)
                        .font(.paragraph2)
                    
                    HStack {
                        HStack {
                            Image(R.image.transmission)
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .frame(width: 35, height: 35)
                            VStack(alignment: .leading) {
                                Text(R.string.localizable.transmissionTitle)
                                    .font(.caption1)
                                    .fontWeight(.bold)
                                Text(autoData.auto.transmisson.localizedStr)
                                    .foregroundStyle(Color(R.color.grayTextColor))
                                    .font(.caption1)
                                    .fontWeight(.bold)
                            }
                        }
                        Spacer()
                        HStack {
                            Image(R.image.gear)
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .frame(width: 35, height: 35)
                            VStack(alignment: .leading) {
                                Text(R.string.localizable.driveTypeTitle)
                                    .font(.caption1)
                                    .fontWeight(.bold)
                                Text(autoData.auto.driveType.localizedStr)
                                    .foregroundStyle(Color(R.color.grayTextColor))
                                    .font(.caption1)
                                    .fontWeight(.bold)
                            }
                        }
                        Spacer()
                    }
                    
                    HStack {
                        HStack {
                            Image(R.image.calendarYear)
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .frame(width: 35, height: 35)
                            VStack(alignment: .leading) {
                                Text(R.string.localizable.yearOfManufactureTitle)
                                    .font(.caption1)
                                    .fontWeight(.bold)
                                Text("\(autoData.auto.yearOfManufacture)")
                                    .foregroundStyle(Color(R.color.grayTextColor))
                                    .font(.caption1)
                                    .fontWeight(.bold)
                            }
                        }
                        Spacer()
                        HStack {
                            Image(R.image.gasoline)
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .frame(width: 35, height: 35)
                            VStack(alignment: .leading) {
                                Text(R.string.localizable.tankTitle)
                                    .font(.caption1)
                                    .fontWeight(.bold)
                                Text(autoData.auto.tankVolume)
                                    .foregroundStyle(Color(R.color.grayTextColor))
                                    .font(.caption1)
                                    .fontWeight(.bold)
                            }
                        }
                        Spacer()
                    }
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
