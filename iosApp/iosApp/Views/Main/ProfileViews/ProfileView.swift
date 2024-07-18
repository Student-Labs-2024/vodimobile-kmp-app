//
//  ProfileView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 11.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ProfileView: View {
    @ObservedObject private var viewModel: ProfileViewModel
    @State private var showAlert: Bool = false
    
    init(viewModel: ProfileViewModel = .init()) {
        self.viewModel = viewModel
    }
    
    var body: some View {
        NavigationView {
            VStack {
                Text(R.string.localizable.profileScreenTitle)
                    .font(.header1)
                    .foregroundStyle(Color.black)
                
                Spacer()
                
                VStack(spacing: ProfileConfig.spacingBetweenBlocks) {
                    VStack {
                        NavigationLink(destination: PersonDataView()) {
                            HStack(spacing: ProfileConfig.horizontalSpacingAvatarAndText) {
                                ZStack(alignment: .center) {
                                    Image(systemName: "person")
                                        .frame(width: ProfileConfig.avatarIconSize, height: ProfileConfig.avatarIconSize)
                                        .fontWeight(.bold)
                                }
                                .frame(width: ProfileConfig.avatarFrameSize, height: ProfileConfig.avatarFrameSize, alignment: .center)
                                .background(RoundedRectangle(cornerRadius: 10).fill(Color(R.color.blueBoxColor)))
                                
                                VStack(alignment: .leading) {
                                    Text(R.string.localizable.personData).font(.paragraph2).foregroundStyle(.black)
                                    Text(R.string.localizable.personDataText).font(.paragraph5).foregroundStyle(Color(R.color.grayTextColor))
                                }
                                
                                Spacer()
                                
                                Image(R.image.editIcon).frame(width: ProfileConfig.editIconSize, height: ProfileConfig.editIconSize)
                            }
                            .foregroundStyle(Color(R.color.grayDarkColor))
                        }
                    }
                    .padding(.horizontal, 20)
                    .padding(.vertical, 25)
                    .background(RoundedRectangle(cornerRadius: 10, style: .continuous).fill(.white))
                    
                    VStack(spacing: ProfileConfig.spacingBetweenBlocks) {
                        ForEach(viewModel.profileMenuData) { cell in
                            ProfileCellView(cell: cell)
                        }
                    }
                    .padding(ProfileConfig.insetPaddingBlock)
                    .background(RoundedRectangle(cornerRadius: 10, style: .continuous).fill(.white))
                    
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
                    .padding(ProfileConfig.insetPaddingExitButton)
                    .background(RoundedRectangle(cornerRadius: 10, style: .continuous).fill(.white))
                    .alert(R.string.localizable.exitAlertTitle(), isPresented: $showAlert) {
                        Button(R.string.localizable.exitFirstBtnText(), role: .destructive) {}
                        
                        Button(R.string.localizable.exitSecondBtnText(), role: .cancel) {
                            showAlert.toggle()
                        }
                    } message: {
                        Text(R.string.localizable.exitAlertText)
                    }
                }
                
                Spacer()
            }
            .padding(.horizontal, horizontalPadding)
            .background(Color(R.color.grayLightColor))
            .navigationBarBackButtonHidden()
        }
    }
}

#Preview {
    ProfileView()
}
