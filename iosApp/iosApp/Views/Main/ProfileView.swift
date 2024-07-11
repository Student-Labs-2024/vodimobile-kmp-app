//
//  ProfileView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 11.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ProfileView: View {
    var body: some View {
        NavigationView {
            VStack {
                Text(LocalizedStringKey("profileTabItem")).font(.header1).foregroundStyle(Color.black)
                
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
                        Button(action: {}, label: {
                            HStack(spacing: 20) {
                                Image(systemName: "doc.text").frame(width: 24, height: 24).fontWeight(.bold)
                                
                                Text(LocalizedStringKey("rulesText")).font(.paragraph2).foregroundStyle(Color.black)
                                
                                Spacer()
                                
                                Image(systemName: "chevron.right")
                            }
                            .foregroundStyle(Color.grayDarkColor)
                        })
                        
                        Button(action: {}, label: {
                            HStack(spacing: 20) {
                                Image(systemName: "info.circle").frame(width: 24, height: 24).fontWeight(.bold)
                                
                                Text(LocalizedStringKey("FAQ")).font(.paragraph2).foregroundStyle(Color.black)
                                
                                Spacer()
                                
                                Image(systemName: "chevron.right")
                            }
                            .foregroundStyle(Color.grayDarkColor)
                        })
                        
                        Button(action: {}, label: {
                            HStack(spacing: 20) {
                                Image(systemName: "envelope").frame(width: 24, height: 24).fontWeight(.bold)
                                
                                Text(LocalizedStringKey("contacts")).font(.paragraph2).foregroundStyle(Color.black)
                                
                                Spacer()
                                
                                Image(systemName: "chevron.right")
                            }
                            .foregroundStyle(Color.grayDarkColor)
                        })
                    }
                    .padding(28)
                    .background(RoundedRectangle(cornerRadius: 10, style: .continuous).fill(.white))
                    
                    VStack {
                        Button(action: {}, label: {
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
