import SwiftUI
import shared

struct SignSuggestView: View {
    @State private var isButtonEnabled: Bool = true
    @EnvironmentObject var appState: AppState

    var body: some View {
        NavigationView {
            VStack(spacing: SignSuggestConfig.spacingBetweenComponents) {
                HStack {
                    Spacer()
                    NavigationLink(destination: MainTabbarView()) {
                        Image.xmark
                            .resizable()
                            .foregroundColor(Color.black)
                            .frame(width: SignSuggestConfig.xmarkSize, height: SignSuggestConfig.xmarkSize)
                    }
                    .padding(.top, SignSuggestConfig.xmarkTopPadding)
                }

                Image(R.image.person)
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(maxWidth: SignSuggestConfig.imageSize)
                    .padding(.top, SignSuggestConfig.topSpacingImage)

                VStack(spacing: SignSuggestConfig.spacingBetweenTitleAndText) {
                    Text(R.string.localizable.signSuggestTitle)
                        .font(.header1)
                    Text(R.string.localizable.signSuggestText)
                        .font(.paragraph4)
                }
                .padding(SignSuggestConfig.verticalPaddingTextBlock)
                .multilineTextAlignment(.center)
                
                NavigationLink(destination: RegistrationScreenView()) {
                    Text(R.string.localizable.regButtonTitle)
                }.buttonStyle(FilledBtnStyle())
                
                NavigationLink(destination: AuthScreenView()) {
                    Text(R.string.localizable.authButtonTitle)
                }.buttonStyle(BorderedBtnStyle())
                
                Spacer()
            }
            .padding(.horizontal, horizontalPadding)
            .fullScreenCover(isPresented: $appState.isInternetErrorVisible) {
                InternetConnectErrorView()
            }
            .onAppear {
                appState.checkConnectivity()
            }
        }
    }
}

#Preview {
    SignSuggestView()
}