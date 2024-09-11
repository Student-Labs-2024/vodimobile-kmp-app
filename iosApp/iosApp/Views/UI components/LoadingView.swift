//
//  LoadingView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 22.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct LoadingView: View {
    var body: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 8, style: .circular)
                .opacity(0.5)
                .frame(width: 160, height: 160)
            ProgressView()
                .progressViewStyle(.circular)
                .tint(.white)
            .scaleEffect(2)
        }
    }
}

struct LoadingModifier: ViewModifier {
    @Binding var isLoading: Bool

    func body(content: Content) -> some View {
        ZStack {
            content
            if isLoading {
                LoadingView()
            }
        }
    }
}

extension View {
    func loadingOverlay(isLoading: Binding<Bool>) -> some View {
        self.modifier(LoadingModifier(isLoading: isLoading))
    }
}
