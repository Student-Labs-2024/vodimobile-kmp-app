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
    
    static let profileMenuCells = [
        ProfileMenuCell(id: 1, icon: Image(R.image.personFill), title: R.string.localizable.personData(), cellType: .personalData),
        ProfileMenuCell(id: 2, icon: Image(R.image.newspaper), title: R.string.localizable.rulesText(), cellType: .conditions),
        ProfileMenuCell(id: 3, icon: Image(R.image.questionFill), title: R.string.localizable.faQ(), cellType: .faq),
        ProfileMenuCell(id: 4, icon: Image(R.image.chatBubbles), title: R.string.localizable.contacts(), cellType: .contacts)
    ]
}
