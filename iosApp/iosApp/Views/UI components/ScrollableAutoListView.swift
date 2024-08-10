//
//  ScrollableAutoListView.swift
//  iosApp
//
//  Created by Sergey Ivanov on 07.08.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared
import SwiftUIPullToRefresh

struct ScrollableAutoListView: View {
    @Binding var carList: [Car]
    @Binding var selectedAuto: Car
    @Binding var showModalCard: Bool
    @Binding var showModalReservation: Bool
    let refreshAction: () async -> ()
    
    var body: some View {
        RefreshableScrollView(loadingViewBackgroundColor: Color(R.color.grayLightColor), action: {
            await refreshAction()
        }, progress: { state in
            RefreshActivityIndicator(isAnimating: state == .loading) {
                $0.hidesWhenStopped = false
            }
        }) {
            LazyVStack(spacing: 20) {
                ForEach(carList.indices, id: \.self) { index in
                    AutoCardWithButtonView(
                        carModel: $carList[index],
                        selectedAuto: $selectedAuto,
                        showModal: $showModalCard,
                        showModalReservation: $showModalReservation
                    )
                }
            }
            .padding(.horizontal, horizontalPadding)
            .padding(.vertical, 24)
        }
    }
}
