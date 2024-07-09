//
//  CheckboxView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 09.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct CheckboxView: View {
    @Binding var isChecked: Bool
    
    var body: some View {
        Button(action: {
            isChecked.toggle()
        }) {
            ZStack {
                RoundedRectangle(cornerRadius: 4)
                    .stroke(isChecked ? Color.blueColor : Color.grayDarkColor, lineWidth: 1)
                    .frame(width: 22, height: 22)
                
                if isChecked {
                    Image(systemName: "checkmark")
                        .foregroundColor(.blueColor)
                        .imageScale(.small)
                }
            }
            .padding(4)
        }
    }
}
