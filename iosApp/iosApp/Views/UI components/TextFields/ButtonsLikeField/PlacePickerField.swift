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
    @Binding var selectedPlace: PlaceShort?
    private let placesDataSource: [PlaceShort]
    private let backgroundColor: SwiftUI.Color
    private let rightImage: Image
    
    init(
        selectedPlace: Binding<PlaceShort?>,
        placesDataSource: [PlaceShort],
        rightImage: Image = Image.clock,
        backgroundColor: SwiftUI.Color = Color(R.color.blueBoxColor)
    ) {
        self._selectedPlace = selectedPlace
        self.placesDataSource = placesDataSource
        self.rightImage = rightImage
        self.backgroundColor = backgroundColor
    }
    
    var body: some View {
        Menu {
            ForEach(placesDataSource) { place in
                Button(action: {
                    selectedPlace = place
                }) {
                    Text(place.nameWithCost)
                }
            }
        } label: {
            HStack {
                Text(selectedPlace?.nameWithCost ?? R.string.localizable.methodOfObtaining())
                    .foregroundColor(selectedPlace != nil ? Color(R.color.blueColor) : Color(R.color.grayDarkColor))
                    .font(.paragraph4)
                
                Spacer()
                
                Image.chevronDown
                    .foregroundColor(Color(R.color.grayDarkColor))
            }
            .padding(16)
            .background(
                RoundedRectangle(cornerRadius: 10).fill(Color(R.color.blueBoxColor))
            )
            .overlay {
                RoundedRectangle(cornerRadius: 10)
                    .stroke(Color(R.color.grayDarkColor), lineWidth: 1)
            }
        }
    }
}
