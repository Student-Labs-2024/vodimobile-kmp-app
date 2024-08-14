import SwiftUI
import shared

struct SignSuggestView: View {
    @State private var isButtonEnabled: Bool = true
    @Binding var showSignSuggestModal: Bool
    @EnvironmentObject var appState: AppState
    private var authManager = AuthManager.shared
    
    init(showSignSuggestModal: Binding<Bool>) {
        self._showSignSuggestModal = showSignSuggestModal
    }

    var body: some View {
        NavigationView {
            VStack(spacing: SignSuggestConfig.spacingBetweenComponents) {
                HStack {
                    Spacer()
                    Button {
                        showSignSuggestModal.toggle()
                    } label: {
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
                
                NavigationLink(destination: RegistrationScreenView(showSignSuggestModal: $showSignSuggestModal)) {
                    Text(R.string.localizable.regButtonTitle)
                }.buttonStyle(FilledBtnStyle())
                
                NavigationLink(destination: AuthScreenView(showSignSuggestModal: $showSignSuggestModal)) {
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
    SignSuggestView(showSignSuggestModal: Binding.constant(true))
}
