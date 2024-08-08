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
    @State private var textViewHeight: CGFloat = 50
    @FocusState.Binding private var isFocused: Bool
    
    init(text: Binding<String>, isFocused: FocusState<Bool>.Binding) {
        self._text = text
        self._isFocused = isFocused
    }

    var body: some View {
        VStack(alignment: .leading) {
            Text(R.string.localizable.commentToReservation)
                .font(.header4)
            GeometryReader { geometry in
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
                    .padding(.horizontal, 4)
            }
            .padding(5)
            .frame(minHeight: textViewHeight, maxHeight: textViewHeight)
            .background(RoundedRectangle(cornerRadius: 10).fill(.white))
            .overlay(
                RoundedRectangle(cornerRadius: 10)
                    .stroke(Color(R.color.grayDarkColor), lineWidth: 1)
            )
        }
        .animation(.easeInOut, value: textViewHeight)
    }
}
