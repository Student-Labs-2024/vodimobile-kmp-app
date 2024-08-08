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
    @State private var inputErrorType: InputErrorType?
    @State private var time: Date = Date()
    @State var selectedPlace: Place?
    @State private var showPlacePicker = false
    @State private var totalPrice: Int = 0
    @State private var comment: String = ""
    @FocusState private var focuseOnCommentField: Bool
    @Binding private var showModal: Bool
    @ObservedObject var viewModel = MakeReservationViewModel()
    
    @ViewBuilder private var destinationView: some View {
        if viewModel.isSuccessed {
            SuccessfulReservationView()
        } else {
            FailureReservationView()
        }
    }
    private let car: Car
    private let dates: String?
    private let carPreview: Image
    private let carPrice: Float
    private let carYear: Int
    
    init(
        car: Car,
        dates: String?,
        showModal: Binding<Bool>? = nil
    ) {
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
        self._showModal = showModal ?? Binding.constant(false)
    }
    
    var body: some View {
        NavigationView {
            ZStack(alignment: .top) {
                VStack {
                    HStack {
                        Button(action: {
                            showModal.toggle()
                        }, label: {
                            Image.chevronLeft.foregroundStyle(Color.black).fontWeight(.bold)
                        })
                        Text(R.string.localizable.reservationScreenTitle)
                            .font(.header1)
                            .foregroundColor(Color.black)
                            .frame(maxWidth: .infinity)
                    }
                    ScrollView(.vertical, showsIndicators: false) {
                        VStack(alignment: .leading, spacing: 24) {
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
                                ButtonLikeBorderedTextField(
                                    fieldType: .datePicker,
                                    showDatePicker: $showDatePicker,
                                    inputErrorType: $inputErrorType,
                                    dateRange: $dateRange
                                )
                            }
                            
                            ButtonLikeBorderedTextField(
                                fieldType: .placePicker,
                                showPlacePicker: $showPlacePicker,
                                inputErrorType: $inputErrorType,
                                selectedPlace: $selectedPlace,
                                placesDataSource: $viewModel.placesWithCost
                            )
                            
                            ButtonLikeBorderedTextField(
                                fieldType: .timePicker,
                                inputErrorType: $inputErrorType,
                                time: $time
                            )
                            
                            AutoSizingTextEditor(text: $comment, isFocused: $focuseOnCommentField)
                            
                            
                            Spacer()
                            
                            
                            
                        }
                    }
                    
                    VStack(spacing: 20) {
                        HStack {
                            Text(R.string.localizable.totalPriceTitle)
                                .font(.header3)
                            Spacer()
                            Text("\(totalPrice) \(R.string.localizable.currencyPriceText())")
                                .font(.header3)
                        }
                        
                        NavigationLink(R.string.localizable.leaveReuqestButton()) {
                            destinationView
                        }
                        .buttonStyle(FilledBtnStyle())
                    }
                    .padding(.horizontal, 10)
                    .padding(.vertical, 20)
                }
                
                if showDatePicker {
                    ModalDatePickerView(
                        showDatePicker: $showDatePicker,
                        dateRange: $dateRange
                    )
                } else if showPlacePicker {
                    DatePicker(
                        "",
                        selection: $time,
                        displayedComponents: .hourAndMinute
                    )
                }
            }
            .padding(.horizontal, horizontalPadding)
            .navigationBarBackButtonHidden()
        }
    }
}

#Preview {
    MakeReservationView(
        car: Car.companion.empty(),
        dates: "11 - 18 июля 2024"
    )
}
