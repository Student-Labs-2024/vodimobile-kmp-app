//
//  PersonDataView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 11.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct PersonDataView: View {
    @Environment(\.dismiss) private var dismiss
    
    var body: some View {
        VStack {
            Spacer()
            Text("Hello, PersonDataView!")
                .frame(maxWidth: .infinity)
            Spacer()
        }
        .padding(0)
        .background(Color.grayLightColor)
        .navigationBarBackButtonHidden()
        .toolbar {
            ToolbarItem(placement: .navigationBarLeading){
                Button(action: {
                    dismiss()
                }, label: {
                    Image(systemName: "chevron.left").foregroundStyle(Color.black).fontWeight(.bold)
                })
            }
            
            ToolbarItem(placement: .principal) {
                Text(LocalizedStringKey("personData"))
                    .font(.header1)
                    .foregroundColor(Color.black)
            }
            
            ToolbarItem(placement: .topBarTrailing) {
                Button(action: {
                    dismiss()
                }, label: {
                    Image(systemName: "checkmark").foregroundStyle(Color.grayDarkColor).fontWeight(.bold)
                })
            }
        }
    }
}

#Preview {
    PersonDataView()
}
