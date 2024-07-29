//
//  ExpantableToolbar.swift
//  iosApp
//
//  Created by Sergey Ivanov on 24.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ExpandableToolbar: View {
    @Environment(\.calendar) var calendar
    @Binding var isExpanded: Bool
    @Binding var dateRange: ClosedRange<Date>?
    @Binding var showDatePicker: Bool
    @Binding var notifBadgeCount: Int
    @Binding var headerHeight: CGFloat
    @Binding var dragOffset: CGSize
    
    var body: some View {
        VStack(spacing: 0) {
            GeometryReader { geometry in
                VStack(spacing: 0) {
                    HStack {
                        Spacer()
                        
                        Button(action: {
                            // TODO: - Action for bell button
                        }) {
                            ZStack(alignment: .topTrailing) {
                                Image.bell
                                    .resizable()
                                    .frame(width: 20, height: 24)
                                    .foregroundColor(.white)
                                
                                if notifBadgeCount != 0 {
                                    GeometryReader { geometry in
                                        Text("\(notifBadgeCount)")
                                            .foregroundColor(.white)
                                            .font(.caption2)
                                            .padding(5)
                                            .background(Circle().foregroundColor(Color.red))
                                            .frame(width: geometry.size.width, height: geometry.size.height, alignment: .topTrailing)
                                            .offset(x: 8, y: -8)
                                            .minimumScaleFactor(0.5)
                                            .lineLimit(1)
                                    }
                                    .frame(width: 24, height: 24, alignment: .topTrailing)
                                }
                            }
                        }
                    }
                    .padding(.horizontal)
                    .padding(.top, 65)
                    .background(Color(R.color.blueDarkColor))
                    
                    if isExpanded {
                        VStack {
                            VStack(spacing: 20) {
                                Text(R.string.localizable.dateTextFieldTitle)
                                    .font(.header3)
                                
                                Button(action: {
                                    showDatePicker = true
                                }) {
                                    HStack(spacing: 10) {
                                        Image(R.image.calendar)
                                            .resizable()
                                            .aspectRatio(contentMode: .fit)
                                            .frame(width: 30 , height: 30)
                                            .foregroundColor(.gray)
                                        
                                        Text(formatDateRange())
                                            .foregroundColor(dateRange == nil ? .gray : .black)
                                        Spacer()
                                    }
                                    .frame(alignment: .leading)
                                    .padding(.all, 16)
                                    .background(
                                        RoundedRectangle(cornerRadius: 10)
                                            .stroke(Color.gray, lineWidth: 1)
                                    )
                                }
                                
                                Button(R.string.localizable.findAutoButton()) {
                                    // TODO: - Make a navigation link into view
                                }
                                .buttonStyle(FilledBtnStyle())
                            }
                            .padding(.vertical, 20)
                            .padding(.horizontal, 16)
                            .background(RoundedRectangle(cornerRadius: 24).fill(.white))
                        }
                        .padding(.vertical, 16)
                        .padding(.horizontal, 16)
                        .offset(y: isExpanded ? 0 : -UIScreen.main.bounds.height * 0.3)
                        .animation(.spring(), value: isExpanded)
                    }
                    
                    if !isExpanded {
                        Text("").padding(.vertical, 5)
                    }
                }
                .background(Color(R.color.blueDarkColor))
                .cornerRadius(24)
                .animation(.spring(), value: isExpanded)
                .onAppear {
                    headerHeight = geometry.size.height
                }
            }
            .frame(height: isExpanded ? 200 : 100)
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
