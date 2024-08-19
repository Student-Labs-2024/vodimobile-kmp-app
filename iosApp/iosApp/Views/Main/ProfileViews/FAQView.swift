//
//  FAQScreen.swift
//  iosApp
//
//  Created by Sergey Ivanov on 12.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct FAQScreenView: View {
    @StateObject private var viewModel: FAQViewModel = FAQViewModel()
    
    var attributedTitle: AttributedString {
        var attributedString = AttributedString(R.string.localizable.questionAndAnswerTitle())
        attributedString.font = .header3
        
        if let range = attributedString.range(of: R.string.localizable.questionAndAnswerAttributedPartOfTitle()) {
            attributedString[range].foregroundColor = Color(R.color.blueColor)
        }
        
        return attributedString
    }
    
    var body: some View {
        VStack {
            ScrollView(.vertical, showsIndicators: false) {
                VStack(alignment: .leading, spacing: 24) {
                    Text(attributedTitle)
                        .font(.header3)
                    Text(R.string.localizable.questionAndAnswerText)
                        .font(.paragraph4)
                        .foregroundStyle(Color(R.color.grayTextColor))
                }
                .frame(maxWidth: .infinity)
                .padding(.vertical, 25)
                .background(Color(R.color.blueBoxColor))
                .padding(.top, 10)
                
                LazyVStack(alignment: .leading) {
                    DisclosureListView(expandedIndices: $viewModel.expandedIndices, listOfQuestions: viewModel.listOfQuestions)
                }
                .padding(.horizontal, 40)
            }
        }
        .navigationBarBackButtonHidden()
        .toolbar {
            CustomToolbar(title: R.string.localizable.faqScreenTitle)
        }
    }
}

#Preview {
    FAQScreenView()
}

struct DisclosureListView: View {
    @Binding private var expandedIndices: [Bool]
    let listOfQuestions: [Question]
    
    init(expandedIndices: Binding<[Bool]>, listOfQuestions: [Question]) {
        self._expandedIndices = expandedIndices
        self.listOfQuestions = listOfQuestions
    }
    
    var body: some View {
        ForEach(Array(listOfQuestions.enumerated()), id: \.element.id) { index, _ in
            DisclosureGroup(
                isExpanded: $expandedIndices[index],
                content: {
                    Text(listOfQuestions[index].body)
                        .font(.paragraph5)
                        .multilineTextAlignment(.leading)
                        .padding(24)
                        .frame(maxWidth: .infinity)
                        .background(RoundedRectangle(cornerRadius: 10).fill(Color(R.color.grayLightColor)))
                },
                label: {
                    Text(listOfQuestions[index].title)
                        .font(.header3)
                        .foregroundStyle(Color.black)
                        .multilineTextAlignment(.leading)
                }
            )
            .tint(Color.black)
            .padding(.top, 25)
            .padding(.bottom, 5)
            .onChange(of: expandedIndices[index]) { newValue in
                withAnimation {
                    if newValue {
                        for i in 0..<expandedIndices.count where i != index {
                            expandedIndices[i] = false
                        }
                    }
                }
            }
            
            if index < expandedIndices.count - 1 {
                Divider()
            }
        }
    }
}
