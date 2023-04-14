# Github App

The app was developed in :
 - Kotlin
 - MVVM Architectural pattern
 - Hilt
 - Coroutines
 - Room
 - Mockito for unit testing
 - Android Min SDK: 21 (Lolipop)

## Project Structure
Globally the project has the following top level packages:
1. **data**: Contains data source both local or remote, and all the data accessing and manipulating components.
2. **di**: Dependency providing classes using Dagger-Hilt.
3. **ui**: Contains the classes View (Activity, Fragment), Adapter, ViewModel.
4. **domain**: Contains all business logic.
5. **utils**: Contains Utility & Helper classes.

### Feature packages
Features/pages is placed in `ui` package. Here is the list of app feature packages:
- user ⇒ Page that contains number of user and search a users.
- detailuser ⇒ Page that contains detail selected user.

## Navigation Graph
- `nav_graph` : Nav graph used for main application flow.

## Technical Debts
- need bump up coverage test for all class

## Credits
- **Dimas Arya Murdiyan** - dimasaryamurdiyan123@gmail.com