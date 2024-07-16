import SwiftUI
import shared

struct ContentView: View {
    @ObservedObject private(set) var viewModel: HelloModel
    
    var body: some View {
        VStack {
            Spacer()
            Button("Start Receiving") {
                Task {
                    await viewModel.startObserving()
                }
            }
            
            Spacer()
            Text(viewModel.values).foregroundStyle(.black)
            Spacer()
        }
    }
}

extension ContentView {
    
    @MainActor
    class HelloModel: ObservableObject {
        @Published var values: String = ""
        
        
        func startObserving() async {
            do {
                let emittedValues = try await HelloWorldRepositoryImpl().getHelloWorld()
                values = emittedValues.hello + " " + emittedValues.word
            } catch {
                print("Произошла ошибка: \(error.localizedDescription)")
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView(viewModel: ContentView.HelloModel())
    }
}
