//
//  FAQViewModel.swift
//  iosApp
//
//  Created by Sergey Ivanov on 16.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

final class FAQViewModel {
    let listOfQuestions: [Question] = Question.mockQuestionsData
}

// Model
struct Question: Hashable {
    let title: String
    let body: String
    
    static let mockQuestionsData = [
        Question(
            title: R.string.localizable.questionTitle1(),
            body: R.string.localizable.questionBody1()
        ),
        Question(
            title: R.string.localizable.questionTitle2(),
            body: R.string.localizable.questionBody2()
        ),
        
        Question(
            title: R.string.localizable.questionTitle3(),
            body: R.string.localizable.questionBody3()
        ),
        
        Question(
            title: R.string.localizable.questionTitle4(),
            body: R.string.localizable.questionBody4()
        ),
        
        Question(
            title: R.string.localizable.questionTitle5(),
            body: R.string.localizable.questionBody5()
        ),
        
        Question(
            title: R.string.localizable.questionTitle6(),
            body: R.string.localizable.questionBody6()
        ),
        Question(
            title: R.string.localizable.questionTitle7(),
            body: R.string.localizable.questionBody7()
        ),
        
        Question(
            title: R.string.localizable.questionTitle8(),
            body: R.string.localizable.questionBody8()
        ),
        
        Question(
            title: R.string.localizable.questionTitle9(),
            body: R.string.localizable.questionBody9()
        ),
        
        Question(
            title: R.string.localizable.questionTitle10(),
            body: R.string.localizable.questionBody10()
        ),
        
        Question(
            title: R.string.localizable.questionTitle11(),
            body: R.string.localizable.questionBody11()
        ),
        Question(
            title: R.string.localizable.questionTitle12(),
            body: R.string.localizable.questionBody12()
        ),
        
        Question(
            title: R.string.localizable.questionTitle13(),
            body: R.string.localizable.questionBody13()
        ),
        
        Question(
            title: R.string.localizable.questionTitle14(),
            body: R.string.localizable.questionBody14()
        ),
        
        Question(
            title: R.string.localizable.questionTitle15(),
            body: R.string.localizable.questionBody15()
        ),
        
        Question(
            title: R.string.localizable.questionTitle16(),
            body: R.string.localizable.questionBody16()
        ),
        Question(
            title: R.string.localizable.questionTitle17(),
            body: R.string.localizable.questionBody17()
        ),
        
        Question(
            title: R.string.localizable.questionTitle18(),
            body: R.string.localizable.questionBody18()
        ),
        
        Question(
            title: R.string.localizable.questionTitle19(),
            body: R.string.localizable.questionBody19()
        ),
        
        Question(
            title: R.string.localizable.questionTitle20(),
            body: R.string.localizable.questionBody20()
        )
    ]
}
