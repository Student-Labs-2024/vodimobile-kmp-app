//
//  ContactsView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 12.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ContactsView: View {
    @Environment(\.dismiss) private var dismiss
    
    var body: some View {
        VStack(spacing: 20) {
            
            VStack {
                Image("logoSmallIcon")
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: 48, height: 48)
                    .padding(.bottom, 20)
                Text("Версия 0.00.0")
                    .font(.paragraph4)
                    .foregroundStyle(Color.grayTextColor)
                Text("© 2016-2022 Водимобиль")
                    .font(.paragraph4)
                    .foregroundStyle(Color.grayTextColor)
                
            }
            .frame(maxWidth: .infinity)
            .padding(.vertical, 25)
            .background(Color.blueBoxColor)
            .padding(.top, 40)
            
            VStack(alignment: .leading, spacing: 20) {
                HStack(spacing: 15) {
                    Image("gmailIcon")
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 24, height: 24)
                    
                    VStack(alignment: .leading) {
                        Text("Почта:")
                            .font(.paragraph5)
                            .foregroundStyle(Color.grayTextColor)
                        
                        Link(destination: URL(string: contactEmail)!) {
                            HStack {
                                Text(contactEmail)
                                    .font(.paragraph2)
                                    .foregroundStyle(Color.black)
                                
                                Spacer()
                            }.overlay {
                                Rectangle()
                                    .fill(Color.grayDarkColor)
                                    .frame(height: 0.5, alignment: .bottom)
                                    .offset(y: 10)
                            }
                        }
                    }
                    
                    Spacer()
                }
                
                HStack(spacing: 15)  {
                    Image("phoneIcon")
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 24, height: 24)
                    
                    VStack(alignment: .leading) {
                        Text("Номер телефона:").font(.paragraph5).foregroundStyle(Color.grayTextColor)
                        
                        Link(destination: URL(string: "tel:\(cleanPhoneNumber(contactPhone))")!) {
                            HStack {
                                Text(contactPhone)
                                    .font(.paragraph2)
                                    .foregroundStyle(Color.black)
                                
                                Spacer()
                            }.overlay {
                                Rectangle()
                                    .fill(Color.grayDarkColor)
                                    .frame(height: 0.5, alignment: .bottom)
                                    .offset(y: 10)
                            }
                        }
                    }
                    
                    Spacer()
                }
                
                HStack(spacing: 15)  {
                    Image("telegramIcon")
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 24, height: 24)
                    
                    VStack(alignment: .leading) {
                        Text("Адрес офиса:").font(.paragraph5).foregroundStyle(Color.grayTextColor)
                        
                        HStack {
                            Text(contactLocation)
                                .font(.paragraph2)
                                .foregroundStyle(Color.black)
                            Spacer()
                        }.overlay {
                            Rectangle()
                                .fill(Color.grayDarkColor)
                                .frame(height: 0.5, alignment: .bottom)
                                .offset(y: 10)
                        }
                    }
                    
                    Spacer()
                }
            }
            .padding(.horizontal, 30)
            .padding(.vertical, 20)
            
            VStack(alignment: .leading) {
                Text("Наши соцсети:").font(.header3)
                
                VStack(alignment: .leading, spacing: 25) {
                    Link(destination: URL(string: vkLink)!) {
                        HStack(spacing: 20) {
                            Image("vkIcon")
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .frame(width: 32, height: 32)
                            Text("Вконтакте").font(.paragraph2).foregroundStyle(Color.black)
                            
                            Spacer()
                            
                            Image(systemName: "chevron.right").foregroundStyle(Color.grayDarkColor).fontWeight(.bold)
                        }
                    }
                    
                    Link(destination: URL(string: whatsappLink)!) {
                        HStack(spacing: 20) {
                            Image("whatsappIcon")
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .frame(width: 32, height: 32)
                            Text("Whatsapp").font(.paragraph2).foregroundStyle(Color.black)
                            
                            Spacer()
                            
                            Image(systemName: "chevron.right").foregroundStyle(Color.grayDarkColor).fontWeight(.bold)
                        }
                    }
                    
                    Link(destination: URL(string: tgLink)!) {
                        HStack(spacing: 20) {
                            Image("telegramIcon")
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .frame(width: 32, height: 32)
                            Text("Telegram").font(.paragraph2).foregroundStyle(Color.black)
                            
                            Spacer()
                            
                            Image(systemName: "chevron.right").foregroundStyle(Color.grayDarkColor).fontWeight(.bold)
                        }
                    }
                }
                .padding(.vertical, 20)
            }.padding(.horizontal, 30)
            
            Spacer()
        }
        .navigationBarBackButtonHidden()
        .toolbar {
            CustomToolbar(title: String.ScreenTitles.contactsScreenTitle)
        }
    }
    
    private func cleanPhoneNumber(_ phoneNumber: String) -> String {
        let allowedCharacters = CharacterSet(charactersIn: "0123456789")
        return phoneNumber.components(separatedBy: allowedCharacters.inverted).joined()
    }
}

#Preview {
    ContactsView()
}
