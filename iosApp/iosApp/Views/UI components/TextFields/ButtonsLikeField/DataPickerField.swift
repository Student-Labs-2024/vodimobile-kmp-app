//
//  DataPickerField.swift
//  iosApp
//
//  Created by Sergey Ivanov on 08.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct DataPickerField: View {
    @Environment(\.calendar) var calendar
    @Binding var dateRange: ClosedRange<Date>?
    @Binding var showDatePicker: Bool
    private let backgroundColor: SwiftUI.Color
    private let rightImage: Image
    
    init(
        dateRange: Binding<ClosedRange<Date>?>,
        showDatePicker: Binding<Bool>,
        rightImage: Image,
        backgroundColor: SwiftUI.Color = Color(R.color.blueBoxColor)
    ) {
        self._dateRange = dateRange
        self._showDatePicker = showDatePicker
        self.rightImage = rightImage
        self.backgroundColor = backgroundColor
    }
    
    var body: some View {
        Button(action: {
            showDatePicker = true
        }) {
            HStack(spacing: 10) {
                Text(formatDateRange())
                    .foregroundColor(dateRange == nil ? .gray : .black)
                Spacer()
                rightImage
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: 20 , height: 20)
                    .foregroundColor(.gray)
            }
            .frame(alignment: .leading)
            .padding(.all, 16)
            .background(backgroundColor)
            .overlay {
                RoundedRectangle(cornerRadius: 10)
                    .stroke(Color.gray, lineWidth: 1)
            }
            .frame(maxHeight: 55)
        }
    }
    
    private func formatDateRange() -> String {
        guard let dateRange = dateRange else {
            return R.string.localizable.dateTextFieldPlaceholder()
        }
        
        let formatter = DateFormatter()
        formatter.dateFormat = "dd MMMM yyyy"
        
        let startDate = formatter.string(from: dateRange.lowerBound)
        let endDate = formatter.string(from: dateRange.upperBound)
        
        if startDate == endDate {
            return startDate
        } else if calendar.compare(dateRange.lowerBound, to: dateRange.upperBound, toGranularity: .day) == .orderedAscending {
            return "\(startDate) - \(endDate)"
        } else {
            return "\(endDate) - \(startDate)"
        }
    }
}
