import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init() {
        // Initialize Coil ImageLoader for iOS
        Platform_iosKt.initializeImageLoader()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
