//
//  FilledButton.swift
//  iosApp
//
//  Created by Sergey Ivanov on 17.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct FilledButton: View {

    let configuration: ButtonStyle.Configuration
    let height: CGFloat?

    @Environment(\.isEnabled) private var isEnabled: Bool

    var body: some View {
        configuration.label
            .padding()
            .frame(maxWidth: .infinity, maxHeight: height)
            .background(isEnabled ? (configuration.isPressed ? Color(R.color.blueDarkColor) : Color(R.color.blueColor)) : Color(R.color.blueLightColor))
            .foregroundColor(.white)
            .cornerRadius(15)
            .font(.button)
            .disabled(!isEnabled)
    }
}
