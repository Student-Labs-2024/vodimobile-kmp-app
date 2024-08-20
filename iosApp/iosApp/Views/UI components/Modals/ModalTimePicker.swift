//
//  ModalTimePicker.swift
//  iosApp
//
//  Created by Sergey Ivanov on 09.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ModalTimePicker: View {
    @Binding var selectedTime: Date?
    @Binding var showTimePicker: Bool

    var body: some View {
        Color.black.opacity(0.4)
            .edgesIgnoringSafeArea(.all)
            .onTapGesture {
                showTimePicker = false
            }
        VStack {
            DatePicker(
                "",
                selection: Binding(
                    get: { selectedTime ?? Date() },
                    set: { selectedTime = $0 }
                ),
                displayedComponents: .hourAndMinute
            )
            .datePickerStyle(WheelDatePickerStyle())
            .labelsHidden()
            .background(
                RoundedRectangle(cornerRadius: 10)
                    .fill(Color.white)
            )
        }
        .background(.white)
        .cornerRadius(13)
        .position(x: screenWidth / 2, y: screenHeight / 3)
        .transition(.scale)
    }
}
