//
//  TopToolBar.swift
//  iosApp
//
//  Created by Sergey Ivanov on 25.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct TabBarView: View {
    @Binding var index: Int
    var titles = AutoListType.allCases

    var body: some View {
        ScrollViewReader { proxy in
            ScrollView(.horizontal, showsIndicators: false) {
                HStack(spacing: 8) {
                    ForEach(titles.indices, id: \.self) { id in
                        let title = Text(titles[id]).id(id)
                            .onTapGesture {
                                withAnimation {
                                    index = id
                                }
                            }
                        if self.index == id {
                            title
                                .font(.tag)
                                .foregroundColor(.white)
                                .padding(.horizontal, 24)
                                .padding(.vertical, 8)
                                .background(
                                    RoundedRectangle(cornerRadius: 12)
                                        .fill(Color(R.color.blueColor))
                                )
                        } else {
                            title
                                .font(.tag)
                                .padding(.horizontal, 24)
                                .padding(.vertical, 8)
                                .foregroundColor(Color(R.color.blueColor))
                        }
                    }
                    .padding(.horizontal, 5)
                }
                .padding(.vertical, 20)
                .padding(.leading, 10)
            }
            .onChange(of: index) { value in
                withAnimation {
                    proxy.scrollTo(value, anchor: UnitPoint(x: UnitPoint.leading.x + leftOffset, y: UnitPoint.leading.y))
                }
            }
            .animation(.easeInOut, value: index)
        }
    }
    private let leftOffset: CGFloat = 0.1
}
