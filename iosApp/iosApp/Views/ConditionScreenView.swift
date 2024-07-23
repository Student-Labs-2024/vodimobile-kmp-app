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
    
    var viewModel: ConditionScreenViewModel
    
    init() {
        self.viewModel = ConditionScreenViewModel()
    }
    
    var body: some View {
        NavigationLink(destination: MainScreenView()) {
            ScrollView(.vertical, showsIndicators: false) {
                Text(conditionText)
                    .font(.paragraph2)
                    .foregroundStyle(Color.black)
                    .frame(maxWidth: .infinity)
                    .multilineTextAlignment(.leading)
                    .onAppear {
                        viewModel.loadUserAgreement(content: $conditionText)
                    }
            }
            .padding([.leading, .bottom, .trailing], ConditionScreenConfig.paddings)
            .navigationBarBackButtonHidden()
            .toolbar {
                ToolbarItem(placement: .navigationBarLeading) {
                    Button(action: {
                        dismiss()
                    }, label: {
                        Image(systemName: "chevron.left").foregroundStyle(Color.black).fontWeight(.bold)
                    })
                }
                
                ToolbarItem(placement: .principal) {
                    Text(R.string.localizable.conditionsScreenTitle)
                        .font(.header1)
                        .foregroundColor(Color.black)
                }
            }
        }
    }
}

#Preview {
    ConditionScreenView()
}
