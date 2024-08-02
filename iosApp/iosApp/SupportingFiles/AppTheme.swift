//
//  AppTheme.swift
//  iosApp
//
//  Created by Sergey Ivanov on 09.07.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import UIKit
import SwiftUI
import shared

extension Image {
    static let xmark: Image = Image(systemName: "xmark")
    static let checkmark: Image = Image(systemName: "checkmark")
    static let chevronLeft: Image = Image(systemName: "chevron.left")
    static let chevronRight: Image = Image(systemName: "chevron.right")
    static let docText: Image = Image(systemName: "doc.text")
    static let infoCircle: Image = Image(systemName: "info.circle")
    static let letter: Image = Image(systemName: "envelope")
    static let person: Image = Image(systemName: "person")
    static let personFill: Image = Image(systemName: "person.fill")
    static let rightArrowCircleFill: Image = Image(systemName: "arrow.forward.circle.fill")
    static let bell: Image = Image(systemName: "bell")
    static let minus: Image = Image(systemName: "minus")
    static let infoCircleFill: Image = Image(systemName: "info.circle.fill")
    static let questionFolder: Image = Image(systemName: "questionmark.folder")
    static let car: Image = Image(systemName: "car")
    static let eye: Image = Image(systemName: "eye")
    static let eyeSlash: Image = Image(systemName: "eye.slash")
}

extension Image {
    init(resource: KeyPath<SharedRes.images, shared.ImageResource>) {
        self.init(uiImage: SharedRes.images()[keyPath: resource].toUIImage()!)
    }
}

extension StringResource {
    var resource: String {
        self.desc().localized()
    }
}

extension Font {
    // header text
    public static let header1: Font = Font(R.font.akzidenzGroteskProMdEx(size: 18) ?? .systemFont(ofSize: 18))
    public static let header2: Font = Font(R.font.akzidenzGroteskProRegular(size: 20) ?? .systemFont(ofSize: 20))
    public static let header3: Font = Font(R.font.akzidenzGroteskProMdEx(size: 16) ?? .systemFont(ofSize: 16))
    public static let header4: Font = Font(R.font.akzidenzGroteskProExt(size: 15) ?? .systemFont(ofSize: 15))
    public static let header5: Font = Font(R.font.akzidenzGroteskProExt(size: 14) ?? .systemFont(ofSize: 14))
    // button text
    public static let button: Font = Font(R.font.akzidenzGroteskProMdEx(size: 16) ?? .systemFont(ofSize: 16))
    public static let tag: Font = Font(R.font.akzidenzGroteskProMdEx(size: 14) ?? .systemFont(ofSize: 14))
    public static let buttonText: Font = Font(R.font.akzidenzGroteskProLight(size: 14) ?? .systemFont(ofSize: 14))
    public static let buttonCheckBox: Font = Font(R.font.akzidenzGroteskProLight(size: 14) ?? .systemFont(ofSize: 14))
    public static let buttonTabbar: Font = Font(R.font.akzidenzGroteskProMd(size: 12) ?? .systemFont(ofSize: 12))

    public static let caption2: Font = Font(R.font.akzidenzGroteskProMd(size: 12) ?? .systemFont(ofSize: 12))
    public static let caption1: Font = Font(R.font.akzidenzGroteskProMd(size: 12) ?? .systemFont(ofSize: 12))

    // paragraph text
    public static let paragraph1: Font = Font(R.font.akzidenzGroteskProMd(size: 18) ?? .systemFont(ofSize: 18))
    public static let paragraph2: Font = Font(R.font.akzidenzGroteskProRegular(size: 16) ?? .systemFont(ofSize: 16))
    public static let paragraph3: Font = Font(R.font.akzidenzGroteskProLight(size: 14) ?? .systemFont(ofSize: 14))
    public static let paragraph5: Font = Font(R.font.akzidenzGroteskProRegular(size: 14) ?? .systemFont(ofSize: 14))
    public static let paragraph4: Font = Font(R.font.akzidenzGroteskProLight(size: 14) ?? .systemFont(ofSize: 14))
    public static let paragraph6: Font = Font(R.font.akzidenzGroteskProLight(size: 12) ?? .systemFont(ofSize: 12))
}

struct FilledBtnStyle: ButtonStyle {
    let heightButton: CGFloat?

    init(heightButton: CGFloat? = nil) {
        self.heightButton = heightButton
    }

    public func makeBody(configuration: ButtonStyle.Configuration) -> some View {
        FilledButton(configuration: configuration, height: heightButton)
    }
}

struct BorderedBtnStyle: ButtonStyle {
    func makeBody(configuration: Configuration) -> some View {
        configuration.label
            .padding()
            .frame(maxWidth: .infinity)
            .background(configuration.isPressed ? Color(uiColor: R.color.blueColor() ?? .clear): .white)
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
    private var textIsNotValid: Bool {
        !isValid && !text.isEmpty
    }
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
                    .stroke(
                        textIsNotValid ? Color(R.color.redColor) : Color(R.color.grayDarkColor),
                        lineWidth: isFocused || textIsNotValid ? 1 : 0
                    )
            )
    }
}

struct BorderedDateTextFieldStyle: TextFieldStyle {
    func _body(configuration: TextField<_Label>) -> some View {
        configuration
            .frame(alignment: .leading)
            .font(.paragraph2)
            .padding(.vertical, 16)
            .padding(.horizontal, 56)
            .foregroundStyle(.black)
            .multilineTextAlignment(.leading)
            .cornerRadius(10)
            .overlay(
                RoundedRectangle(cornerRadius: 10)
                    .stroke(Color(R.color.grayDarkColor), lineWidth: 1)
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
