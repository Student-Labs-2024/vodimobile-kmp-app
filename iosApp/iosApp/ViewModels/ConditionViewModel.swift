//
//  ConditionViewModel.swift
//  iosApp
//
//  Created by Sergey Ivanov on 18.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation


final class ConditionViewModel: ObservableObject {
    @Published var conditionText: String = ""
    
    init() {
        loadUserAgreement()
    }
    
    func loadUserAgreement() {
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
