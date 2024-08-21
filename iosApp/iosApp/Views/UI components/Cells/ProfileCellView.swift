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
    @Binding var showSignSuggestModal: Bool
    @ObservedObject var authManager = AuthManager.shared
    
    init(
        cell: ProfileMenuCell,
        showSignSuggestModal: Binding<Bool>
    ) {
        self.cell = cell
        self._showSignSuggestModal = showSignSuggestModal
    }
    
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
        let navigationLinkToView = NavigationLink(destination: destinationView) {
            VStack(alignment: .leading) {
                Text(cell.title).font(.header4).lineLimit(2).foregroundStyle(.black)
                
                HStack {
                    Spacer()
                    cell.icon
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                    
                }
            }
        }
            .padding(.vertical, 34)
            .padding(.horizontal, 24)
            .background(RoundedRectangle(cornerRadius: 20).fill(.white))
        
        let buttonSwitchModal =
        Button(action: {
            showSignSuggestModal.toggle()
        }, label: {
            VStack(alignment: .leading) {
                Text(cell.title)
                    .font(.header4)
                    .lineLimit(2)
                    .foregroundStyle(.black)
                    .multilineTextAlignment(.leading)
                
                HStack {
                    Spacer()
                    cell.icon
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                    
                }
            }
            .padding(.vertical, 34)
            .padding(.horizontal, 24)
            .background(RoundedRectangle(cornerRadius: 20).fill(.white))
        })
        
        switch cell.cellType {
        case .conditions, .contacts, .faq:
            navigationLinkToView
        case .personalData:
            if authManager.isAuthenticated {
                navigationLinkToView
            } else {
                buttonSwitchModal
            }
        }
        
    }
}
