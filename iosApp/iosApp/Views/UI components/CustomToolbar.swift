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

    @Environment(\.dismiss) private var dismiss

    init(title: StringResource) {
        self.title = title
    }

    var body: some ToolbarContent {
        ToolbarItem(placement: .navigationBarLeading) {
            Button(action: {
                dismiss()
            }, label: {
                Image.chevronLeft.foregroundStyle(Color(R.color.text)).fontWeight(.bold)
            })
        }

        ToolbarItem(placement: .principal) {
            Text(title)
                .font(.header1)
                .foregroundColor(Color(R.color.text))
        }
    }
}
