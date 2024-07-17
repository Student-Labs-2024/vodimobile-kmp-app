//
//  AppTheme.swift
//  iosApp
//
//  Created by Sergey Ivanov on 09.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import UIKit
import SwiftUI
import RswiftResources

extension Font {
    public static let header1: Font = .custom("AkzidenzGroteskPro-MdEx", size: 18)
    public static let header2: Font = .custom("AkzidenzGroteskPro-Regular", size: 20)
    public static let header3: Font = .custom("AkzidenzGroteskPro-MdEx", size: 16)
    public static let header4: Font = .custom("AkzidenzGroteskPro-Ext", size: 15)
    
    public static let button: Font = .custom("AkzidenzGroteskPro-MdEx", size: 16)
    public static let buttonText: Font = .custom("AkzidenzGroteskPro-Light", size: 14)
    public static let buttonCheckBox: Font = .custom("AkzidenzGroteskPro-Light", size: 14)
    public static let buttonTabbar: Font = .custom("AkzidenzGroteskPro-Md", size: 12)
    
    public static let paragraph1: Font = .custom("AkzidenzGroteskPro-Md", size: 18)
    public static let paragraph2: Font = .custom("AkzidenzGroteskPro-Regular", size: 16)
    public static let paragraph3: Font = .custom("AkzidenzGroteskPro-Light", size: 14)
    public static let paragraph4: Font = .custom("AkzidenzGroteskPro-Regular", size: 14)
    public static let paragraph5: Font = .custom("AkzidenzGroteskPro-Light", size: 14)
    public static let paragraph6: Font = .custom("AkzidenzGroteskPro-Light", size: 12)
}

struct FilledBtnStyle: ButtonStyle {

    public func makeBody(configuration: ButtonStyle.Configuration) -> some View {
        FilledButton(configuration: configuration)
    }
}

struct BorderedBtnStyle: ButtonStyle {
    
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .padding()
            .frame(maxWidth: .infinity)
            .background(configuration.isPressed ? Color(uiColor: R.color.blueColor.callAsFunction() ?? .clear): .white)
            .foregroundColor(configuration.isPressed ? .white : Color(R.color.blueColor))
            .overlay(
                RoundedRectangle(cornerRadius: 15)
                    .stroke(Color(R.color.blueColor), lineWidth: configuration.isPressed ? 0 : 2)
            )
            .border(Color(R.color.blueColor), width: configuration.isPressed ? 0 : 2)
            .cornerRadius(15)
            .font(.button)
    }
}

struct BorderedTextFieldStyle: TextFieldStyle {
    var text: String
    var isFocused: Bool
    var isValid: Bool
    
    func _body(configuration: TextField<_Label>) -> some View {
        configuration
            .frame(alignment: .leading)
            .font(.paragraph4)
            .padding(16)
            .foregroundStyle(Color.black)
            .multilineTextAlignment(.leading)
            .background(Color(R.color.grayLightColor))
            .cornerRadius(12)
            .overlay(
                RoundedRectangle(cornerRadius: 12)
                    .stroke(!isValid && !text.isEmpty ? Color(R.color.redColor) : Color(R.color.grayDarkColor), lineWidth: isFocused || (!isValid && !text.isEmpty) ? 1 : 0)
            )
    }
}

struct CustomTextFieldStyle: TextFieldStyle {
    var text: String
    var isFocused: Bool
    var isValid: Bool
    
    func _body(configuration: TextField<_Label>) -> some View {
        configuration
            .frame(alignment: .leading)
            .font(.paragraph4)
            .padding(16)
            .foregroundStyle(Color.black)
            .multilineTextAlignment(.leading)
            .background(Color(R.color.grayLightColor))
            .cornerRadius(12)
            .overlay(
                RoundedRectangle(cornerRadius: 12)
                    .stroke(!isValid && !text.isEmpty ? Color(R.color.redColor) : Color(R.color.grayDarkColor), lineWidth: isFocused || (!isValid && !text.isEmpty) ? 1 : 0)
            )
    }
}

extension UIColor {
    
    // Definision color by hex format
    convenience init(red: Int, green: Int, blue: Int) {
        assert(red >= 0 && red <= 255, "Invalid red component")
        assert(green >= 0 && green <= 255, "Invalid green component")
        assert(blue >= 0 && blue <= 255, "Invalid blue component")
        self.init(red: CGFloat(red) / 255.0, green: CGFloat(green) / 255.0, blue: CGFloat(blue) / 255.0, alpha: 1.0)
    }
    convenience init(rgb: Int) {
        self.init(
            red: (rgb >> 16) & 0xFF,
            green: (rgb >> 8) & 0xFF,
            blue: rgb & 0xFF
        )
    }
}
