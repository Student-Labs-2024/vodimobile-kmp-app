//
//  ModalView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 16.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct DetailRuleView: View {
    var viewModel: DetailRuleViewModel

    init(rule: Rule) {
        self.viewModel = DetailRuleViewModel(rule: rule)
    }

    var body: some View {
        ScrollView(.vertical, showsIndicators: false) {
            VStack(alignment: .leading, spacing: 24) {
                Text(viewModel.rule.attributedTitle)
                    .multilineTextAlignment(.leading)

                VStack(alignment: .leading, spacing: 24) {
                    Text(viewModel.rule.ruleTitle).font(.paragraph2)
                    Text(viewModel.rule.ruleBody).font(.paragraph4)
                }
                .frame(maxWidth: .infinity)
                .padding(.vertical, 25)
                .padding(.horizontal, 28)
                .background(RoundedRectangle(cornerRadius: 30, style: .circular).fill(Color(R.color.blueBox)))

                Spacer()
            }
            .padding(.top, 40)
        }
        .padding(.horizontal, 16)
        .navigationBarBackButtonHidden()
        .toolbar {
            CustomToolbar(title: R.string.localizable.rulesScreenTitle)
        }
    }
}

#Preview {
    DetailRuleView(rule: Rule.mockRulesData[0])
}
