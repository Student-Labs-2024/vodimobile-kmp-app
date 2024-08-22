//
//  ContactsView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 12.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ContactsView: View {
    @ObservedObject private var viewModel: ContactsViewModel
    @Environment(\.dismiss) private var dismiss

    init() {
        self.viewModel = ContactsViewModel()
    }

    var body: some View {
        VStack {
            ScrollView(.vertical) {
                VStack(spacing: 20) {
                    VStack {
                        ZStack {
                            Image(R.image.logoSmall)
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .frame(width: 40, height: 48)
                        }
                        .frame(width: 48, height: 48)
                        .background(RoundedRectangle(cornerRadius: 10, style: .circular).fill(Color.white))
                        .padding(.bottom, 20)

                        Text("\(R.string.localizable.version()) \(appVersion)")
                            .font(.paragraph4)
                            .foregroundStyle(Color(R.color.grayText))
                        Text(R.string.localizable.brandLabel)
                            .font(.paragraph4)
                            .foregroundStyle(Color(R.color.grayText))

                    }
                    .frame(maxWidth: .infinity)
                    .padding(.vertical, 25)
                    .background(Color(R.color.blueBox))
                    .padding(.top, 40)

                    VStack(spacing: 30) {
                        VStack(alignment: .leading, spacing: 20) {
                            ForEach(viewModel.contactCells) { cell in
                                ContactCellView(cell: cell)
                            }
                        }

                        VStack(alignment: .leading, spacing: 20) {
                            Text("\(R.string.localizable.ourMediaText()):").font(.header3)

                            VStack(alignment: .leading, spacing: 25) {
                                ForEach(viewModel.mediaLinks) { cell in
                                    MediaCellView(cell: cell)
                                }
                            }
                        }
                    }
                    .padding(.horizontal, 30)
                    .padding(.vertical, 20)
                }
            }
        }
        .navigationBarBackButtonHidden()
        .toolbar {
            CustomToolbar(title: R.string.localizable.contactsScreenTitle)
        }
    }
}

#Preview {
    ContactsView()
}
