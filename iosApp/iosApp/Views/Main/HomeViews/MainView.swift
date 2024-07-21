//
//  MainView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 11.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct MainView: View {
    @State private var selectedDate = Date()
    @State private var isExpanded = true
    @State private var showDatePicker = false
    @State private var lastScrollOffset: CGFloat = 0
    @State private var currentScrollOffset: CGFloat = 0
    
    var body: some View {
        ZStack(alignment: .top) {
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
                .background(
                    GeometryReader { geo in
                        Color.clear
                            .preference(key: ScrollOffsetPreferenceKey.self, value: geo.frame(in: .global).minY)
                    }
                )
            }
            .onPreferenceChange(ScrollOffsetPreferenceKey.self) { value in
                currentScrollOffset = value
                withAnimation {
                    if currentScrollOffset < lastScrollOffset && abs(currentScrollOffset - lastScrollOffset) > 20 {
                        isExpanded = false
                    } else if currentScrollOffset > lastScrollOffset && abs(currentScrollOffset - lastScrollOffset) > 50 {
                        isExpanded = true
                    }
                }
                lastScrollOffset = currentScrollOffset
            }
            
            // Expandable Toolbar
            VStack(spacing: 0) {
                HStack {
                    Spacer()
                    
                    Button(action: {
                        // Action for bell button
                    }) {
                        Image(systemName: "bell")
                            .resizable()
                            .frame(width: 24, height: 24)
                            .foregroundColor(.white)
                    }
                }
                .padding(.horizontal)
                .padding(.top, 55)
                .background(Color(R.color.blueDarkColor))
                
                if isExpanded {
                    VStack {
                        VStack(spacing: 20) {
                            Text("Выберите даты аренды").font(.header3)
                            
                            TextField("Select Date", value: $selectedDate, formatter: dateFormatter, onEditingChanged: { isEditing in
                                if isEditing {
                                    showDatePicker = true
                                }
                            })
                            .textFieldStyle(BorderedDateTextFieldStyle())
                            .overlay {
                                
                                HStack {
                                    Image(R.image.calendar)
                                        .resizable()
                                        .aspectRatio(contentMode: .fit)
                                        .frame(width: 30 , height: 30)
                                        .foregroundColor(Color(R.color.grayDarkColor))
                                    
                                    Spacer()
                                    
                                    Button(action: { showDatePicker = true }, label: {
                                        Image.rightArrowCircleFill
                                            .resizable()
                                            .aspectRatio(contentMode: .fit)
                                            .frame(width: 30 , height: 30)
                                            .foregroundColor(Color(R.color.blueDarkColor))
                                    })
                                }
                                .padding(.horizontal, 14)
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
            .background(Color(R.color.blueDarkColor))
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
                    DatePicker("Select Date", selection: $selectedDate, displayedComponents: .date)
                        .datePickerStyle(GraphicalDatePickerStyle())
                        .background(Color.white)
                        .cornerRadius(12)
                        .padding()
                    
                    Button("Done") {
                        showDatePicker = false
                    }
                    .padding()
                    .background(Color.blue)
                    .foregroundColor(.white)
                    .cornerRadius(12)
                }
                .padding()
                .background(Color(R.color.grayLightColor))
                .cornerRadius(12)
                .transition(.scale)
            }
        }
        .ignoresSafeArea(.container, edges: .top)
    }
    
    private var dateFormatter: DateFormatter {
        let formatter = DateFormatter()
        formatter.dateStyle = .medium
        return formatter
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
