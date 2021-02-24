# AMC-Clone-App

## Technology Used
### Retrofit
Retrofit is RESTassured Client for Java/Kotlin and Android. It makes relatively easy to retrieve and upload JSON. It's a web-based server.
We use GSON as the converter to convert JSON.
To use Retrofit we need:
* Model Class;
* Interface; and
* Builder
### Model-View-ViewModel (MVVM)
UI should only has UI logic. We need MVVM to handle the separation of concerns. Google created this architecture.
Key components:
* LiveData: to build data objects that notify views when the underlying database changes.
* ViewModel: stores UI-related data that isn't destroyed on app rotations.
* Room: SQLite object mapping library. Easily convert SQLite table data to Java objects.

**Why MVVM?**
* UI components are kept away from the business logic.
* The business logic is kept away from the database operations.
* Easy to read.
* Very good at handling lifecycle events.

## Singleton
Singleton Pattern restricts the instantiation of a class and ensures that only one instance of that class exists in Java Virtual Machine (JVM). Singleton class must provide a global access point to get the instance of the class. Singleton Pattern is used for logging, caching, and thread pool. Singleton Pattern is also used in other design pattern, such as: Builder, Prototype, etc. Also used in code Java class.
The file `Service` is a Singleton.

## Parcelable
To send data throughout the activities.

