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
    
    @ViewBuilder
    var destinationView: some View {
        switch cell.cellType {
        case .personalData:
            PersonDataView()
        case .conditions:
            RulesAndConditionsView()
        case .contacts:
            ContactsView()
        case .faq:
            FAQScreenView()
        }
    }
    
    var body: some View {
        NavigationLink(destination: destinationView) {
            VStack(alignment: .leading) {
                Text(cell.title).font(.header4).lineLimit(2).foregroundStyle(.black)
                
                HStack {
                    Spacer()
                    cell.icon
                        .resizable()
                        .aspectRatio(contentMode: .fit)
//                        .frame(width: ProfileConfig.mainIconsWidth, height: ProfileConfig.mainIconsHeight)
                    
                }
            }
        }
        .padding(.vertical, 34)
        .padding(.horizontal, 24)
        .background(RoundedRectangle(cornerRadius: 20).fill(.white))
    }
}
