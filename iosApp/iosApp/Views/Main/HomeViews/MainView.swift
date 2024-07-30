//
//  MainView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 11.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct MainView: View {
    @State private var isExpanded = true
    @State private var showDatePicker = false
    @State private var dateRange: ClosedRange<Date>?
    @State private var scrollOffset: CGPoint = .zero
    @State private var headerHeight: CGFloat = 0
    @State private var dragOffset: CGSize = .zero
    private let listOfCar = KoinHelper().getPopularCars()
    
    var body: some View {
        ZStack(alignment: .top) {
            ScrollViewWithOffset(onScroll: handleScroll) {
                LazyVStack {
                    ForEach(0..<listOfCar.count , id: \.self) { index in
                        VStack {
                            Text("\(listOfCar[index].model.desc().localized())")
                                .frame(maxWidth: .infinity)
                                .padding()
                                .background(Color.white)
                                .cornerRadius(10)
                                .shadow(radius: 5)
                                .padding(.horizontal)
                            .padding(.vertical, 5)
                        }
                    }
                }
                .padding(.top, headerHeight * 1.7)
            }
            
            // Expandable Toolbar
            ExpandableToolbar(
                isExpanded: $isExpanded,
                dateRange: $dateRange,
                showDatePicker: $showDatePicker,
                headerHeight: $headerHeight,
                dragOffset: $dragOffset
            )
            
            // Date Picker Modal
            if showDatePicker {
                ModalDatePickerView(
                    showDatePicker: $showDatePicker,
                    dateRange: $dateRange
                )
            }
        }
        .ignoresSafeArea(.container, edges: .top)
    }
    
    func handleScroll(_ offset: CGPoint) {
        self.scrollOffset = offset
        isExpanded = offset.y < 0 ? false : true
    }
}

#Preview {
    MainView()
}
