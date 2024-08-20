//
//  ProfileView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 11.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ProfileView: View {
    @ObservedObject var authManager = AuthManager.shared
    @State private var showAlert: Bool = false
    @State private var showSignSuggestModal: Bool = false
    private var viewModel: ProfileViewModel
    private let columns = [
        GridItem(.flexible(), spacing: 12),
        GridItem(.flexible(), spacing: 12)
    ]

    init() {
        self.viewModel = .init()
    }

    var body: some View {
        NavigationView {
            VStack {
                Text(R.string.localizable.profileScreenTitle)
                    .font(.header1)
                    .foregroundStyle(Color(R.color.text))

                Spacer()

                VStack(spacing: ProfileConfig.spacingBetweenBlocks) {
                    LazyVGrid(columns: columns, spacing: 24) {
                        ForEach(viewModel.profileMenuData) { cell in
                            ProfileCellView(
                                cell: cell,
                                showSignSuggestModal: $showSignSuggestModal
                            )
                        }
                    }

                    if authManager.isAuthenticated {
                        VStack {
                            Button(action: {
                                showAlert.toggle()
                            }, label: {
                                HStack(spacing: 20) {
                                    Image(R.image.exitIcon)

                                    Text(R.string.localizable.exitText)

                                    Spacer()
                                }
                                .foregroundStyle(Color(R.color.redColor))
                            })
                        }
                        .padding(.horizontal, 30)
                        .padding(.vertical, ProfileConfig.insetPaddingExitButton)
                        .background(RoundedRectangle(cornerRadius: 10, style: .continuous).fill(.white))
                        .alert(
                            R.string.localizable.exitAlertTitle(),
                            isPresented: $showAlert
                        ) {
                            Button(R.string.localizable.exitFirstBtnText(), role: .destructive) {
                                authManager.logout()
                            }

                            Button(R.string.localizable.exitSecondBtnText(), role: .cancel) {
                                showAlert.toggle()
                            }
                        } message: {
                            Text(R.string.localizable.exitAlertText)
                        }
                    }
                }

                Spacer()
            }
            .padding(.horizontal, horizontalPadding)
            .background(Color(R.color.grayLight))
            .navigationBarBackButtonHidden()
        }
        .fullScreenCover(isPresented: $showSignSuggestModal) {
            SignSuggestView(
                showSignSuggestModal: $showSignSuggestModal
            ).environmentObject(authManager)
        }
    }
}

#Preview {
    ProfileView()
}
