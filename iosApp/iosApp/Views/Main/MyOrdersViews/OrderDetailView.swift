//
//  OrderDetailView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 17.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct OrderDetailView: View {
    @Binding private var showOrderModal: Bool
    @ObservedObject var viewModel: OrderDetailViewModel
    
    init(
        order: Order,
        showOrderModal: Binding<Bool>? = nil
    ) {
        self.viewModel = .init(order: order)
        self._showOrderModal = showOrderModal ?? Binding.constant(false)
    }
    
    var body: some View {
        ZStack(alignment: .top) {
            VStack {
                HStack {
                    Button(action: {
                        showOrderModal.toggle()
                    }, label: {
                        Image.chevronLeft.foregroundStyle(Color.black).fontWeight(.bold)
                    })
                    Text(R.string.localizable.aboutOrder)
                        .font(.header1)
                        .foregroundColor(Color.black)
                        .frame(maxWidth: .infinity)
                }
                ScrollView(.vertical, showsIndicators: false) {
                    VStack(alignment: .leading, spacing: 24) {
                        HStack {
                            viewModel.orderCarPreview
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .frame(maxWidth: screenWidth / 2.3)
                            
                            Spacer()
                            
                            VStack(alignment: .leading, spacing: 12) {
                                VStack(alignment: .leading) {
                                    Text(R.string.localizable.autoNameTitle)
                                        .font(.paragraph5)
                                        .foregroundStyle(Color(R.color.grayText))
                                    Text(viewModel.order.car.model.resource)
                                        .font(.header5)
                                }
                            }
                            .multilineTextAlignment(.leading)
                        }
                        .padding(.horizontal, horizontalPadding)
                        .padding(.vertical, 24)
                        .background(
                            RoundedRectangle(cornerRadius: 16)
                                .fill(Color(R.color.blueBox))
                        )
                        
                    }
                }
                
            }
            .padding(.horizontal, horizontalPadding)        }
        .navigationBarBackButtonHidden()
    }
}

#Preview {
    OrderDetailView(order: Order.companion.empty())
}
