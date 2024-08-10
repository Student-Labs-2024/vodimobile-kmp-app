//
//  AutoCardVieModel.swift
//  iosApp
//
//  Created by Sergey Ivanov on 10.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

final class AutoCardViewModel: ObservableObject {
    @Binding var carModel: Car
    var carPreview: Image {
        if let image = carModel.images.first {
            return Image(ImageResource(name: image.assetImageName, bundle: image.bundle))
        } else {
            return Image.questionFolder
        }
    }
    
    init(carModel: Binding<Car>) {
        self._carModel = carModel
    }
}
