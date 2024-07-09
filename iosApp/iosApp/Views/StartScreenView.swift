import SwiftUI

struct StartScreenView: View {
    
    var body: some View {
        NavigationView {
            VStack(spacing: 20) {
                HStack {
                    Spacer()
                    Button(action: {
                        print("Button has been pressed")
                    }) {
                        Image(systemName: "xmark")
                            .resizable()
                            .foregroundColor(Color.black)
                            .frame(width: 15, height: 15)
                    }
                    .padding(.top, 10.0)
                }
                
                Image(uiImage: UIImage(named: "logo") ?? UIImage())
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .padding(.horizontal, 50.0)
                
                NavigationLink(destination: RegistrationScreenView()) {
                    Text(LocalizedStringKey("regBtnName"))
                }.buttonStyle(FilledBtnStyle())
                
                NavigationLink(destination: AuthScreenView()) {
                    Text(LocalizedStringKey("authBtnName"))
                }.buttonStyle(BorderedBtnStyle())
                
                Spacer()
            }
            .padding(.horizontal, 16.0)
            .padding(.vertical, 0)
        }
    }
}

#Preview {
    StartScreenView()
}

struct RootPresentationModeKey: EnvironmentKey {
    static let defaultValue: Binding<RootPresentationMode> = .constant(RootPresentationMode())
}

extension EnvironmentValues {
    var rootPresentationMode: Binding<RootPresentationMode> {
        get { return self[RootPresentationModeKey.self] }
        set { self[RootPresentationModeKey.self] = newValue }
    }
}

typealias RootPresentationMode = Bool

extension RootPresentationMode {
    
    public mutating func dismiss() {
        self.toggle()
    }
}
