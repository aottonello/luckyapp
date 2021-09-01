# Introduction



App respects its Mvvm architecture. Android Architecture Components
Part of Android Jetpack. Android architecture components are a collection of libraries that help you design robust, testable, and maintainable apps.

Dagger has been used for Dependency Injection and Programming in kotlin. Implementing REST API in the Android app using Retrofit Library.

UI list with Groupie is a simple, flexible library for complex RecyclerView layouts. NavController manages app navigation.

UnitTest created using Mockito and Junit.  

<h4>Features</h4>
<ul>
  <li>Home List with sections</li>
   <li>Dynamic search</li>
   <li>Detail view (mock from json)</li>
   <li>Animations</li>
  </ul>
<h4>Architecture</h4>

MVVM is one of the architectural patterns which enhances separation of concerns, it allows
separating the user interface logic from the business (or the back-end) logic. Its target 
(with other MVC patterns goal) is to achieve the following principle “Keeping UI code simple 
and free of app logic in order to make it easier to manage”.

<ul>
<li>Lifecycles: It manages activity and fragment lifecycles of our app, survives configuration changes,
avoids memory leaks and easily loads data into our UI.</li>
<li>LiveData: It notifies views of any database changes. Use LiveData to build data objects that notify views when
the underlying database changes.</li>
<li>Room: It is a SQLite object mapping library. Use it to Avoid boilerplate code and easily 
convert SQLite table data to Java objects. Room provides compile time checks of SQLite statements
and can return RxJava, Flowable and LiveData observables.</li>
<li>ViewModel: It manages UI-related data in a lifecycle-conscious way. It stores UI-related data
that isn't destroyed on app rotations.</li>
<li>Repository: The repository depends on a persistent data model and a remote backend data source.</li>

<img src="https://miro.medium.com/max/1400/1*Tt_OwtZJ993YzswuRyPQiA.png" width="700" >

 <h4>Library used</h4>
<ul>
<li><a href="https://developer.android.com/topic/libraries/architecture/viewmodel" target="_blank">Viewmodel</a></li>
<li><a href="https://developer.android.com/topic/libraries/architecture/livedata">Livedata</a></li>
<li><a href="https://github.com/google/dagger" target="_blank">Dagger2</a></li>
<li><a href="https://github.com/google/dagger" target="_blank">Dagger Android</a></li>
<li><a href="https://developer.android.com/kotlin/coroutines" target="_blank">Groupie</a></li>
<li><a href="https://github.com/lisawray/groupie" target="_blank">Material library</a></li>
<li><a href="https://developer.android.com/guide/navigation/navigation-getting-started" target="_blank">Navigation Component</a></li>
<li><a href="Gson ">Gson</a></li>
<li><a href="https://square.github.io/retrofit/" target="_blank">Retrofit</a></li>
<li><a  target="_blank">RxJava</a></li>
 
  
  
  
</ul>


