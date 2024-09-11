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
    @ObservedObject private var viewModel: ConditionViewModel

    init(viewModel: ConditionViewModel = .init()) {
        self.viewModel = viewModel
    }

    var body: some View {
        ScrollView(.vertical, showsIndicators: false) {
            Text(viewModel.conditionText)
                .font(.paragraph2)
                .foregroundStyle(Color(R.color.text))
                .frame(maxWidth: .infinity)
                .multilineTextAlignment(.leading)
        }
        .padding([.leading, .bottom, .trailing], 24)
        .navigationBarBackButtonHidden()
        .toolbar {
            CustomToolbar(title: R.string.localizable.conditionsScreenTitle)
        }
    }
}

#Preview {
    ConditionScreenView(viewModel: .init())
}
