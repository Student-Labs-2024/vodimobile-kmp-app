//
//  ModalDatePickerView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 24.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ModalDatePickerView: View {
    @Environment(\.calendar) var calendar
    @Environment(\.timeZone) var timeZone
    @Binding var showDatePicker: Bool
    @Binding var dateRange: ClosedRange<Date>?
    private let currentDate = Date()

    var bounds: PartialRangeFrom<Date> {
        currentDate...
    }

    var body: some View {
        Color.black.opacity(0.4)
            .edgesIgnoringSafeArea(.all)
            .onTapGesture {
                showDatePicker = false
            }
        VStack {
            DatePicker(
                "",
                selection: Binding(
                    get: {
                        dateRange?.lowerBound ?? Date()
                    },
                    set: { newStart in
                        if let end = dateRange?.upperBound {
                            if newStart < end {
                                dateRange = newStart...end
                            } else if newStart > end {
                                dateRange = end...newStart
                            } else if newStart == end {
                                dateRange = newStart...end
                            }
                        } else {
                            dateRange = newStart...newStart
                        }
                    }
                ),
                in: bounds,
                displayedComponents: .date
            )
            .datePickerStyle(GraphicalDatePickerStyle())
            .background(Color.white)
            .cornerRadius(13)
            .padding(.horizontal, 6)

            HStack {
                Button(R.string.localizable.clearButton()) {
                    dateRange = nil
                    showDatePicker = false
                }
                .foregroundColor(.red)
                .font(.system(size: 14))
                Spacer()
                Button(R.string.localizable.saveButton()) {
                    showDatePicker = false
                }
                .foregroundColor(.blue)
                .font(.system(size: 14))
            }
            .padding(.vertical, 20)
            .padding(.horizontal, 24)
            .overlay {
                Rectangle()
                    .fill(Color.gray)
                    .frame(height: 0.5, alignment: .top)
                    .offset(y: -28)
                    .opacity(0.5)
            }
        }
        .background(.white)
        .cornerRadius(13)
        .frame(maxWidth: .infinity)
        .padding(.horizontal, 16)
        .position(x: screenWidth / 2, y: screenHeight / 2)
        .transition(.scale)
    }
}
