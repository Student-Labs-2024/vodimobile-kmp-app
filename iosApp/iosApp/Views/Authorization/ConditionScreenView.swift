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
            CustomToolbar(title: R.string.localizable.conditionsScreenTitle)
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
