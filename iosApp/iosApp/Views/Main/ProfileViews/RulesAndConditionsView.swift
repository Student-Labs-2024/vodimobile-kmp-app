//
//  RulesAndConditionsView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 12.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct RulesAndConditionsView: View {
    private var viewModel: RulesAndConditionsViewModel

    init() {
        self.viewModel = RulesAndConditionsViewModel()
    }

    var body: some View {
        VStack {
            List(viewModel.listOfRules, id: \.self) { rule in
                NavigationLink(destination: DetailRuleView(rule: rule), label: {
                    HStack {
                        Text(rule.title)
                            .font(.paragraph2)
                            .foregroundStyle(Color(R.color.text))
                    }
                })
                .listRowSeparator(.hidden)
                .listRowInsets(.init(top: 25, leading: 40, bottom: 25, trailing: 40))
            }
            .listStyle(.plain)
        }
        .padding(.vertical, 15)
//        .background(Color.white.ignoresSafeArea())
        .navigationBarBackButtonHidden()
        .toolbar {
            CustomToolbar(title: R.string.localizable.rulesScreenTitle)
        }
    }
}

#Preview {
    RulesAndConditionsView()
}
