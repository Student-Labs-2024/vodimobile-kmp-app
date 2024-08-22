//
//  SwiftUIView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 18.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ContactCellView: View {
    let cell: ContactCell

    var body: some View {
        HStack(spacing: 15) {
            Image(cell.icon)
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 24, height: 24)

            VStack(alignment: .leading) {
                Text("\(cell.title):")
                    .font(.paragraph5)
                    .foregroundStyle(Color(R.color.grayText))

                if let url = URL(string: cell.contact) {
                    Link(destination: url) {
                        HStack {
                            Text(cell.contact)
                                .font(.paragraph2)
                                .foregroundStyle(Color(R.color.text))
                            Spacer()
                        }.overlay {
                            Rectangle()
                                .fill(Color(R.color.grayDark))
                                .frame(height: 0.5, alignment: .bottom)
                                .offset(y: 10)
                        }
                    }
                } else {
                    HStack {
                        Text(cell.contact)
                            .font(.paragraph2)
                            .foregroundStyle(Color(R.color.text))
                        Spacer()
                    }.overlay {
                        Rectangle()
                            .fill(Color(R.color.grayDark))
                            .frame(height: 0.5, alignment: .bottom)
                            .offset(y: 10)
                    }
                }
            }
        }
    }
}
