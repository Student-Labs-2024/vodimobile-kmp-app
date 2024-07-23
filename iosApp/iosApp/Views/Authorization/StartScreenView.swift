import SwiftUI
import shared

struct StartScreenView: View {
    @State private var isButtonEnabled: Bool = true
    @ObservedObject private var dataStorage = KMPDataStorage()
    @State private var userData: User = KMPDataStorage.defaultUser
    
    var body: some View {
        NavigationView {
            VStack(spacing: StartScreenConfig.spacingBetweenComponents) {
                HStack {
                    Text(userData.description())
                    Spacer()
                    NavigationLink(destination: MainTabbarView()) {
                        Image.xmark
                            .resizable()
                            .foregroundColor(Color.black)
                            .frame(width: StartScreenConfig.xmarkSize, height: StartScreenConfig.xmarkSize)
                    }
                    .padding(.top, StartScreenConfig.xmarkTopPadding)
                }.task {
                    print(dataStorage.editUserData())
                    userData = dataStorage.getUser()
                }
                
                Image(R.image.logo)
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .padding(.horizontal, StartScreenConfig.logoHorizontalPadding)
                
                NavigationLink(destination: RegistrationScreenView()) {
                    Text(R.string.localizable.regButtonTitle)
                }.buttonStyle(FilledBtnStyle())
                
                NavigationLink(destination: AuthScreenView()) {
                    Text(R.string.localizable.authButtonTitle)
                }.buttonStyle(BorderedBtnStyle())
                
                Spacer()
            }
            .padding(.horizontal, horizontalPadding)
        }
    }
}

#Preview {
    StartScreenView()
}
