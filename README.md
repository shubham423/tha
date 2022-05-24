# tha

# Demo
https://user-images.githubusercontent.com/57858666/170095348-af6afa0e-3e41-48c0-84a3-3c742be0d847.mp4


## Built With
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) -
  - [Hilt-Dagger](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.
  - [Hilt-ViewModel](https://developer.android.com/training/dependency-injection/hilt-jetpack) - DI for injecting `ViewModel`.

 # Package Structure

    com.example.tha    # Root Package
     .
     ├── data                # For data handling.
     │   ├── local           #local db
     │   ├── models          # Model data classes 
         └── repository      # Single source of data.
     |
     ├── di                  # Dependency Injection             
     │   └── modules         # DI Modules
     |
     ├── ui                  # UI/View layer
     │   │
     │   ├── add
     │   ├── auth
     │   ├── noteslist
     │   ├── update     
     |   ├── viewmodels        
     │   └── adapters        
     |
     └── utils               # Utility Classes / Kotlin extensions


 ## Architecture
 This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.

 ![](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)

![diagram_task](https://user-images.githubusercontent.com/57858666/170096236-6dc26224-0894-4528-b656-23f534425c39.PNG)

## Technology Stack:
  1) Kotlin
  2) XML
  3) Android Jetpack
  4) Android Architecture Components
  5) SQLite database using room
