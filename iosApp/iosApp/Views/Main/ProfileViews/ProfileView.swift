//
//  ProfileView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 11.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ProfileView: View {
    @State private var showAlert: Bool = false
    
    var body: some View {
        NavigationView {
            VStack {
                Text(R.string.localizable.profileScreenTitle)
                    .font(.header1)
                    .foregroundStyle(Color.black)
                
                Spacer()
                
                VStack(spacing: 30) {
                    VStack {
                        NavigationLink(destination: PersonDataView()) {
                            HStack(spacing: 10) {
                                ZStack(alignment: .center) {
                                    Image(systemName: "person")
                                        .frame(width: 24, height: 24)
                                        .fontWeight(.bold)
                                }
                                .frame(width: 48, height: 48, alignment: .center)
                                .background(RoundedRectangle(cornerRadius: 10).fill(Color(R.color.blueBoxColor)))
                                
                                VStack(alignment: .leading) {
                                    Text(R.string.localizable.personData).font(.paragraph2).foregroundStyle(.black)
                                    Text(R.string.localizable.personDataText).font(.paragraph5).foregroundStyle(Color(R.color.grayTextColor))
                                }
                                
                                Spacer()
                                
                                Image(R.image.editIcon).frame(width: 24, height: 24)
                            }
                            .foregroundStyle(Color(R.color.grayDarkColor))
                        }
                    }
                    .padding(.horizontal, 20)
                    .padding(.vertical, 25)
                    .background(RoundedRectangle(cornerRadius: 10, style: .continuous).fill(.white))
                    
                    VStack(spacing: 30) {
                        NavigationLink(destination: RulesAndConditionsView()) {
                            HStack(spacing: 20) {
                                Image(systemName: "doc.text")
                                    .resizable()
                                    .aspectRatio(contentMode: .fit)
                                    .frame(width: 20, height: 20)
                                
                                Text(R.string.localizable.rulesText).font(.paragraph2).foregroundStyle(Color.black)
                                
                                Spacer()
                                
                                Image(systemName: "chevron.right")
                            }
                            .foregroundStyle(Color(R.color.grayDarkColor))
                        }
                        
                        NavigationLink(destination: FAQScreenView()) {
                            HStack(spacing: 20) {
                                Image(systemName: "info.circle")
                                    .resizable()
                                    .aspectRatio(contentMode: .fit)
                                    .frame(width: 20, height: 20)
                                
                                Text(R.string.localizable.faQ).font(.paragraph2).foregroundStyle(Color.black)
                                
                                Spacer()
                                
                                Image(systemName: "chevron.right")
                            }
                            .foregroundStyle(Color(R.color.grayDarkColor))
                        }
                        
                        NavigationLink(destination: ContactsView()) {
                            HStack(spacing: 20) {
                                Image(systemName: "envelope")
                                    .resizable()
                                    .aspectRatio(contentMode: .fit)
                                    .frame(width: 20, height: 20)
                                
                                Text(R.string.localizable.contacts).font(.paragraph2).foregroundStyle(Color.black)
                                
                                Spacer()
                                
                                Image(systemName: "chevron.right")
                            }
                            .foregroundStyle(Color(R.color.grayDarkColor))
                        }
                    }
                    .padding(28)
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
                    .padding(18)
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
            .padding(.horizontal, 16)
            .background(Color(R.color.grayLightColor))
            .navigationBarBackButtonHidden()
        }
    }
}

#Preview {
    ProfileView()
}
