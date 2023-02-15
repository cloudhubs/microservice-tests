# Testing using Gatling 
### Tools Needed
- [Java](https://www.java.com/en/download/help/download_options.html)
- [Scala](https://www.scala-lang.org/download/)
- [Maven](https://maven.apache.org/download.cgi)

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
