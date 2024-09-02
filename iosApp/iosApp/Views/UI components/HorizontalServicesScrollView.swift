//
//  ServicesHorizontalScrollView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 28.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct HorizontalServicesScrollView: View {
    @Binding var servicesList: [ServicesDTO]
    @Binding var selectedServicesList: [ServicesDTO]

    var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            LazyHStack(spacing: 16) {
                ForEach(servicesList, id: \.service_id) { service in
                    AdditionalServiceCell(
                        service: service,
                        selectedServicesList: $selectedServicesList
                    )
                }
            }
        }
    }
}

struct AdditionalServiceCell: View {
    let service: ServicesDTO
    @State var isSelected: Bool = false
    @Binding var selectedServicesList: [ServicesDTO]

    var body: some View {
        Button(action: {
            if !selectedServicesList.contains(where: { $0 == service }) {
                isSelected = true
                selectedServicesList.append(service)
            } else {
                isSelected = false
                if let selectedServiceIndex = selectedServicesList.firstIndex(of: service) {
                    selectedServicesList.remove(at: selectedServiceIndex)
                }
            }
        }, label: {
            if isSelected {
                VStack(spacing: 5) {
                    Text(service.title)
                        .font(.buttonTabbar)
                        .foregroundStyle(.white)
                    Text("+\(Int(service.cost)) \(R.string.localizable.currencyText())")
                        .font(.buttonTabbar)
                        .foregroundStyle(.white)
                }
                .padding(.horizontal, 24)
                .padding(.vertical, 8)
                .background(RoundedRectangle(cornerRadius: 10)
                    .fill(Color(R.color.blueColor)))
            } else {
                VStack(spacing: 5) {
                    Text(service.title)
                        .font(.buttonTabbar)
                        .foregroundStyle(Color(R.color.text))
                    Text("+\(Int(service.cost)) \(R.string.localizable.currencyText())")
                        .font(.buttonTabbar)
                        .foregroundStyle(Color(R.color.blueColor))
                }
                .padding(.horizontal, 24)
                .padding(.vertical, 8)
                .background(RoundedRectangle(cornerRadius: 10)
                    .fill(Color(R.color.blueBox)))
            }
        })
    }
}
