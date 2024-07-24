//
//  MainView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 11.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct MainView: View {
    var body: some View {
        VStack {
            Spacer()
            Text("Hello, MainView!")
                .frame(maxWidth: .infinity)
            Spacer()
        }
        .background(Color(R.color.grayLightColor))
    }
}

#Preview {
    MainView()
}
