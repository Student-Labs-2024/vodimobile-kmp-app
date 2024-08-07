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
    @State private var showDatePicker = false
    @State private var dateRange: ClosedRange<Date>?
    @State private var time: Date
    @State var selectedPlace: Place?
    let car: Car
    let dates: String?
    private let carPreview: Image
    private let carPrice: Float
    private let carYear: Int
    // TODO: - Add network logic for request all place for pick up cars
    
    init(car: Car, dates: String?) {
        self.car = car
        if let image = car.images.first,
           let tariff = car.tariffs.first,
           let year = car.year
        {
            self.carPreview = Image(ImageResource(name: image.assetImageName, bundle: image.bundle))
            self.carPrice = tariff.cost
            self.carYear = Int(truncating: year)
        } else {
            self.carPreview = Image.questionFolder
            self.carPrice = 0
            self.carYear = 0
        }
        self.dates = dates
    }
    
    var body: some View {
        ZStack {
            ScrollView(.vertical) {
                VStack(spacing: 24) {
                    HStack {
                        carPreview
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                            .frame(maxWidth: screenWidth / 2.3)
                        
                        Spacer()
                        
                        VStack(alignment: .leading, spacing: 12) {
                            VStack(alignment: .leading) {
                                Text(R.string.localizable.autoNameTitle)
                                    .font(.paragraph5)
                                    .foregroundStyle(Color(R.color.grayTextColor))
                                Text(car.model.resource)
                                    .font(.header5)
                            }
                            
                            if let dates = dates {
                                VStack(alignment: .leading) {
                                    Text(R.string.localizable.autoDatesTitle)
                                        .font(.paragraph5)
                                        .foregroundStyle(Color(R.color.grayTextColor))
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
                            .fill(Color(R.color.blueBoxColor))
                    )
                    
                    if dates == nil {
                        ButtonLikeBorderedTextField(fieldType: .datePicker)
                    }
                    ButtonLikeBorderedTextField(fieldType: .placePicker)
                    ButtonLikeBorderedTextField(fieldType: .timePicker)
                    
                    Spacer()
                    
                    
                    
                }
            }
            
            if showDatePicker {
                ModalDatePickerView(
                    showDatePicker: $showDatePicker,
                    dateRange: $dateRange
                )
            }
        }
        .padding(.horizontal, horizontalPadding)
        .navigationBarBackButtonHidden()
        .toolbar {
            CustomToolbar(title: R.string.localizable.reservationScreenTitle)
        }
    }
}

#Preview {
    MakeReservationView(car: Car.companion.empty(), dates: "11 - 18 июля 2024")
}
