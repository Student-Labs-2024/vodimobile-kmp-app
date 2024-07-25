//
//  AutoListView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 24.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct AutoListView: View {
    var body: some View {
        VStack {
            Spacer()
            Text("Hello, AutoListView!")
                .frame(maxWidth: .infinity)
            Spacer()
        }
        .background(Color(R.color.grayLightColor))
        .navigationBarBackButtonHidden()
        .toolbar {
            CustomToolbar(title: R.string.localizable.carParkScreenTitle)
        }
    }
}

#Preview {
    AutoListView()
}
