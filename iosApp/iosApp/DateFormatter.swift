//
//  DateFormatter.swift
//  iosApp
//
//  Created by Sergey Ivanov on 18.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

final class CustomDateFormatter {
    static let shared = CustomDateFormatter()

    func formatDates(startDateInMillis: Int64, endDateInMillis: Int64) -> String {
        let dateFormatter = DateFormatter()
        dateFormatter.locale = Locale(identifier: "ru_RU")

        let startDate = Date(timeIntervalSince1970: TimeInterval(startDateInMillis) / 1000)
        let endDate = Date(timeIntervalSince1970: TimeInterval(endDateInMillis) / 1000)

        let calendar = Calendar.current
        let startMonth = calendar.component(.month, from: startDate)
        let endMonth = calendar.component(.month, from: endDate)
        let startYear = calendar.component(.year, from: startDate)
        let endYear = calendar.component(.year, from: endDate)

        if startDate == endDate {
            dateFormatter.dateFormat = "d"
            let startDayString = dateFormatter.string(from: startDate)
            dateFormatter.dateFormat = "MMMM yyyy"
            let monthYearString = dateFormatter.string(from: startDate)

            return "\(startDayString) \(monthYearString)"
        } else if startMonth == endMonth && startYear == endYear {
            dateFormatter.dateFormat = "d"
            let startDayString = dateFormatter.string(from: startDate)
            let endDayString = dateFormatter.string(from: endDate)
            dateFormatter.dateFormat = "MMMM yyyy"
            let monthYearString = dateFormatter.string(from: endDate)

            return "\(startDayString) - \(endDayString) \(monthYearString)"
        } else {
            dateFormatter.dateFormat = "d MMMM"
            let startDateString = dateFormatter.string(from: startDate)
            let endDateString = dateFormatter.string(from: endDate)
            dateFormatter.dateFormat = "yyyy"
            let yearString = dateFormatter.string(from: endDate)

            return "\(startDateString) - \(endDateString) \(yearString)"
        }
    }

    func formatTimes(startTimeInMillis: Int64, endTimeInMillis: Int64) -> String {
        let timeFormatter = DateFormatter()
        timeFormatter.locale = Locale(identifier: "ru_RU")
        timeFormatter.dateFormat = "HH:mm"

        let startTime = Date(timeIntervalSince1970: TimeInterval(startTimeInMillis) / 1000)
        let endTime = Date(timeIntervalSince1970: TimeInterval(endTimeInMillis) / 1000)

        let startTimeString = timeFormatter.string(from: startTime)
        let endTimeString = timeFormatter.string(from: endTime)

        if startTime == endTime {
            return startTimeString
        } else {
            return "\(min(startTimeString, endTimeString)) - \(max(startTimeString, endTimeString))"
        }
    }

    func transformDateToString(date: Date?, time: Date?) -> String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy-MM-dd HH:mm"
        guard let date = date else { return dateFormatter.string(from: Date.now) }
        let calendar = Calendar.current
        let dateComponents = calendar.dateComponents([.year, .month, .day], from: date)
        let timeComponents = calendar.dateComponents([.hour, .minute], from: time ?? Date.now)

        var combinedComponents = DateComponents()
        combinedComponents.year = dateComponents.year
        combinedComponents.month = dateComponents.month
        combinedComponents.day = dateComponents.day
        combinedComponents.hour = timeComponents.hour
        combinedComponents.minute = timeComponents.minute

        if let combinedDate = calendar.date(from: combinedComponents) {
            return dateFormatter.string(from: combinedDate)
        }

        return dateFormatter.string(from: Date.now)
    }
}
