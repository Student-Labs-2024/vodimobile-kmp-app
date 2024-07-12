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
                Text(String.ScreenTitles.profileScreenTitle)
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
                                .background(RoundedRectangle(cornerRadius: 10).fill(Color.blueBoxColor))
                                
                                VStack(alignment: .leading) {
                                    Text(LocalizedStringKey("personData")).font(.paragraph2).foregroundStyle(Color.black)
                                    Text(LocalizedStringKey("fioText")).font(.paragraph5).foregroundStyle(Color.grayTextColor)
                                }
                                
                                Spacer()
                                
                                Image("editIcon").frame(width: 24, height: 24)
                            }
                            .foregroundStyle(Color.grayDarkColor)
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
                                
                                Text(LocalizedStringKey("rulesText")).font(.paragraph2).foregroundStyle(Color.black)
                                
                                Spacer()
                                
                                Image(systemName: "chevron.right")
                            }
                            .foregroundStyle(Color.grayDarkColor)
                        }
                        
                        NavigationLink(destination: FAQScreenView()) {
                            HStack(spacing: 20) {
                                Image(systemName: "info.circle")
                                    .resizable()
                                    .aspectRatio(contentMode: .fit)
                                    .frame(width: 20, height: 20)
                                
                                Text(LocalizedStringKey("FAQ")).font(.paragraph2).foregroundStyle(Color.black)
                                
                                Spacer()
                                
                                Image(systemName: "chevron.right")
                            }
                            .foregroundStyle(Color.grayDarkColor)
                        }
                        
                        NavigationLink(destination: ContactsView()) {
                            HStack(spacing: 20) {
                                Image(systemName: "envelope")
                                    .resizable()
                                    .aspectRatio(contentMode: .fit)
                                    .frame(width: 20, height: 20)
                                
                                Text(LocalizedStringKey("contacts")).font(.paragraph2).foregroundStyle(Color.black)
                                
                                Spacer()
                                
                                Image(systemName: "chevron.right")
                            }
                            .foregroundStyle(Color.grayDarkColor)
                        }
                    }
                    .padding(28)
                    .background(RoundedRectangle(cornerRadius: 10, style: .continuous).fill(.white))
                    
                    VStack {
                        Button(action: {
                            showAlert.toggle()
                        }, label: {
                            HStack(spacing: 20) {
                                Image("exitIcon")
                                
                                Text(LocalizedStringKey("exitText"))
                                
                                Spacer()
                            }
                            .foregroundStyle(Color.redColor)
                        })
                    }
                    .padding(18)
                    .background(RoundedRectangle(cornerRadius: 10, style: .continuous).fill(.white))
                    .alert(LocalizedStringKey("exitAlertTitle"), isPresented: $showAlert) {
                        Button(LocalizedStringKey("exitFirstBtnText"), role: .destructive) {}
                        
                        Button(LocalizedStringKey("exitSecondBtnText"), role: .cancel) {
                            showAlert.toggle()
                        }
                    } message: {
                        Text(LocalizedStringKey("exitAlertText"))
                    }
                }
                
                Spacer()
            }
            .padding(.horizontal, 16)
            .background(Color.grayLightColor)
            .navigationBarBackButtonHidden()
        }
    }
}

#Preview {
    ProfileView()
}
