//
//  AutoSizedTextEditorView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 08.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct AutoSizingTextEditor: View {
    @Binding var text: String
    @Binding private var isFocused: Bool
    @State private var textViewHeight: CGFloat = 50
    @State private var isPlaceholderVisible: Bool = true

    init(text: Binding<String?>, isFocused: Binding<Bool>) {
        if let unwrapText = text.wrappedValue {
            _text = Binding.constant(unwrapText)
        } else {
            _text = Binding.constant("")
        }
        self._isFocused = isFocused
    }

    var body: some View {
        VStack(alignment: .leading) {
            Text(R.string.localizable.commentToReservation)
                .font(.header4)
            GeometryReader { _ in
                Group {
                    if text.isEmpty && isPlaceholderVisible {
                        Text(R.string.localizable.commentToReservationPlaceholder)
                            .font(.paragraph4)
                            .foregroundStyle(Color(R.color.grayText))
                    } else {
                        TextEditor(text: $text)
                            .frame(height: textViewHeight)
                            .background(GeometryReader { geo -> SwiftUI.Color in
                                DispatchQueue.main.async {
                                    let size = geo.size
                                    if size.height != textViewHeight {
                                        textViewHeight = size.height
                                    }
                                }
                                return Color.clear
                            })
                            .padding(.horizontal, 5)
                            .font(.paragraph4)
                            .foregroundStyle(Color(R.color.blueColor))
                            .onChange(of: text) { newValue in
                                self.isPlaceholderVisible = newValue.isEmpty ? true : false
                            }
                    }
                }
            }
            .padding(5)
            .frame(minHeight: textViewHeight, maxHeight: textViewHeight)
            .background(RoundedRectangle(cornerRadius: 10).fill(.white))
            .overlay(
                RoundedRectangle(cornerRadius: 10)
                    .stroke(Color(R.color.grayDark), lineWidth: 1)
            )
            .onTapGesture {
                if text.isEmpty {
                    isPlaceholderVisible = false
                }
            }
        }
        .animation(.easeInOut, value: textViewHeight)
    }
}
