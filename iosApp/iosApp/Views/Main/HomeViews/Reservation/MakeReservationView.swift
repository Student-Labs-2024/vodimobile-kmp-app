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
    @Environment(\.dismiss) private var dismiss
    
    @ViewBuilder private var destinationView: some View {
        if viewModel.isSuccessed == .success {
            SuccessfulReservationView()
        } else {
            FailureReservationView()
        }
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
                                fieldType: .placePicker,
                                inputErrorType: $viewModel.inputErrorType,
                                selectedPlace: $viewModel.selectedPlace,
                                placesDataSource: $viewModel.placesWithCost
                            )
                            
                            ButtonLikeBorderedTextField(
                                fieldType: .timePicker,
                                inputErrorType: $viewModel.inputErrorType,
                                time: $viewModel.time,
                                showTimePicker: $viewModel.showTimePicker
                            )
                            
                            AutoSizingTextEditor(text: $viewModel.comment, isFocused: $viewModel.focuseOnCommentField)
                            
                            
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
                        
                        NavigationLink(R.string.localizable.leaveReuqestButton()) {
                            destinationView
                        }
                        .buttonStyle(FilledBtnStyle())
                        .disabled(
                            viewModel.selectedPlace == nil &&
                            viewModel.time == nil &&
                            viewModel.dateRange == nil
                        )
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
                } else if viewModel.showTimePicker {
                    ModalTimePicker(selectedTime: $viewModel.time, showTimePicker: $viewModel.showTimePicker)
                }
            }
            .loadingOverlay(isLoading: $viewModel.isLoading)
        }
        .navigationBarBackButtonHidden()
    }
}

#Preview {
    MakeReservationView(
        car: Car.companion.empty(),
        dates: nil
    )
}
