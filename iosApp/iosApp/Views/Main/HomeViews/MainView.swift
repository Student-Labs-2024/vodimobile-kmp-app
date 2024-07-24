//
//  MainView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 11.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import Combine

struct ViewOffsetKey: PreferenceKey {
    typealias Value = CGFloat
    static var defaultValue = CGFloat.zero
    static func reduce(value: inout Value, nextValue: () -> Value) {
        value += nextValue()
    }
}

struct MainView: View {
    @Environment(\.calendar) var calendar
    @Environment(\.timeZone) var timeZone
    @State private var isExpanded = true
    @State private var showDatePicker = false
    @State private var lastScrollOffset: CGFloat = 0
    @State private var currentScrollOffset: CGFloat = 0
    @State private var notifBadgeCount: Int = 0
    @State private var startDate: Date = Date()
    @State private var endDate: Date = Date()
    @State private var dateRange: ClosedRange<Date>?
    @State private var scrollViewProxy: ScrollViewProxy?
    let detector: CurrentValueSubject<CGFloat, Never>
    let publisher: AnyPublisher<CGFloat, Never>
    
    init() {
        let detector = CurrentValueSubject<CGFloat, Never>(0)
        self.publisher = detector
            .debounce(for: .seconds(0.2), scheduler: DispatchQueue.main)
            .dropFirst()
            .eraseToAnyPublisher()
        self.detector = detector
    }
    
    var bounds: PartialRangeFrom<Date> {
        let start = calendar.date(
            from: DateComponents(
                timeZone: timeZone,
                year: 2022,
                month: 6,
                day: 20)
        )!
        
        return start...
    }
    
    var body: some View {
        ZStack(alignment: .top) {
            ScrollViewReader { proxy in
                ScrollView {
                    VStack {
                        ForEach(0..<20) { index in
                            Text("Car \(index + 1)")
                                .frame(maxWidth: .infinity)
                                .padding()
                                .background(Color.white)
                                .cornerRadius(10)
                                .shadow(radius: 5)
                                .padding(.horizontal)
                                .padding(.vertical, 5)
                        }
                    }
                    .padding(.top, 150)
                    .frame(maxWidth: .infinity)
                    .background(GeometryReader {
                        Color.clear.preference(key: ViewOffsetKey.self,
                                               value: -$0.frame(in: .named("scroll")).origin.y)
                    })
                    .onPreferenceChange(ViewOffsetKey.self) { detector.send($0) }
                }
                .coordinateSpace(name: "scroll")
                .onReceive(publisher) {
                    print("Stopped on: \($0)")
                    isExpanded = $0 > 0 ? false : true
                }
            }
            
        // Expandable Toolbar
        VStack(spacing: 0) {
            HStack {
                Spacer()
                
                Button(action: {
                    // Action for bell button
                }) {
                    ZStack(alignment: .topTrailing) {
                        Image(systemName: "bell")
                            .resizable()
                            .frame(width: 24, height: 24)
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
            .padding(.top, 55)
            .background(Color(R.color.blueColor))
            
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
                    }
                    .padding(.vertical, 28)
                    .padding(.horizontal, 16)
                    .background(RoundedRectangle(cornerRadius: 24).fill(.white))
                }
                .padding(.vertical, 16)
                .padding(.horizontal, 16)
                .offset(y: isExpanded ? 0 : -UIScreen.main.bounds.height * 0.3)
                .animation(.spring(), value: isExpanded)
            }
            
            if !isExpanded {
                Image(systemName: "minus")
                    .resizable()
                    .frame(width: 100, height: 5)
                    .foregroundColor(.white)
                    .padding(.vertical, 16)
                    .onTapGesture {
                        withAnimation {
                            isExpanded.toggle()
                        }
                    }
            }
        }
        .background(Color(R.color.blueColor))
        .cornerRadius(24)
        .animation(.spring(), value: isExpanded)
        
        // Date Picker Modal
        if showDatePicker {
            Color.black.opacity(0.4)
                .edgesIgnoringSafeArea(.all)
                .onTapGesture {
                    showDatePicker = false
                }
            VStack {
                DatePicker(
                    "Select Date Range",
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
                    Button("Clear", role: .destructive) {
                        dateRange = nil
                        showDatePicker = false
                    }
                    .foregroundColor(.red)
                    .font(.system(size: 14))
                    Spacer()
                    Button("Save") {
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
            .position(x: UIScreen.main.bounds.width / 2, y: UIScreen.main.bounds.height / 2)
            .transition(.scale)
        }
    }
        .ignoresSafeArea(.container, edges: .top)
}

private func formatDateRange() -> String {
    guard let dateRange = dateRange else {
        return "When?"
    }
    
    let formatter = DateFormatter()
    formatter.dateFormat = "dd MMMM yyyy"
    
    let startDate = formatter.string(from: dateRange.lowerBound)
    let endDate = formatter.string(from: dateRange.upperBound)
    
    if startDate == endDate {
        return startDate
    } else if startDate < endDate {
        return "\(startDate) - \(endDate)"
    } else if startDate > endDate {
        return "\(endDate) - \(startDate)"
    }
    return ""
}
}

struct ScrollOffsetPreferenceKey: PreferenceKey {
    typealias Value = CGFloat
    static var defaultValue: CGFloat = 0
    
    static func reduce(value: inout CGFloat, nextValue: () -> CGFloat) {
        value = nextValue()
    }
}

#Preview {
    MainView()
}

