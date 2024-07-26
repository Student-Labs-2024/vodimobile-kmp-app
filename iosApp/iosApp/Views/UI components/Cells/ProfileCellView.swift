//
//  File.swift
//  iosApp
//
//  Created by Sergey Ivanov on 18.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ProfileCellView: View {
    let cell: ProfileMenuCell
    
    var body: some View {
        NavigationLink(destination: RulesAndConditionsView()) {
            HStack(spacing: ProfileConfig.horizontalSpacingBetweenIconAndText) {
                cell.icon
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: ProfileConfig.mainIconsSize, height: ProfileConfig.mainIconsSize)
                
                Text(cell.title).font(.paragraph2).foregroundStyle(Color.black)
                
                Spacer()
                
                Image.chevronRight
            }
            .foregroundStyle(Color(R.color.grayDarkColor))
        }
    }
}
