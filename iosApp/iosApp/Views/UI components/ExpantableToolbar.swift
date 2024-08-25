//
//  ExpantableToolbar.swift
//  iosApp
//
//  Created by Sergey Ivanov on 24.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct ExpandableToolbar: View {
    @Environment(\.calendar) var calendar
    @Binding var selectedAuto: Car
    @Binding var showModalReservation: Bool
    @Binding var showSignSuggestModal: Bool
    @Binding var isExpanded: Bool
    @Binding var dateRange: ClosedRange<Date>?
    @Binding var showDatePicker: Bool
    @Binding var headerHeight: CGFloat
    @Binding var dragOffset: CGSize

    var body: some View {
        VStack {
            GeometryReader { geometry in
                VStack {
                    HStack {
                        Spacer()

                        Button(action: {
                            // TODO: - Action for bell button
                        }) {
                            Image.bell
                                .resizable()
                                .frame(width: 20, height: 24)
                                .foregroundColor(.white)
                        }
                    }
                    .padding(.horizontal, 20)
                    .padding(.top, 65)
                    .background(Color(R.color.blueDark))
                    .padding(.bottom, !isExpanded ? 25 : 0)

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
                                            .frame(width: 30, height: 30)
                                            .foregroundColor(Color(R.color.grayDark))
                                        Text(CustomDateFormatter.formatDateRange())
                                            .foregroundColor(
                                                dateRange == nil
                                                ? Color(R.color.grayDark)
                                                : Color(R.color.background)
                                            )
                                        Spacer()
                                    }
                                    .frame(alignment: .leading)
                                    .padding(.all, 16)
                                    .background(
                                        RoundedRectangle(cornerRadius: 10)
                                            .fill(Color(R.color.containerItem))
                                    )
                                    .overlay {
                                        RoundedRectangle(cornerRadius: 10)
                                            .stroke(Color(R.color.grayDark), lineWidth: 1)
                                    }
                                }

                                NavigationLink(R.string.localizable.findAutoButton()) {
                                    AutoListView(
                                        selectedAuto: $selectedAuto,
                                        showModalReservation: $showModalReservation,
                                        showSignSuggestModal: $showSignSuggestModal,
                                        dateRange: dateRange
                                    )
                                }
                                .buttonStyle(FilledBtnStyle())
                            }
                            .padding(.vertical, 20)
                            .padding(.horizontal, 16)
                            .background(
                                RoundedRectangle(cornerRadius: 24)
                                    .fill(Color(R.color.container))
                            )
                        }
                        .padding(.vertical, 16)
                        .padding(.horizontal, 16)
                        .offset(y: isExpanded ? 0 : -UIScreen.main.bounds.height * 0.3)
                        .animation(.spring(), value: isExpanded)
                    }
                }
                .background(Color(R.color.blueDark))
                .cornerRadius(24)
                .animation(.spring(), value: isExpanded)
                .onAppear {
                    headerHeight = geometry.size.height
                }
            }
            .frame(height: isExpanded ? 200 : 100)
        }
    }
}
