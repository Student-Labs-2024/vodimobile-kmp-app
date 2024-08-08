//
//  DataPickerField.swift
//  iosApp
//
//  Created by Sergey Ivanov on 08.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct PlacePickerField: View {
    @Binding var selectedPlace: Place?
    @Binding var showPlacePicker: Bool
    private let placesDataSource: [String]
    private let backgroundColor: SwiftUI.Color
    private let rightImage: Image
    
    init(
        selectedPlace: Binding<Place?>,
        showPlacePicker: Binding<Bool>,
        placesDataSource: [String],
        rightImage: Image = Image.clock,
        backgroundColor: SwiftUI.Color = Color(R.color.blueBoxColor)
    ) {
        self._selectedPlace = selectedPlace
        self._showPlacePicker = showPlacePicker
        self.placesDataSource = placesDataSource
        self.rightImage = rightImage
        self.backgroundColor = backgroundColor
    }
    
    var body: some View {
        Button(action: {
            showPlacePicker = true
        }) {
            HStack(spacing: 10) {
                Text(R.string.localizable.methodOfObtaining)
                    .foregroundColor(selectedPlace == nil ? .gray : .black)
                Spacer()
                rightImage
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: 30 , height: 30)
                    .foregroundColor(.gray)
            }
            .frame(alignment: .leading)
            .padding(.all, 16)
            .background(backgroundColor)
            .overlay {
                RoundedRectangle(cornerRadius: 10)
                    .stroke(Color.gray, lineWidth: 1)
            }
        }
    }
}
