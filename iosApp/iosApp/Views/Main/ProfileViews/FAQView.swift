//
//  FAQScreen.swift
//  iosApp
//
//  Created by Sergey Ivanov on 12.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct FAQScreenView: View {
    var body: some View {
        VStack {
            Spacer()
            Text("Hello, FAQScreen!")
                .frame(maxWidth: .infinity)
            Spacer()
        }
        .background(Color.grayLightColor.ignoresSafeArea())
        .navigationBarBackButtonHidden()
        .toolbar {
            CustomToolbar(title: String.ScreenTitles.faqScreenTitle)
        }
    }
}

#Preview {
    FAQScreenView()
}
