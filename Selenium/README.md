How to test:

1. Navigate to: File -> Project structure.
	* Go to the "Project" tab and make sure the SDK is set to 1.8
	* Go to the "Modules" tab and make sure the language level is set to 8

2. Navigate to: File -> Settings -> Build, Execution, Deployment -> Compiler -> Java Compiler
	* Change the compiler to be "Eclipse"
	* Change the target bytecode version to 1.8

3. Change the directory of the chromedriver application to where it is installed locally on your computer

4. Make sure your Google Chrome is updated
	* Go to the settings page in Chrome. Then go "About Chrome" and make sure Chrome is fully updated and restart Chrome

4. Run the tests
   