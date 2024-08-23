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
    @Binding var time: Date?
    @Binding var showTimePicker: Bool
    @Binding var selectedPlace: PlaceShort?
    @Binding var placesDataSource: [PlaceShort]

    private let fieldType: ButtonLikeTextFieldType
    private let placeholder: String = ""

    init(
        fieldType: ButtonLikeTextFieldType,
        showDatePicker: Binding<Bool>? = nil,
        inputErrorType: Binding<InputErrorType?> = Binding.constant(nil),
        dateRange: Binding<ClosedRange<Date>?>? = nil,
        time: Binding<Date?>? = nil,
        showTimePicker: Binding<Bool>? = nil,
        selectedPlace: Binding<PlaceShort?>? = nil,
        placesDataSource: Binding<[PlaceShort]>? = nil
    ) {
        self.fieldType = fieldType
        self._inputErrorType = inputErrorType
        self._placesDataSource = placesDataSource ?? Binding.constant([])
        self._dateRange = dateRange ?? Binding.constant(nil)
        self._showDatePicker = showDatePicker ?? Binding.constant(false)
        self._time = time ?? Binding.constant(Date())
        self._showTimePicker = showTimePicker ?? Binding.constant(false)
        self._selectedPlace = selectedPlace ?? Binding.constant(nil)
    }

    var body: some View {
        VStack(alignment: .leading) {
            Text(fieldType.localizedStr).font(.header4).foregroundStyle(Color.black)

            switch fieldType {
            case .datePicker:
                DataPickerField(
                    dateRange: $dateRange,
                    showDatePicker: $showDatePicker,
                    rightImage: Image(R.image.calendar)
                )
            case .placePicker:
                PlacePickerField(
                    selectedPlace: $selectedPlace,
                    placesDataSource: placesDataSource,
                    icon: Image.chevronDown
                )
            case .timePicker:
                TimePickerField(selectedTime: $time, showTimePicker: $showTimePicker)

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
