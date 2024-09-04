//
//  DatePickerModalView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 28.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct DatePickerModifier: ViewModifier {
    @Binding var showDatePicker: Bool
    @Binding var dateRange: ClosedRange<Date>?

    func body(content: Content) -> some View {
        ZStack {
            content
            if showDatePicker {
                ModalDatePickerView(showDatePicker: $showDatePicker, dateRange: $dateRange)
            }
        }
    }
}

extension View {
    func datePickerModalOverlay(showDatePicker: Binding<Bool>, dateRange: Binding<ClosedRange<Date>?>) -> some View {
        self.modifier(DatePickerModifier(showDatePicker: showDatePicker, dateRange: dateRange))
    }
}
