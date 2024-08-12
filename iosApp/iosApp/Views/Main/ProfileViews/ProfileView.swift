//
//  ProfileView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 11.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ProfileView: View {
    @EnvironmentObject var authManager: AuthManager
    @EnvironmentObject var dataStorage: KMPDataStorage
    @ObservedObject private var viewModel: ProfileViewModel
    @State private var showAlert: Bool = false
    @State private var showSignSuggestModal: Bool = false
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
                    .foregroundStyle(Color.black)
                
                Spacer()
                
                VStack(spacing: ProfileConfig.spacingBetweenBlocks) {
                    LazyVGrid(columns: columns, spacing: 24) {
                        ForEach(viewModel.profileMenuData) { cell in
                            ProfileCellView(cell: cell)
                        }
                    }
//                    VStack {
//                        if authManager.isAuthenticated {
//                            NavigationLink(destination: PersonDataView()) {
//                                HStack(spacing: ProfileConfig.horizontalSpacingAvatarAndText) {
//                                    ZStack(alignment: .center) {
//                                        Image.person
//                                            .frame(width: ProfileConfig.avatarIconSize, height: ProfileConfig.avatarIconSize)
//                                            .fontWeight(.bold)
//                                    }
//                                    .frame(
//                                        width: ProfileConfig.avatarFrameSize,
//                                        height: ProfileConfig.avatarFrameSize,
//                                        alignment: .center
//                                    )
//                                    .background(RoundedRectangle(cornerRadius: 10).fill(Color(R.color.blueBoxColor)))
//                                    
//                                    VStack(alignment: .leading) {
//                                        Text(R.string.localizable.personData)
//                                            .font(.paragraph2)
//                                            .foregroundStyle(.black)
//                                        Text(R.string.localizable.personDataText)
//                                            .font(.paragraph5)
//                                            .foregroundStyle(Color(R.color.grayTextColor))
//                                    }
//                                    
//                                    Spacer()
//                                    
//                                    Image(R.image.editIcon)
//                                        .frame(
//                                            width: ProfileConfig.editIconSize,
//                                            height: ProfileConfig.editIconSize
//                                        )
//                                }
//                                .foregroundStyle(Color(R.color.grayDarkColor))
//                            }
//                        } else {
//                            Button {
//                                showSignSuggestModal.toggle()
//                            } label: {
//                                HStack(spacing: ProfileConfig.horizontalSpacingAvatarAndText) {
//                                    ZStack(alignment: .center) {
//                                        Image.person
//                                            .frame(width: ProfileConfig.avatarIconSize, height: ProfileConfig.avatarIconSize)
//                                            .fontWeight(.bold)
//                                    }
//                                    .frame(
//                                        width: ProfileConfig.avatarFrameSize,
//                                        height: ProfileConfig.avatarFrameSize,
//                                        alignment: .center
//                                    )
//                                    .background(RoundedRectangle(cornerRadius: 10).fill(Color(R.color.blueBoxColor)))
//                                    
//                                    Text(R.string.localizable.loginToAccount)
//                                        .font(.paragraph5)
//                                        .foregroundStyle(.black)
//                                    
//                                    Spacer()
//                                    
//                                    Image.chevronRight
//                                        .frame(
//                                            width: ProfileConfig.editIconSize,
//                                            height: ProfileConfig.editIconSize
//                                        )
//                                }
//                                .foregroundStyle(Color(R.color.grayDarkColor))
//                            }
//                        }
//                    }
//                    .padding(.horizontal, 20)
//                    .padding(.vertical, 25)
//                    .background(RoundedRectangle(cornerRadius: 10, style: .continuous).fill(.white))
//                    
//                    VStack(spacing: ProfileConfig.spacingBetweenBlocks) {
//                        ForEach(viewModel.profileMenuData) { cell in
//                            ProfileCellView(cell: cell)
//                        }
//                    }
//                    .padding(ProfileConfig.insetPaddingBlock)
//                    .background(RoundedRectangle(cornerRadius: 10, style: .continuous).fill(.white))
                    
                    if authManager.isAuthenticated {
                        VStack {
                            Button(action: {
                                showAlert.toggle()
                                // TODO: - Appear modal with sign suggesting
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
            .background(Color(R.color.grayLightColor))
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
