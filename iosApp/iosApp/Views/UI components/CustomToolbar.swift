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
    let title: String
    let trailingToolbarItem: TrailingToolbarItem?
    
    @Environment(\.dismiss) private var dismiss
    
    init(title: StringResource, trailingToolbarItem: TrailingToolbarItem? = nil) {
        self.title = title()
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
            Text(LocalizedStringKey(title))
                .font(.header1)
                .foregroundColor(Color.black)
        }
        
        if let trailingToolbarItem = trailingToolbarItem {
            ToolbarItem(placement: .topBarTrailing) {
                Button(action: {
                    trailingToolbarItem.actionAfterTapping()
                }) {
                    trailingToolbarItem.image
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 18, height: 18)
                        .fontWeight(.bold)
                        .foregroundStyle(trailingToolbarItem.foregroundColor)
                    
                }
                .disabled(trailingToolbarItem.disableItem)
            }
        }
    }
}

struct TrailingToolbarItem {
    let image: Image
    @Binding var control: UserInputData {
        mutating didSet {
            foregroundColor = control.checkEmpty() ? Color(R.color.grayDarkColor) : Color(R.color.blueColor)
            disableItem = control.checkEmpty()
        }
    }
    var foregroundColor: Color
    var actionAfterTapping: () -> Void
    var disableItem: Bool = true
    
    init(image: Image, control: Binding<UserInputData>, actionAfterTapping: @escaping () -> Void) {
        self.image = image
        self._control = control
        self.actionAfterTapping = actionAfterTapping
        self.foregroundColor = control.wrappedValue.checkEmpty() ? Color(R.color.grayDarkColor) : Color(R.color.blueColor)
        self.disableItem = control.wrappedValue.checkEmpty()
    }
}
