import SwiftUI
import shared

final class KMPDataStorageWork {
    let testUser = User(
        fullName: "test testov",
        password: "12344321",
        token: "",
        phone: "+79029994147",
        email: "rekeylg@re.rt"
    )
    let dataStorage = DataStoreHelper().userDataStoreStorage
    
    func editUserData() async throws -> Bool {
        do {
            try await dataStorage.edit(user: testUser)
            return true
        } catch {
            return false
        }
    }
    
    func getUser() async throws -> Kotlinx_coroutines_coreFlow? {
        do {
            let gettingUser = try await dataStorage.getUser()
            return gettingUser
        } catch {
            return nil
        }
    }
}

struct StartScreenView: View {
    @State private var isButtonEnabled: Bool = true
    @State private var dataStore = KMPDataStorageWork()
    
    var body: some View {
        NavigationView {
            VStack(spacing: StartScreenConfig.spacingBetweenComponents) {
                HStack {
                    Spacer()
                    Button(action: {
                        print("Button has been pressed")
                    }) {
                        
                        Image(systemName: "xmark")
                            .resizable()
                            .foregroundColor(Color.black)
                            .frame(width: StartScreenConfig.xmarkSize, height: StartScreenConfig.xmarkSize)
                    }
                    .task {
                        DispatchQueue.main.async {
                            Task {
                                await executeTasks()
                            }
                        }
                    }
                    .padding(.top, StartScreenConfig.xmarkTopPadding)
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
    
    @MainActor
    func executeTasks() async {
        do {
            print("dataStore.editUserData() \(try await dataStore.editUserData())")
            if let userFlow = try await dataStore.getUser() {
                print("dataStore.getUser() \(userFlow)")
            }
        } catch {
            print(error)
        }
    }
}

#Preview {
    StartScreenView()
}
