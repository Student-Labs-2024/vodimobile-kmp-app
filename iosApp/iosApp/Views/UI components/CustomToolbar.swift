//
//  CusomToolbar.swift
//  iosApp
//
//  Created by Sergey Ivanov on 12.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import RswiftResources

struct CustomToolbar: ToolbarContent {
    let title: StringResource
    var trailingToolbarItem: TrailingToolbarItem?
    
    @Environment(\.dismiss) private var dismiss
    
    init(title: StringResource, trailingToolbarItem: TrailingToolbarItem? = nil) {
        self.title = title
        self.trailingToolbarItem = trailingToolbarItem
    }
    
    var body: some ToolbarContent {
        ToolbarItem(placement: .navigationBarLeading){
            Button(action: {
                dismiss()
            }, label: {
                Image.chevronLeft.foregroundStyle(Color.black).fontWeight(.bold)
            })
        }
        
        ToolbarItem(placement: .principal) {
            Text(title)
                .font(.header1)
                .foregroundColor(Color.black)
        }
        
        if let trailingToolbarItem = trailingToolbarItem {
            ToolbarItem(placement: .topBarTrailing) {
                trailingToolbarItem
            }
        }
    }
}

struct TrailingToolbarItem: View {
    let image: Image
    @ObservedObject var observedObject: PersonalDataViewModel
    var actionAfterTapping: () -> Void
    
    var body: some View {
        Button(action: {
            actionAfterTapping()
        }) {
            image
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 18, height: 18)
                .fontWeight(.bold)
                .foregroundStyle(observedObject.userInput.fieldsIsValid() ? (observedObject.dataIsEditing ? Color(R.color.blueColor) : Color(R.color.grayDarkColor)) : Color(R.color.grayDarkColor))
        }
        .disabled(!observedObject.userInput.fieldsIsValid() && observedObject.dataIsEditing)
    }
}
