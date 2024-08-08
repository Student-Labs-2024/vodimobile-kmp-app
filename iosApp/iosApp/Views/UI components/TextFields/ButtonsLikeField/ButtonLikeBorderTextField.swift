//
//  ButtonLikeBorderTextField.swift
//  iosApp
//
//  Created by Sergey Ivanov on 07.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct ButtonLikeBorderedTextField: View {
    @State private var errorMessage: String = ""
    @State var fieldContent: String = ""
    @Binding var inputErrorType: InputErrorType?
    @Binding var dateRange: ClosedRange<Date>?
    @Binding var showDatePicker: Bool
    @Binding var showPlacePicker: Bool
    @Binding var time: Date?
    @Binding var selectedPlace: Place?
    @Binding var placesDataSource: [String]
    
    private let fieldType: ButtonLikeTextFieldType
    private let placeholder: String = ""
    
    init(
        fieldType: ButtonLikeTextFieldType,
        showDatePicker: Binding<Bool>? = nil,
        showPlacePicker: Binding<Bool>? = nil,
        inputErrorType: Binding<InputErrorType?> = Binding.constant(nil),
        dateRange: Binding<ClosedRange<Date>?>? = nil,
        time: Binding<Date?>? = nil,
        selectedPlace: Binding<Place?>? = nil,
        placesDataSource: Binding<[String]>? = nil
    ) {
        self.fieldType = fieldType
        self._inputErrorType = inputErrorType
        if let placesDataSource = placesDataSource {
            self._placesDataSource = placesDataSource
        } else {
            self._placesDataSource = Binding.constant([])
        }
        if let dateRange = dateRange {
            self._dateRange = dateRange
        } else {
            self._dateRange = Binding.constant(nil)
        }
        if let showDatePicker = showDatePicker {
            self._showDatePicker = showDatePicker
        } else {
            self._showDatePicker = Binding.constant(false)
        }
        if let showPlacePicker = showPlacePicker {
            self._showPlacePicker = showPlacePicker
        } else {
            self._showPlacePicker = Binding.constant(false)
        }
        if let time = time {
            self._time = time
        } else {
            self._time = Binding.constant(nil)
        }
        if let selectedPlace = selectedPlace {
            self._selectedPlace = selectedPlace
        } else {
            self._selectedPlace = Binding.constant(nil)
        }
    }
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(fieldType.localizedStr).font(.header4).foregroundStyle(Color.black)
            
            switch fieldType {
            case .datePicker:
                if let unwrappedDateRange = dateRange {
                    DataPickerField(
                        dateRange: Binding(
                            get: { unwrappedDateRange },
                            set: { newValue in
                                dateRange = newValue
                            }),
                        showDatePicker: $showDatePicker,
                        rightImage: Image(R.image.calendar)
                    )
                }
            case .placePicker:
                if let unwrappedSelectedPlace = selectedPlace
                {
                    PlacePickerField(
                        selectedPlace: Binding(
                            get: { unwrappedSelectedPlace },
                            set: { newValue in
                                selectedPlace = newValue
                            }),
                        showPlacePicker: $showPlacePicker, 
                        placesDataSource: placesDataSource
                    )
                }
            case .timePicker:
                if let unwrappedTime = time {
                    DatePicker(
                        "",
                        selection: Binding(
                            get: { unwrappedTime },
                            set: { newValue in
                                time = newValue
                            }
                        ),
                        displayedComponents: .hourAndMinute
                    )
                }
                
                if inputErrorType != nil {
                    Text(errorMessage)
                        .font(.paragraph6)
                        .foregroundStyle(Color(R.color.redColor))
                        .padding(.leading, 10)
                }
            }
        }
    }
    
    private func handleErrorTypeChanging(errorMsg: inout String) {
        if let inputErrorType = inputErrorType {
            switch inputErrorType {
            case .selectDayTime:
                errorMsg = InputErrorType.selectDayTime.errorString
            case .selectNightTime:
                errorMsg = InputErrorType.selectNightTime.errorString
            default:
                errorMsg = ""
            }
        }
    }
}
