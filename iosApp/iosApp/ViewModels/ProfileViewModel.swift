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
    let cellType: ProfileCellType
    
    enum ProfileCellType {
        case conditions, faq, contacts
    }
    
    static let profileMenuCells = [
        ProfileMenuCell(id: 1, icon: Image.docText, title: R.string.localizable.rulesText(), cellType: .conditions),
        ProfileMenuCell(id: 2, icon: Image.infoCircle, title: R.string.localizable.faQ(), cellType: .faq),
        ProfileMenuCell(id: 3, icon: Image.letter, title: R.string.localizable.contacts(), cellType: .contacts)
    ]
}
