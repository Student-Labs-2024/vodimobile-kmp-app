import SwiftUI
import shared

@MainActor
final class KMPDataStorage: ObservableObject {
    private let repository = UserDataStoreRepositoryImpl(dataStore: CreateDataStore_iosKt.createDataStore())
    private let newUser = User(
        fullName: "test testov",
        password: "12344321",
        token: "token_test",
        phone: "+79029994148",
        email: "rele@df.df"
    )
    static let defaultUser = User.companion.empty()

    func editUserData() {
        Task {
            try await repository.editUserData(user: newUser)
        }
    }
    
    func getUser() -> User {
        var user: User = KMPDataStorage.defaultUser
        Task {
            user = try await repository.getUserData()
        }
        return user
    }
}

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
