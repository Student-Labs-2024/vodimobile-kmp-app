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
    @Binding var time: Date
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
        time: Binding<Date>? = nil,
        selectedPlace: Binding<Place?>? = nil,
        placesDataSource: Binding<[String]>? = nil
    ) {
        self.fieldType = fieldType
        self._inputErrorType = inputErrorType
        self._placesDataSource = placesDataSource ?? Binding.constant([])
        self._dateRange = dateRange ?? Binding.constant(nil)
        self._showDatePicker = showDatePicker ?? Binding.constant(false)
        self._showPlacePicker = showPlacePicker ?? Binding.constant(false)
        self._time = time ?? Binding.constant(Date())
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
                    showPlacePicker: $showPlacePicker,
                    placesDataSource: placesDataSource,
                    rightImage: Image.chevronDown
                )
            case .timePicker:
                Button(action: {
                    showPlacePicker = true
                }) {
                    HStack(spacing: 10) {
                        Text(R.string.localizable.methodOfObtaining)
                            .foregroundColor(selectedPlace == nil ? .gray : .black)
                        Spacer()
                        Image.clock
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                            .frame(width: 20 , height: 20)
                            .foregroundColor(.gray)
                    }
                    .frame(alignment: .leading)
                    .padding(.all, 16)
                    .background(Color(R.color.blueBoxColor))
                    .overlay {
                        RoundedRectangle(cornerRadius: 10)
                            .stroke(Color.gray, lineWidth: 1)
                    }
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
