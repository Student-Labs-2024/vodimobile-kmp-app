import SwiftUI


struct StartScreenView: View {
    @State private var isButtonEnabled: Bool = true
    
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
}

#Preview {
    StartScreenView()
}
