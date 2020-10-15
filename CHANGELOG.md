# CHANGELOG

__JL 7.13.2020__

- Create SRS

__JL 7.20.2020__

- Complete initial SRS
- Revise SRS
- Begin UI design

__JL 7.21.2020__

- Revise SRS
- Create UI Design
- Create SDD

__JL 8.20.2020__

- Create launcher activity
- Create options activity
    - Create options picker dialog fragment
- Add custom default font
- Add espresso
- Create unit UI tests
    - Options Activity
        - Select semester option
- Set general theme fields
- Add Java 8 compatibility

__JL 8.21.2020__

- Option selection feature
    - Create options activity
    - Create options picker dialog fragment
    - Implement semester selection functionality
    - Implement campus selection functionality
    - Implement level selection functionality
    - Create and pass unit UI and functional unit tests for options activity
- Transition from launcher to options in time
- Set general theme fields

__JL 8.23.2020__

- Options Activity
    - Save button feature
        - Create save button
        - Enable/disable save button when appropriate
        - Save button UI depending on button state
        - Implement unit tests for enabled/disabled save button
- Implement UI
    - Options Activity
    - Launcher Activity
    - Add image assets
    - Modify layouts
    - Modify resources
    - Lock into portrait mode
    - Add transition animation from launcher to options

__JL 8.31.2020__

- Models
    - Implement location models
- Dependencies
    - Add Retrofit
    - Add RxJava
- Refactoring
    - Organize file structure
- Update UML

__JL 9.2.2020__

- Update Manifest: Internet permission and cleartext traffic

__JL 9.27.2020__

- Implement Rutgers Places retrieval
    - Building and Room models
    - LocationsService
    - LocationsUtil
    - LocationsDeserializer
    - LocationsServiceTest

__JL 9.28.2020__

- Implement Locations retrievel from Rutgers courses data- Write unit test
    - Override equals and hashcode functions in models
    - Implement retrieveLocationsDownloadFromRutgersCourses
    - Add CoursesUtil
    - Fix bugs in deserializer and remove duplicates upon parsing

__JL 10.4.2020__

- Implement subject retrieval from rutgers
    - Implement unit test
    - Implement subject model
    - Implement deserializer
    - Implement retrofit service

__JL 10.5.2020__

- Implement initial LocationsViewModel + unit tests
    - Implement addBuilding
    - Implement containsBuilding
    - Implement addRoom
    - Implement containsRoom
    - Test addBuilding
    - Test containsBuilding
    - Test addRoom
    - Test containsRoom
- Add initial and unimplemented DataProcessor

__JL 10.7.2020__

- Remove clazz field from Locations model and references to it
- Update UML
    - Bridging Components
    - Service Components

__JL 10.8.2020__

- Fix locations model
- Implement more models
    - Level
    - Option
    - Semester
- Implement locations retrieval
    - Including unit test
    - Initial DataProcessor model
    - LocationsRepository
    - RepositoryInstance
    - LocationsWebService
    - SubjectsWebService
    - OptionsUtil.getNearestFullSemesterOption()
    - Partial LocationsViewModel
- Update UML
    - Bridging components
    - Data components
    - Service components

__JL 10.9.2020__

- Fix bug in OptionsUtil
- Implement proper parallelism into locations retrieval

__JL 10.15.2020__

- Add unit test for course info service
- Refactor (rename) DataProcessor to DataDownloadProcessor
- Update UML
    - Data components
    - Tests
    - Bridging components
    - Service components
