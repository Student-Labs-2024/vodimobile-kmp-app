//
//  ContactsViewModel.swift
//  iosApp
//
//  Created by Sergey Ivanov on 18.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import RswiftResources

final class ContactsViewModel: ObservableObject {
    let contactCells: [ContactCell] = ContactCell.contactsData
    let mediaLinks: [MediaLink] = MediaLink.mediaLinks
    
    func cleanPhoneNumber(_ phoneNumber: String) -> String {
        let allowedCharacters = CharacterSet(charactersIn: "0123456789")
        return phoneNumber.components(separatedBy: allowedCharacters.inverted).joined()
    }
}

// Models
struct ContactCell: Identifiable {
    let id: Int
    let cellType: CellType
    let icon: RswiftResources.ImageResource
    let title: String
    let contact: String
    
    enum CellType {
        case email
        case phone
        case officeLocation
    }
    
    init(id: Int, cellType: CellType) {
        self.id = id
        self.cellType = cellType
        
        switch self.cellType {
        case .email:
            self.icon = R.image.gmailIcon
            self.title = R.string.localizable.emailText()
            self.contact = contactEmail
        case .officeLocation:
            self.icon = R.image.locationIcon
            self.title = R.string.localizable.officeLocation()
            self.contact = contactLocation
        case .phone:
            self.icon = R.image.phoneIcon
            self.title = R.string.localizable.phone()
            self.contact = contactPhone
        }
    }
    
    // static field
    static let contactsData = [
        ContactCell(id: 1, cellType: .email),
        ContactCell(id: 2, cellType: .phone),
        ContactCell(id: 3, cellType: .officeLocation),
    ]
}

struct MediaLink: Identifiable {
    let id: Int
    let cellType: MediaType
    let icon: RswiftResources.ImageResource
    let title: String
    let url: String
    
    enum MediaType {
        case vk
        case whatsapp
        case tg
    }
    
    init(id: Int, cellType: MediaType) {
        self.id = id
        self.cellType = cellType
        
        switch self.cellType {
        case .vk:
            self.icon = R.image.vkIcon
            self.title = R.string.localizable.vk()
            self.url = vkLink
        case .whatsapp:
            self.icon = R.image.whatsappIcon
            self.title = R.string.localizable.whatsapp()
            self.url = whatsappLink
        case .tg:
            self.icon = R.image.telegramIcon
            self.title = R.string.localizable.telegram()
            self.url = tgLink
        }
    }
    
    static let mediaLinks = [
        MediaLink(id: 1, cellType: .vk),
        MediaLink(id: 2, cellType: .whatsapp),
        MediaLink(id: 3, cellType: .tg),
    ]
}
