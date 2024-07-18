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
        Link(destination: URL(string: cell.url)!) {
            HStack(spacing: 20) {
                Image(cell.icon)
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: 32, height: 32)
                Text(cell.title).font(.paragraph2).foregroundStyle(Color.black)
                
                Spacer()
                
                Image(systemName: "chevron.right")
                    .foregroundStyle(Color(R.color.grayDarkColor))
                    .fontWeight(.bold)
            }
        }
    }
}
