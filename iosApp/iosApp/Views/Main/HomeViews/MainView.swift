//
//  MainView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 11.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct MainView: View {
    @State private var isExpanded = true
    @State private var showDatePicker = false
    @State private var notifBadgeCount: Int = 0
    @State private var dateRange: ClosedRange<Date>?
    @State private var scrollOffset: CGPoint = .zero
    @State private var headerHeight: CGFloat = 0
    @State private var dragOffset: CGSize = .zero
    @State private var showModalCard: Bool = false
    @State private var selectedAuto: AutoCard = AutoCard.empty
    @ObservedObject private var viewModel: MainViewModel = MainViewModel()
    
    var body: some View {
        NavigationView {
            ZStack(alignment: .top) {
                ScrollViewWithOffset(onScroll: handleScroll) {
                    LazyVStack(spacing: 20) {
                        HStack {
                            Text(R.string.localizable.popularAuto).font(.header3)
                            Spacer()
                            NavigationLink(R.string.localizable.allAutoButton()) {
                                AutoListView()
                            }
                            .font(.buttonTabbar)
                            .foregroundStyle(Color(R.color.blueColor))
                        }
                        .padding(.bottom, 10)
                        
                        ForEach(AutoCard.autoCardsList.indices, id: \.self) { index in
                            AutoCardView(
                                autoCard: AutoCard.autoCardsList[index],
                                showModal: $showModalCard,
                                selectedAuto: $selectedAuto
                            )
                        }
                    }
                    .padding(.top, headerHeight * 1.75)
                    .padding(.horizontal, 24)
                }
                
                // Expandable Toolbar
                ExpandableToolbar(
                    isExpanded: $isExpanded,
                    dateRange: $dateRange,
                    showDatePicker: $showDatePicker,
                    notifBadgeCount: $notifBadgeCount,
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
            .background(Color(R.color.grayLightColor))
            .sheet(isPresented: $showModalCard) {
                ModalAutoView(autoData: $selectedAuto, showModalView: $showModalCard)
            }
        }
    }
    
    func handleScroll(_ offset: CGPoint) {
        self.scrollOffset = offset
        isExpanded = offset.y < 0 ? false : true
    }
}

#Preview {
    MainView()
}
