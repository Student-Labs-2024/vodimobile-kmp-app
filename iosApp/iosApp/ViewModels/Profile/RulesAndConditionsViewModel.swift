//
//  RulesAndConditionsViewModel.swift
//  iosApp
//
//  Created by Sergey Ivanov on 16.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

final class RulesAndConditionsViewModel {
    let listOfRules: [Rule] = Rule.mockRulesData
}

// Model
struct Rule: Hashable {
    let title: String
    var attributedPartOfTitle: String
    lazy var attributedTitle: AttributedString = {
        var attributedString = AttributedString(title)
        attributedString.font = .header3

        if let range = attributedString.range(of: attributedPartOfTitle) {
            attributedString[range].foregroundColor = Color(R.color.blueColor)
        }

        return attributedString
    }()
    let ruleTitle: String
    let ruleBody: String

    static let mockRulesData = [
        Rule(
            title: R.string.localizable.ruleTitle1(),
            attributedPartOfTitle: R.string.localizable.attributedPartOfTitle1(),
            ruleTitle: R.string.localizable.ruleSubtitle1(),
            ruleBody: R.string.localizable.ruleBody1()
        ),

        Rule(
            title: R.string.localizable.ruleTitle2(),
            attributedPartOfTitle: R.string.localizable.attributedPartOfTitle2(),
            ruleTitle: R.string.localizable.ruleSubtitle2(),
            ruleBody: R.string.localizable.ruleBody2()
        ),

        Rule(
            title: R.string.localizable.ruleTitle3(),
            attributedPartOfTitle: R.string.localizable.attributedPartOfTitle3(),
            ruleTitle: R.string.localizable.ruleSubtitle3(),
            ruleBody: R.string.localizable.ruleBody3()
        ),

        Rule(
            title: R.string.localizable.ruleTitle4(),
            attributedPartOfTitle: R.string.localizable.attributedPartOfTitle4(),
            ruleTitle: R.string.localizable.ruleSubtitle4(),
            ruleBody: R.string.localizable.ruleBody4()
        ),

        Rule(
            title: R.string.localizable.ruleTitle5(),
            attributedPartOfTitle: R.string.localizable.attributedPartOfTitle5(),
            ruleTitle: R.string.localizable.ruleSubtitle5(),
            ruleBody: R.string.localizable.ruleBody5()
        ),

        Rule(
            title: R.string.localizable.ruleTitle6(),
            attributedPartOfTitle: R.string.localizable.attributedPartOfTitle6(),
            ruleTitle: R.string.localizable.ruleSubtitle6(),
            ruleBody: R.string.localizable.ruleBody6()
        ),

        Rule(
            title: R.string.localizable.ruleTitle7(),
            attributedPartOfTitle: R.string.localizable.attributedPartOfTitle7(),
            ruleTitle: R.string.localizable.ruleSubtitle7(),
            ruleBody: R.string.localizable.ruleBody7()
        ),

        Rule(
            title: R.string.localizable.ruleTitle8(),
            attributedPartOfTitle: R.string.localizable.attributedPartOfTitle8(),
            ruleTitle: R.string.localizable.ruleSubtitle8(),
            ruleBody: R.string.localizable.ruleBody8()
        ),

        Rule(
            title: R.string.localizable.ruleTitle9(),
            attributedPartOfTitle: R.string.localizable.attributedPartOfTitle9(),
            ruleTitle: R.string.localizable.ruleSubtitle9(),
            ruleBody: R.string.localizable.ruleBody9()
        ),

        Rule(
            title: R.string.localizable.ruleTitle10(),
            attributedPartOfTitle: R.string.localizable.attributedPartOfTitle10(),
            ruleTitle: R.string.localizable.ruleSubtitle10(),
            ruleBody: R.string.localizable.ruleBody10()
        ),

        Rule(
            title: R.string.localizable.ruleTitle11(),
            attributedPartOfTitle: R.string.localizable.attributedPartOfTitle11(),
            ruleTitle: R.string.localizable.ruleSubtitle11(),
            ruleBody: R.string.localizable.ruleBody11()
        ),

        Rule(
            title: R.string.localizable.ruleTitle12(),
            attributedPartOfTitle: R.string.localizable.attributedPartOfTitle12(),
            ruleTitle: R.string.localizable.ruleSubtitle12(),
            ruleBody: R.string.localizable.ruleBody12()
        ),

        Rule(
            title: R.string.localizable.ruleTitle13(),
            attributedPartOfTitle: R.string.localizable.attributedPartOfTitle13(),
            ruleTitle: R.string.localizable.ruleSubtitle13(),
            ruleBody: R.string.localizable.ruleBody13()
        ),

        Rule(
            title: R.string.localizable.ruleTitle14(),
            attributedPartOfTitle: R.string.localizable.attributedPartOfTitle14(),
            ruleTitle: R.string.localizable.ruleSubtitle14(),
            ruleBody: R.string.localizable.ruleBody14()
        ),

        Rule(
            title: R.string.localizable.ruleTitle15(),
            attributedPartOfTitle: R.string.localizable.attributedPartOfTitle15(),
            ruleTitle: R.string.localizable.ruleSubtitle15(),
            ruleBody: R.string.localizable.ruleBody15()
        ),

        Rule(
            title: R.string.localizable.ruleTitle16(),
            attributedPartOfTitle: R.string.localizable.attributedPartOfTitle16(),
            ruleTitle: R.string.localizable.ruleSubtitle16(),
            ruleBody: R.string.localizable.ruleBody16()
        )
    ]
}
