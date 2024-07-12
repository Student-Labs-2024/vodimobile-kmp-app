//
//  CusomToolbar.swift
//  iosApp
//
//  Created by Sergey Ivanov on 12.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct CustomToolbar: ToolbarContent {

    let title: String

    @Environment(\.presentationMode) var presentationMode
    @Environment(\.dismiss) private var dismiss

    var body: some ToolbarContent {
        ToolbarItem(placement: .navigationBarLeading){
            Button(action: {
                dismiss()
            }, label: {
                Image(systemName: "chevron.left").foregroundStyle(Color.black).fontWeight(.bold)
            })
        }
        
        ToolbarItem(placement: .principal) {
            Text(LocalizedStringKey(title))
                .font(.header1)
                .foregroundColor(Color.black)
        }
    }
}
