# Testing using Gatling 
### Tools Needed
- [Gatling](https://gatling.io/open-source/)
- [Java](https://www.java.com/en/download/help/download_options.html)
- [Scala](https://www.scala-lang.org/download/)
- [Maven](https://maven.apache.org/download.cgi)

### Set Up
1. Navigate to: File -> Project structure
 - Under the "Global libraries" tab, add the folder where you downloaded Scala
2. Change the URL for the correct URL for the deployed microservice system
 - Navigate to the "Modules" directory and then into the "HeaderModules" file
 - Change the URL in the .baseUrl section

### Parts of Gatling
- Gatling Recorder
  - Used to record steps on an application and turn into a test script
  - Types
    - Proxy
    - HAR file
- Gatling Engine
  - Used to run the test scripts
  - Usage
    - Run Gatling Engine
    - Select index of test to run from list, then click 'enter'
    - Optional: Enter description of test
    - Once test is run, the output folder will be put in target.gatling directory
    - Optional: Change name of file with more meaningful name

### Change Virtual Users
 - Go into given test
 - Change the number of users in the setUp() function
