# Associate Android Developer Certification's Examples
This Repository represents Notes(To-Do) example to cover Associate Android Developer(AAD) certification's some topics. below topics which covered in this repository.

## 1.Persistent data storage
Here i mentioned the subtopic of Persistent data storage with relevant files.

### 1. Define a database schema; include tables, fields, and indices
* [NotesContract.java](https://github.com/solutelabs/AAD_examples/blob/master/app/src/main/java/com/example/android/databaseexample/data/NotesContract.java)

### 2. Create an application-private database file
* [NotesHelper.java](https://github.com/solutelabs/AAD_examples/blob/master/app/src/main/java/com/example/android/databaseexample/data/NotesHelper.java)

### 3. Construct database queries returning single results
Here i used content provider so content provider related code you can see from NotesProvider class and it's calling part from HomePresenter's getNotes() method.
* [HomePresenter.java](https://github.com/solutelabs/AAD_examples/blob/master/app/src/main/java/com/example/android/databaseexample/home/HomePresenter.java) : check getNotes() method
* [NotesProvider.java](https://github.com/solutelabs/AAD_examples/blob/master/app/src/main/java/com/example/android/databaseexample/data/NotesProvider.java)
: check query() method

If you want to see database query without use of content provider then you can check from HomePresenter's [diff file](https://github.com/solutelabs/AAD_examples/commit/10b98ffdd9517ee7aa4e7aa39a787105e9edc069#diff-78bad223ee8c27d1cbfc9788b17e4726)

### 4. Construct database queries returning multiple results
 Same as 3rd subtopic, you can see code from NotesProvider class for Content provider but for multiple result's calling example you need to check from HomePresenter's getAllUser() method.
* [HomePresenter.java](https://github.com/solutelabs/AAD_examples/blob/master/app/src/main/java/com/example/android/databaseexample/home/HomePresenter.java) : check getAllUser() method
* [NotesProvider.java](https://github.com/solutelabs/AAD_examples/blob/master/app/src/main/java/com/example/android/databaseexample/data/NotesProvider.java)
: check query() method

If you want to see database query without use of content provider then you can check from HomePresenter's [diff file](https://github.com/solutelabs/AAD_examples/commit/10b98ffdd9517ee7aa4e7aa39a787105e9edc069#diff-78bad223ee8c27d1cbfc9788b17e4726)

### 5. Insert new items into a database
* [AddUserPresenter.java](https://github.com/solutelabs/AAD_examples/blob/master/app/src/main/java/com/example/android/databaseexample/add_user/AddUserPresenter.java) : check addUser() method
* [AddNotesPresenter.java](https://github.com/solutelabs/AAD_examples/blob/master/app/src/main/java/com/example/android/databaseexample/notes/AddNotesPresenter.java) : check addNote() method
* [NotesProvider.java](https://github.com/solutelabs/AAD_examples/blob/master/app/src/main/java/com/example/android/databaseexample/data/NotesProvider.java)
: check insert() method

### 6. Update or delete existing items in a database
* [NotesProvider.java](https://github.com/solutelabs/AAD_examples/blob/master/app/src/main/java/com/example/android/databaseexample/data/NotesProvider.java)
: check delete() method

### 7. Expose a database to other applications via Content Provider

### 8. Read and parse raw resources or asset files

### 9. Create persistent preference data from user input

### 10. Toggle application logic based on preference values
* [pref_settings.xml](https://github.com/solutelabs/AAD_examples/blob/master/app/src/main/res/xml/pref_settings.xml)
* [SettingsFragment.java](https://github.com/solutelabs/AAD_examples/blob/master/app/src/main/java/com/example/android/databaseexample/settings/SettingsFragment.java)

## 2.Fundamental application components
Here i mentioned the subtopic of Fundamental application components with relevant files.

### 1. Describe an application's key functional and nonfunctional requirements
### 2. Create an Activity that displays a layout resource
### 3. Fetch local data from disk using a Loader on a background thread
### 4. Propagate data changes through a Loader to the UI
### 5. Schedule a time-sensitive task using alarms
### 6. Schedule a background task using JobScheduler
### 7. Execute a background task inside of a Service
### 8. Implement non-standard task stack navigation (deep links)
### 9. Integrate code from an external support library
