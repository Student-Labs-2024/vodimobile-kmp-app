//
//  ConditionScreenView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 09.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ConditionScreenView: View {
    @Environment(\.dismiss) private var dismiss
    @State private var conditionText: String = ""
    
    var body: some View {
        NavigationLink(destination: MainScreenView()) {
            ScrollView(.vertical, showsIndicators: false) {
                Text(conditionText)
                    .font(.paragraph2)
                    .foregroundStyle(Color.black)
                    .frame(maxWidth: .infinity)
                    .multilineTextAlignment(.leading)
                    .onAppear {
                        loadUserAgreement()
                    }
            }
            .padding([.leading, .bottom, .trailing], 24)
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
                    Text(LocalizedStringKey("conditionsScreenTitle"))
                        .font(.header1)
                        .foregroundColor(Color.black)
                }
            }
        }
    }
    
    private func loadUserAgreement() {
        if let filePath = Bundle.main.path(forResource: "terms_and_conditions", ofType: "txt") {
            do {
                let contents = try String(contentsOfFile: filePath)
                conditionText = contents
            } catch {
                print("Error loading user agreement text")
            }
        }
    }
}

#Preview {
    ConditionScreenView()
}
