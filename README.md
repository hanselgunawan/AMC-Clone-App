# AMC-Clone-App

## App Demo
<img src="https://i.gyazo.com/fe7617a5ed4ecc930a74ee3dc6e09839.gif" height="650px" width="350px" />

## Dependencies
```
// Retrofit
implementation 'com.squareup.retrofit2:retrofit:2.9.0'

// Gson Converter
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

// ViewModel
implementation "androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version"

// LiveData
implementation "androidx.lifecycle:lifecycle-livedata:$lifecycle_version"

// Glide
implementation 'com.github.bumptech.glide:glide:4.12.0'
annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'

// Material Design
implementation 'com.google.android.material:material:1.3.0-alpha03'
```

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
* LiveData: to build data objects that notify views when the underlying database changes `MutableLiveData`.
* ViewModel: stores UI-related data that isn't destroyed on app rotations.
* Room: SQLite object mapping library. Easily convert SQLite table data to Java objects.
### ScheduledExecutorServices
Used to schedule commands to run after a given delay, or to execute periodically.
### Glide
A fast and efficient open source media management and image loading framework for Android that wraps media decoding, memory and disk caching, and resource pooling into a simple and easy to use interface.
#### Glide vs Picasso
* Glide loads image according to the size of view **but** Picasso loads full size image.
* Glide prevents `OutOfMemoryError` exception.
* Glide supports `.gif` where Picasso does not.
* Glide can load multiple image into the same view at the same time.
* Glide caches images on the disk. So if it's already on the disk, it won't download anymore.
* Picasso will cache only a single size of image (the full-size) where Glide cache each size of `ImageView`.

## Why MVVM?
* UI components are kept away from the business logic.
* The business logic is kept away from the database operations.
* Easy to read.
* Very good at handling lifecycle events.

## MVVM
* **Model**: holds the data of the application. Expose the data to ViewModel through Observables.
* **View**: It represents the UI. It observes the ViewModel.
* **ViewModel**: A link between the Model and the View. Responsible to transfer the data from the Model. It provides data streams to the View. It uses callbacks to update the View.

## MVVM Flow Diagram
<img src="https://i.imgur.com/bI1InDn.png" width="700px" height="450px">

## Singleton
Singleton Pattern restricts the instantiation of a class and ensures that only one instance of that class exists in Java Virtual Machine (JVM). Singleton class must provide a global access point to get the instance of the class. Singleton Pattern is used for logging, caching, and thread pool. Singleton Pattern is also used in other design pattern, such as: Builder, Prototype, etc. Also used in code Java class.
The file `Service` is a Singleton.

## Parcelable
To send data throughout the activities.

## Files
### OnMovieListener
Used to store all the OnClicks on ViewHolder.
### ViewHolder
Used to assign on which layout element does it pointing to.
```
TextView title, category, duration;
public MovieViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
    super(itemView);
    title = itemView.findViewById(R.id.movie_title);
    category = itemView.findViewById(R.id.movie_category);
    duration = itemView.findViewById(R.id.movie_duration);
}
```
### RecyclerView
The `RecyclerView` file is used as the adapter for the Activity's `recyclerView.adapter`.
Assign value to the view on `onBindViewHolder`.
```
@Override
public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    ((MovieViewHolder) holder).title.setText(movieModels.get(position).getTitle());
}
```
### MovieListActivity
This file is used to display the `RecyclerView` to the user.
Set the adapter by using:
```
recyclerView.setAdapter(movieRecyclerViewAdapter);
```
And then set how we want to show our `RecyclerView` to be displayed by using `setLayoutManager`.
To show it as a list, you can use `new LinearLayoutManager(context)`.
```
recyclerView.setLayoutManager(new LinearLayoutManager(this));
```
To show it as a grid, you can use `new GridLayoutManager(context, column_per_row)`.
```
recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
```
### MovieDetailsActivity
This file is used to display the selected movie's overview.

## Material Design
### App Bar Behavior
To collapse the toolbar when scrolled, we can easily implement that by adding
```
app:layout_behavior="com.google.android.material.appbar.AppBarLayout$ScrollingViewBehavior"
```
into the `<androidx.recyclerview.widget.RecyclerView>` tag on the layout file.
### Set App Bar from Activity
We can setup the toolbar on the activity by calling `setSupportActionBar` function.
```
// Toolbar
Toolbar toolbar = findViewById(R.id.my_toolbar);
setSupportActionBar(toolbar);
```
This method sets the toolbar as the app bar for the activity.
To use the `ActionBar` utility methods, call the activity's `getSupportActionBar()` method. This method returns a reference to an appcompat `ActionBar` object. Once you have that reference, you can call any of the `ActionBar` methods to adjust the app bar. For example, to hide the app bar, call `ActionBar.hide()`.

## Hex Opacity Values
This is used to create an opacity to the color. For example: `#80FFFFFF` to give 50% opacity to black color `#FFFFFF`.
```
100% — FF
95% — F2
90% — E6
85% — D9
80% — CC
75% — BF
70% — B3
65% — A6
60% — 99
55% — 8C
50% — 80
45% — 73
40% — 66
35% — 59
30% — 4D
25% — 40
20% — 33
15% — 26
10% — 1A
5% — 0D
0% — 00
```
