//
//  ConditionScreenViewModel.swift
//  iosApp
//
//  Created by Sergey Ivanov on 15.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

final class ConditionScreenViewModel {
    
    func loadUserAgreement(content: Binding<String>) {
        if let filePath = Bundle.main.path(forResource: "terms_and_conditions", ofType: "txt") {
            do {
                let contents = try String(contentsOfFile: filePath)
                content.wrappedValue = contents
            } catch {
                print("Error loading user agreement text")
            }
        }
    }
}
