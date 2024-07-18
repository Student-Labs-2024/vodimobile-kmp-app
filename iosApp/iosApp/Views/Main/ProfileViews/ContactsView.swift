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
                ZStack {
                    Image(R.image.logoSmall)
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 40, height: 48)
                }
                .frame(width: 48, height: 48)
                .background(RoundedRectangle(cornerRadius: 10, style: .circular).fill(Color.white))
                .padding(.bottom, 20)
                
                Text("\(R.string.localizable.version) \(appVersion)")
                    .font(.paragraph4)
                    .foregroundStyle(Color(R.color.grayTextColor))
                Text(R.string.localizable.brandLabel)
                    .font(.paragraph4)
                    .foregroundStyle(Color(R.color.grayTextColor))
                
            }
            .frame(maxWidth: .infinity)
            .padding(.vertical, 25)
            .background(Color(R.color.blueBoxColor))
            .padding(.top, 40)
            
            VStack(alignment: .leading, spacing: 20) {
                HStack(spacing: 15) {
                    Image(R.image.gmailIcon)
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 24, height: 24)
                    
                    VStack(alignment: .leading) {
                        Text("\(R.string.localizable.emailText):")
                            .font(.paragraph5)
                            .foregroundStyle(Color(R.color.grayTextColor))
                        
                        Link(destination: URL(string: contactEmail)!) {
                            HStack {
                                Text(contactEmail)
                                    .font(.paragraph2)
                                    .foregroundStyle(Color.black)
                                
                                Spacer()
                            }.overlay {
                                Rectangle()
                                    .fill(Color(R.color.grayDarkColor))
                                    .frame(height: 0.5, alignment: .bottom)
                                    .offset(y: 10)
                            }
                        }
                    }
                    
                    Spacer()
                }
                
                HStack(spacing: 15)  {
                    Image(R.image.phoneIcon)
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 24, height: 24)
                    
                    VStack(alignment: .leading) {
                        Text("\(R.string.localizable.phone):")
                            .font(.paragraph5)
                            .foregroundStyle(Color(R.color.grayTextColor))
                        
                        Link(destination: URL(string: "tel:\(cleanPhoneNumber(contactPhone))")!) {
                            HStack {
                                Text(contactPhone)
                                    .font(.paragraph2)
                                    .foregroundStyle(Color.black)
                                
                                Spacer()
                            }.overlay {
                                Rectangle()
                                    .fill(Color(R.color.grayDarkColor))
                                    .frame(height: 0.5, alignment: .bottom)
                                    .offset(y: 10)
                            }
                        }
                    }
                    
                    Spacer()
                }
                
                HStack(spacing: 15)  {
                    Image(R.image.telegramIcon)
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 24, height: 24)
                    
                    VStack(alignment: .leading) {
                        Text("\(R.string.localizable.officeLocationText):").font(.paragraph5).foregroundStyle(Color(R.color.grayTextColor))
                        
                        HStack {
                            Text(contactLocation)
                                .font(.paragraph2)
                                .foregroundStyle(Color.black)
                            Spacer()
                        }.overlay {
                            Rectangle()
                                .fill(Color(R.color.grayDarkColor))
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
                Text("\(R.string.localizable.ourMediaText):").font(.header3)
                
                VStack(alignment: .leading, spacing: 25) {
                    Link(destination: URL(string: vkLink)!) {
                        HStack(spacing: 20) {
                            Image(R.image.vkIcon)
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .frame(width: 32, height: 32)
                            Text("\(R.string.localizable.vk)").font(.paragraph2).foregroundStyle(Color.black)
                            
                            Spacer()
                            
                            Image(systemName: "chevron.right")
                                .foregroundStyle(Color(R.color.grayDarkColor))
                                .fontWeight(.bold)
                        }
                    }
                    
                    Link(destination: URL(string: whatsappLink)!) {
                        HStack(spacing: 20) {
                            Image(R.image.whatsappIcon)
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .frame(width: 32, height: 32)
                            Text("\(R.string.localizable.whatsapp)").font(.paragraph2).foregroundStyle(Color.black)
                            
                            Spacer()
                            
                            Image(systemName: "chevron.right")
                                .foregroundStyle(Color(R.color.grayDarkColor))
                                .fontWeight(.bold)
                        }
                    }
                    
                    Link(destination: URL(string: tgLink)!) {
                        HStack(spacing: 20) {
                            Image(R.image.telegramIcon)
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .frame(width: 32, height: 32)
                            Text("\(R.string.localizable.telegram)")
                                .font(.paragraph2)
                                .foregroundStyle(Color.black)
                            
                            Spacer()
                            
                            Image(systemName: "chevron.right")
                                .foregroundStyle(Color(R.color.grayDarkColor))
                                .fontWeight(.bold)
                        }
                    }
                }
                .padding(.vertical, 20)
            }.padding(.horizontal, 30)
            
            Spacer()
        }
        .navigationBarBackButtonHidden()
        .toolbar {
            CustomToolbar(title: R.string.localizable.contactsScreenTitle)
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
