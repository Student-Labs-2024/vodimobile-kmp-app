//
//  TimePickerField.swift
//  iosApp
//
//  Created by Sergey Ivanov on 09.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct TimePickerField: View {
    @Binding var selectedTime: Date?
    @Binding var showTimePicker: Bool
    
    var body: some View {
        VStack {
            Button(action: {
                showTimePicker.toggle()
            }) {
                HStack {
                    if let time = selectedTime {
                        Text(timeFormatter.string(from: time))
                            .foregroundColor(Color(R.color.blueColor))
                            .font(.paragraph4)
                    } else {
                        Text(R.string.localizable.rentalTimePlaceholder)
                            .foregroundColor(Color(R.color.grayTextColor))
                            .font(.paragraph4)
                    }
                    
                    Spacer()
                    
                    Image.clock
                        .foregroundColor(Color(R.color.grayDarkColor))
                }
                .padding()
                .background(
                    RoundedRectangle(cornerRadius: 8)
                        .stroke(Color.gray, lineWidth: 1)
                        .background(Color(R.color.blueBoxColor))
                )
            }
        }
    }
    
    private var timeFormatter: DateFormatter {
        let formatter = DateFormatter()
        formatter.timeStyle = .short
        return formatter
    }
}
