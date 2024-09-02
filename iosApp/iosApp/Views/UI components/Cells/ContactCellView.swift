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
        let cellBody = HStack {
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

        HStack(spacing: 15) {
            Image(cell.icon)
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 24, height: 24)

            VStack(alignment: .leading) {
                Text("\(cell.title):")
                    .font(.paragraph5)
                    .foregroundStyle(Color(R.color.grayText))

                cellBody
                    .onTapGesture {
                        handleTap(on: cell)
                    }
                    .contextMenu {
                        Button(action: {
                            copyToClipboard(cell.contact)
                        }) {
                            Label(
                                title: {
                                    Text(R.string.localizable.copyText)
                                        .foregroundStyle(Color(R.color.text))
                                },
                                icon: {
                                    Image.copyIcon
                                        .foregroundStyle(Color(R.color.text))
                                }
                            )
                        }
                    }
            }
        }
    }

    private func handleTap(on cell: ContactCell) {
        let urlString: String
        switch cell.cellType {
        case .email:
            urlString = "mailto://\(cell.contact)"
        case .phone:
            urlString = "tel://\(cell.contact.cleanUp())"
        case .officeLocation:
            urlString = "maps://?q=\(cell.contact.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed) ?? "")"
        }

        if let url = URL(string: urlString) {
            if UIApplication.shared.canOpenURL(url) {
                UIApplication.shared.open(url, options: [:], completionHandler: nil)
            } else {
                print("Failed to open URL: \(urlString)")
            }
        }
    }

    private func copyToClipboard(_ contact: String) {
        UIPasteboard.general.string = contact
        print("Contact copied: \(contact)")
    }
}
