# ClothesStoreAndroidApp

This is a sample app that includes screens to view : -Catalogue; -Item Details ; -Wishlist; -Basket.

UX flow Description:
  - User and view Products in Catalogue from an endpoint
  - User can locally search products in Catalogue
  - User can click on a Product, a bottomsheet with product info will appear
  - User can then add the item to Wishlist/Basket 
  - User can view saved items in Wishlist
  - User can delete item from wishlist
  - User can move item from Wishlist to Basket
  - Products in Basket Screen appear with a count
  - User can swipe to delete the item
  - When user deletes an item (that has multiple count > 1), only one item will be deleted on each swipe, thus item will appear in the basket list with a lesser count.


Note: I have included a sample package for showcasing my Compose UI skills ("composeuisample" package), I am still in the learning curve regarding this technology, so I was not completely comfortbale
to solely use in the app, as I would have not been able to showcase my real understanding of the architecture I like to use as well as the skills in Android development.
For future projects, I plan to get for comfortable with Compose UI and start using it instead of the traditional XML layouts and Databinding. Thank you for understanding.


SDKs and Languages used :
- Kotlin
- Android SDK

Jetpack Library
- Coroutines, StateFlow and Flow (especilly for room database)
- Navigation Component Architecture
- Safe Args
- ViewModels
- Data binding

Architecures and patterns :
- MVVM
- Repository pattern
- Dependency Injection using Dagger-Hilt, integrated with viewmodels and views(fragments and activities)
- A  single layer of abstraction between Data layer and Presention Layer using interfaces (Reposiotry & RepositoryImpl)
- SOLID principles

Layouts and Designs:
- ConstraintLayout and other Layouts
- Material Design
- Data binding
- Sample compose UI "composeuisample" package

- I have implemented a sample work for :
  - UnitTests (MockRepository and viewmodels)
  - Integrated Tests ( ex : to test DAOs in Room Database)


What could have been added :
- Fully implementing Clean Architecture (added UseCase classes)
- Better UX practices
- Better SOLID Principles
- Implementing Compose UI instead of XML layouts
- Better UniTests practices and wider implementations
- CI/CD pipelines (github actions / bitrise / etc...)

Thank you :)
