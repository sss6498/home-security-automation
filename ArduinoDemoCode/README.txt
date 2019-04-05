	These files are for running the hardware that was presented in the first demo. 
This includes the alarm and accelerometer code, the LED on and off code, and the arducam
code. All of these are the hardware components of the security system. They are currently
programmed and run seperatly, but by the end of the project, they will function as one system. 
All of these components are run on an Arduino Uno board and require the Arduino IDE to execute. 

Code 1 - AccelerometerAlarm: 
	The functionality of the accelerometer and alarm code is to test these two devices 
working and how they interact with one another. The first device is the accelerometer. This
is the MPU-9250. This requires only 4 port connections - A4 to SCL, A5 to SNL, GND to Ground,
and VCC to 5V. The second device is the alarm which can be any standard 2 port alarm or buzzer.
The longer pin side of the alarm is connected to pin 8, and the other end is connected to ground. 
Once hardware is hooked up and code is uploaded, the alarm should start buzzing whenever the 
accelerometer is moved. 

Code 2 - Arducam:
	The functionality of the Arducam is to take a picture when the system detects motion which 
will then be sent for facial recognition to run on it. The device we use is the Arducam_mini_2mp. 
There are 7 port connections - SCL to 17, SDA to 16, VCC to 5V, GND to GND, SCK to 13, MISO to 12, 
MOSI to 11, and CS to 7. In order to run this program, the processor IDE has to be installed. First, 
the Capture code has to be uploaded into the Arduino. Once that is done, the processor code can be run,
and pictures can be taken by command and saved in the same directory. In line 34, if the arduino is 
on a COM that isnt the default one, then the value '0' has to updated accordingly. For example, if 
the output shows COM5 COM6, and the board you are uplaoding the code to is COM6, then you will have to
update the array value to 1. 

code 3 - LightOnOFF:
	The functionality of this code is to show that we are able to manipulate the lights inside the
house with signals beign sent to it. This code just requires any LED connected to a pin 9, through a 
resistor, and to ground. Once the code is uploaded to the Arduino, you can input 2 values into the 
serial monitor bud 9600. If you input a zero, the LED will turn on. If you input a 1, the LED will 
turn off. Any other value will do nothing to the LED. 