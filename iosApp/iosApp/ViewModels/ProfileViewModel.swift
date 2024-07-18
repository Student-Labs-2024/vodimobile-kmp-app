//
//  ProfileViewModel.swift
//  iosApp
//
//  Created by Sergey Ivanov on 18.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI


final class ProfileViewModel: ObservableObject {
    let profileMenuData = ProfileMenuCell.profileMenuCells
}

struct ProfileMenuCell: Identifiable {
    let id: Int
    let icon: Image
    let title: String
    
    enum ProfileCellType {
        case conditions, faq, contacts
    }
    
    static let profileMenuCells = [
        ProfileMenuCell(id: 1, icon: Image(systemName: "doc.text"), title: R.string.localizable.rulesText()),
        ProfileMenuCell(id: 2, icon: Image(systemName: "info.circle"), title: R.string.localizable.faQ()),
        ProfileMenuCell(id: 3, icon: Image(systemName: "envelope"), title: R.string.localizable.contacts())
    ]
}
