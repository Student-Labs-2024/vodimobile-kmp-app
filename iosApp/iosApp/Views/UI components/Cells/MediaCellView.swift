//
//  SwiftUIView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 18.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct MediaCellView: View {
    let cell: MediaLink
    
    var body: some View {
        if let url = URL(string: cell.url) {
            Link(destination: url) {
                HStack(spacing: 20) {
                    Image(cell.icon)
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 32, height: 32)
                    Text(cell.title).font(.paragraph2).foregroundStyle(Color.black)
                    
                    Spacer()
                    
                    Image.chevronRight
                        .foregroundStyle(Color(R.color.grayDarkColor))
                        .fontWeight(.bold)
                }
            }
        } else {
            HStack(spacing: 20) {
                Image(cell.icon)
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: 32, height: 32)
                Text(cell.title).font(.paragraph2).foregroundStyle(Color.black)
                
                Spacer()
                
                Image.chevronRight
                    .foregroundStyle(Color(R.color.grayDarkColor))
                    .fontWeight(.bold)
            }
        }
    }
}
