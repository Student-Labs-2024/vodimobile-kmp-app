import SwiftUI

struct StartView: View {
    
    var body: some View {
        
        VStack(spacing: 20) {
            HStack {
                Spacer()
                Button(action: {
                    print("Крестик нажат")
                }) {
                    Image(systemName: "xmark")
                        .resizable()
                        .foregroundColor(Color.black)
                        .frame(width: 18, height: 18)
                }
                .padding(.top, 10.0)
            }
            
            Image(uiImage: UIImage(named: "logo") ?? UIImage())
                .resizable()
                .aspectRatio(contentMode: .fit)
                .padding(.horizontal, 50.0)
            
            Button("Зарегистрироваться") {
                
            }.buttonStyle(FilledBtnStyle())
            
            Button("Войти") {
                
            }.buttonStyle(BorderedBtnStyle())
            Spacer()
        }
        .padding(.horizontal, 16.0)
        .padding(.vertical, 0)
    }
}

struct StartView_Previews: PreviewProvider {
    static var previews: some View {
        StartView()
    }
}
