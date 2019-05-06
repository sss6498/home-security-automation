This Repository hosts the Home Security Automation Project.
The HomeSecurityAutomation folder contains all the code, testing documents, and other information regarding the mobile application.
The Arduino folder holds all the code, testing and information containing relating to the hardware components of the project.
The FacialRecognition folder contains all the code, testing documents, and other information relating to the facial recognition aspect of the project.

*We did not follow the format itemized on the demo format, as 3_integration_testing and 4_data_collection did not apply to us. We did not put all coding directories in one folder called 1_code so as to not alter the code, as much of it includes filepaths that shouldn't be altered.*

The 5_documentation folder holds the following files:

    technical_documentation.pdf
    user_documentation.pdf
    brochure/flyer.pdf
    presentation_slides.pdf
    individual_contributions.pdf

-----------------------------------------------ARDUINO CONFIGURATION------------------------------------------------------
	Before running the arduino, make sure to have a raspberry pi model b+ and have arduino IDE installed on it.
After downloading, inside your Arduino folder, make sure the libarary contains all the files same files that are
listed as the Arduino folder in this directory. Some folders such as Arducam do not come in in the libarary after
installation.

	Follow the schematic diagram in the picture to set up all the hardware. Becaues some ports are not visible in
the picture, the port numbers are written above the setup for the correlating variables in the functions.ino file. For
The camera, follow all the pins from left to write on the camera and plug them into all the pins left to right on the
Arduino starting from the highest pin. The only pin not to be plugged like this is 'CS' which should be plugged into
port 7. Check the CameraSchematic inside the camera folder for more details.

	Connect both the Arduino's to the raspberry pi. Ensure that when uploading code, there is a different COM port
selected for both arduinos. Inside the processing file under Arduino\CameraCode\Processing_code, update the COM port
array in line 22. When the processing file is running, there is a list of currently open ports that outputs on the bottom
of the processing screen. It correlates to the index value in "myPort = new Serial(this, Serial.list()[1], 921600);"
Update the arrays index value to the arduino COM port the camera is attatched to. For example, after running the processing
file, the processing file detects 2 COM ports, AMC0 and AMC1, and the camera is attatched to the arduino with COM port
AMC0, thus the array in "myPort = new Serial(this, Serial.list()[1], 921600);" would be changed to "myPort = new
Serial(this, Serial.list()[0], 921600);"

	Inside the "Server Functions" Python file, update line 2 to the serial port that the second arduino is attatched
to (the one with the rest of the devices, not the camera). So if the Second Arduino is on port ACM2, then the string
'/dev/ttyACM1' would be changed to 'ACM2'.
--------------------------------------------------------------------------------------------------------------------------

------------------------------------------------Mobile App Configuration---------------------------------------------------
* The documents that show case testing for this app are included in the App_Testing folder
* This code utilizes the Android Studio IDE and is compiled and run using the run button in Android Studio
* The main java source code is located in HomeSecurityAutomation\app\src\main\java\com\example\homesecurityautomation
* The UI design in XML is located in HomeSecurityAutomation\app\src\main\res\layout

To run the mobile app the user must first download Android Studio IDE onto their computer. Once installed the HomeSecurityAutomation folder that is within the entire project folder must be opened up in the Android Studio software. If the entire project folder is selected to open up in android studio the IDE will not recognize the app code configuration files and will not be able to run the app. Once the code is opened several configuration steps need to be taken to run the system.

The user must navigate to the Socket_AsyncTask class in the main java source code folder. There the String variable txtAddress must be changed to represent the IP address of the Raspberry Pi and the port chosen by the server code that is also located on the Pi. The format for the string variable should be "ip:port" where the ip represent just the ip address number and the port is the port number. Once completed the user must either run the app on an emulation device or directly download the app onto their android smart phone. The phone or emulation device's software must be running Android Pie, API level 28 at minimum.

The device that is running the app must be connected to the same wifi network as the Raspberry Pi. If on a secured network the two devices may not be able to connect to each other and instead the user should switch over to an open wifi network. Once logged in the user can either enter a new first time email and password by pressing the SETUP button or enter in an already created email and password combination to access the app. Some email and password configurations are given below. Once logged into the app the user has access to all of the features provided all of the other configuration steps for every system is done correctly.

Sample login credentials:
Administrator: email: nikunjjhaveri@gmail.com password: abcd1234
Regular User: email: nj216@scarletmail.rutgers.edu password: aaa111

---------------------------------------------------------------------------------------------------------------------------

-------------------------------------------------FULL SYSTEM RUN-----------------------------------------------------------
Run the following files on the raspberry pi to inialize the full system running

1. Run CameraCode.ino file - upload code to the Arduino with the camera
2. Run the Processing file, should detect the camera and take test picture
3. Run Functions.ino file - upload code to the Arduino without the camera
4. Run the ServerFunction.py file - should detect arduino and wait for client connection
5.
6.
---------------------------------------------------------------------------------------------------------------------------
