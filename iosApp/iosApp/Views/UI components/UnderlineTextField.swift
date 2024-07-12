//
//  UnderlinedTextField.swift
//  iosApp
//
//  Created by Sergey Ivanov on 13.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct UnderlineTextField: View {
    @Binding var text: String
    var title: String
    var placeholder: String
    
    @FocusState private var isFocused: Bool
    @State private var isPlaceholderVisible: Bool = true
    
    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            Text(title)
                .font(.paragraph5)
                .foregroundColor(isFocused || !text.isEmpty ? .grayDarkColor : .clear)
                .animation(.easeInOut(duration: 0.2), value: isFocused || !text.isEmpty)
            
            ZStack(alignment: .leading) {
                if isPlaceholderVisible {
                    Text(placeholder)
                        .font(.paragraph5)
                        .foregroundColor(.grayTextColor)
                        .onTapGesture {
                            self.isFocused = true
                        }
                }
                
                TextField("", text: $text)
                    .font(.paragraph2)
                    .focused($isFocused)
                    .padding(.bottom, 5)
                    .opacity(isPlaceholderVisible ? 0 : 1)
                    .onChange(of: isFocused) { newValue in
                        withAnimation {
                            isPlaceholderVisible = !newValue && text.isEmpty
                        }
                    }
                    .onChange(of: text) { newValue in
                        withAnimation {
                            isPlaceholderVisible = text.isEmpty
                        }
                    }
            }
            .overlay(
                Rectangle()
                    .frame(height: 1)
                    .foregroundColor(isFocused ? .blueColor : .grayDarkColor),
                alignment: .bottom
            )
        }
    }
}
