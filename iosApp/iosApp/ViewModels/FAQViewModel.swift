//
//  FAQViewModel.swift
//  iosApp
//
//  Created by Sergey Ivanov on 16.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

final class FAQViewModel: ObservableObject {
    @Published var listOfQuestions: [Question]
    @Published var expandedIndices: [Bool]
    
    init() {
        self.listOfQuestions = Question.mockQuestionsData
        self.expandedIndices = Array(repeating: false, count: Question.mockQuestionsData.count)
    }
}

// Model
struct Question: Identifiable {
    let id: Int
    var title: String
    var body: String
    
    static let mockQuestionsData = [
        Question(
            id: 1,
            title: R.string.localizable.questionTitle1(),
            body: R.string.localizable.questionBody1()
        ),
        
        Question(
            id: 2,
            title: R.string.localizable.questionTitle2(),
            body: R.string.localizable.questionBody2()
        ),
        
        Question(
            id: 3,
            title: R.string.localizable.questionTitle3(),
            body: R.string.localizable.questionBody3()
        ),
        
        Question(
            id: 4,
            title: R.string.localizable.questionTitle4(),
            body: R.string.localizable.questionBody4()
        ),
        
        Question(
            id: 5,
            title: R.string.localizable.questionTitle5(),
            body: R.string.localizable.questionBody5()
        ),
        
        Question(
            id: 6,
            title: R.string.localizable.questionTitle6(),
            body: R.string.localizable.questionBody6()
        ),
        Question(
            id: 7,
            title: R.string.localizable.questionTitle7(),
            body: R.string.localizable.questionBody7()
        ),
        
        Question(
            id: 8,
            title: R.string.localizable.questionTitle8(),
            body: R.string.localizable.questionBody8()
        ),
        
        Question(
            id: 9,
            title: R.string.localizable.questionTitle9(),
            body: R.string.localizable.questionBody9()
        ),
        
        Question(
            id: 10,
            title: R.string.localizable.questionTitle10(),
            body: R.string.localizable.questionBody10()
        ),
        
        Question(
            id: 11,
            title: R.string.localizable.questionTitle11(),
            body: R.string.localizable.questionBody11()
        ),
        Question(
            id: 12,
            title: R.string.localizable.questionTitle12(),
            body: R.string.localizable.questionBody12()
        ),
        
        Question(
            id: 13,
            title: R.string.localizable.questionTitle13(),
            body: R.string.localizable.questionBody13()
        ),
        
        Question(
            id: 14,
            title: R.string.localizable.questionTitle14(),
            body: R.string.localizable.questionBody14()
        ),
        
        Question(
            id: 15,
            title: R.string.localizable.questionTitle15(),
            body: R.string.localizable.questionBody15()
        ),
        
        Question(
            id: 16,
            title: R.string.localizable.questionTitle16(),
            body: R.string.localizable.questionBody16()
        ),
        Question(
            id: 17,
            title: R.string.localizable.questionTitle17(),
            body: R.string.localizable.questionBody17()
        ),
        
        Question(
            id: 18,
            title: R.string.localizable.questionTitle18(),
            body: R.string.localizable.questionBody18()
        ),
        
        Question(
            id: 19,
            title: R.string.localizable.questionTitle19(),
            body: R.string.localizable.questionBody19()
        ),
        
        Question(
            id: 20,
            title: R.string.localizable.questionTitle20(),
            body: R.string.localizable.questionBody20()
        )
    ]
}
