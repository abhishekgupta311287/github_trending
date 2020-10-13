# Github Trending Repositories
=============================

This app the displays trending repositories from Github.

Introduction
--------------------
The app consists of single fragment (TrendingFragment) that displays the trending repositories from Github

### Functionality
##### Fetch trending repositories from github api using retrofit
##### Store fetched repositories in Room Db with refresh timestamp
##### Provides Offline support using Room DB once data is fetched from github api
##### Provides Pull to Refresh action that force refreshes repositories from api in case of success and from DB in case of api failure
##### Sort and display the repositories based on name
##### Sort and display the repositories based on stars
##### Provides Retry option in case of api or any other failure and no data is available to display

### Architecture Used
MVVM with LiveData

### UI Tests
The projects uses Espresso for UI testing.
TrendingFragmentTest mocks TrendingViewModel to run the tests.

### Database Tests
The project creates an in memory database for each database test but still
runs them on the device.

### Local Unit Tests
#### ViewModel Tests
Each ViewModel is tested using local unit tests with mock Repository
implementations.

#### Repository Tests
Each Repository is tested using local unit tests with mock web service and
mock database.

#### Webservice Tests
The project uses MockWebServer project to test REST api interactions.

#### DB Tests
TrendingDaoImpl is tested using local unit tests with mock dao implementations.

### Libraries
* Android Support Library
* Android Architecture Components
* Swipe Refresh Layout for pull to refresh action
* Facebook Shimmer Library for shimmer loading effect
* Koin for dependency injection
* Retrofit and OkHttp for REST api communication
* Kotline coroutines
* Glide for image loading
* Espresso for UI tests
* Mockwebserver for api test
* Mockk for mocking in tests
* Room for offline storage



