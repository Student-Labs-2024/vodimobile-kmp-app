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
    
    private let fieldType: ButtonLikeTextFieldType
    private let placeholder: String = ""
    
    init(
        fieldType: ButtonLikeTextFieldType
    ) {
        self.fieldType = fieldType
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
                PlacePickerField(showPlacePicker: $showPlacePicker)
            case .timePicker:
                DatePicker("", selection: $time, displayedComponents: .hourAndMinute)
                
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
