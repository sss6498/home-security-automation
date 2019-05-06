#include <MPU9250.h>

int takePic = 7;
int motionInput = 3;

//ASHWIN's EDITS
int ledPin = 2;           //Initializes pin to connect LED
int motionLEDPin = 5;
int alarmPin = 8;         //Initializes pin to connect alarm
char mode = 'A';
MPU9250 IMU(Wire,0x68);   // an MPU9250 object with the MPU-9250 sensor on I2C bus 0 with address 0x68

int status;
int loopCounter = 0;      
int initialValue = 0;

//

void setup() {
  // put your setup code here, to run once:

   //ASHWIN'S EDITS
  pinMode(ledPin,OUTPUT);
  pinMode(alarmPin,OUTPUT);
  
  //

  
pinMode(takePic,OUTPUT);
status = IMU.begin(); // start communication with IMU
Serial.begin(9600);
Serial.println("READY!");
digitalWrite(takePic,LOW);

//ASHWIN'S EDITS
  
  if (status < 0) {
    Serial.println("IMU initialization unsuccessful");
    Serial.println("Check IMU wiring or try cycling power");
    Serial.print("Status: ");
    Serial.println(status);
    while(1) {}
  }
  
  IMU.setAccelRange(MPU9250::ACCEL_RANGE_8G);         // setting the accelerometer full scale range to +/-8G 
  IMU.setGyroRange(MPU9250::GYRO_RANGE_500DPS);       // setting the gyroscope full scale range to +/-500 deg/s
  IMU.setDlpfBandwidth(MPU9250::DLPF_BANDWIDTH_20HZ); // setting DLPF bandwidth to 20 Hz
  IMU.setSrd(19);                                     // setting SRD to 19 for a 50 Hz update rate

//

}

void loop() {
  // put your main code here, to run repeatedly:
char x = Serial.read();

 //ASHWIN'S EDITS
   //AWAY MODE BY DEFAULT
  
  if (x == 'H') {
    //ALARM IS OFF
    //UNLESS TRIGGERED BY APP
    mode = 'D';
    digitalWrite(ledPin,HIGH);
  }

  if (x == 'A') {
    //ALARM IS ARMED
    //TRIGGERED BY ACCELEROMETER AND/OR APP
    mode = 'A';
    digitalWrite(ledPin,LOW);
  }
  
  if (x == 'L') {
    //LED SHOULD TURN ON
    digitalWrite(ledPin,HIGH);
    Serial.println("HIGH");
  }   
  
  if (x == 'O') {
    //LED SHOULD TURN OFF
    Serial.println("LOW");
    digitalWrite(ledPin,LOW);
  } 

  if (x == '8') {
    //MANUAL TURN ON ALARM
    runAlarm();
  }

  if (x == 'S') {
    //ALARM IS ARMED
    mode = 'A';
  }
  if (x == 'D') {
    //ALARM IS DISARMED
    mode = 'D';
  }

  // read the sensor
  IMU.readSensor();
  if(loopCounter == 0) {
    initialValue = IMU.getAccelZ_mss();       //Save the intial value of accelerometer
    loopCounter++;
  }
  
  if((mode=='A')&&(IMU.getAccelZ_mss()>initialValue+2||IMU.getAccelZ_mss()<initialValue-2)) {     //If accelerometer reads value greater than threshold, run alarm
    runAlarm();
  }

  //Below is code to output accelerometer readings
  // display the data
  //Serial.print(IMU.getAccelX_mss());
  //Serial.print("\t");
  //Serial.print(IMU.getAccelY_mss());
  //Serial.print("\t");
  //Serial.print(IMU.getAccelZ_mss());
  //Serial.println("\t");
  //delay(90);

//Below are all the function cases for when a serial input is received from the raspberry pi
if(x=='0')
{
 digitalWrite(takePic,HIGH); 
 Serial.println("HIGH");
  digitalWrite(takePic,LOW); 
 Serial.println("LOW");
}
//if(x=='1')
//{
 // digitalWrite(takePic,LOW);
 // Serial.println("LOW");
//}

if(digitalRead(motionInput) == HIGH) 
{
  //digitalWrite(takePic,HIGH);
  //digitalWrite(takePic,LOW);
  digitalWrite(motionLEDPin,HIGH); 
  Serial.println("Motion Detected!");
  delay(3000);
  digitalWrite(motionLEDPin,LOW);
}

//

}

int runAlarm() {   //Runs alarm, alarm runs 3 times with 3 beeps each cycle
  int counter = 0;
  boolean shouldRun = true;
  while(shouldRun) {
    digitalWrite(alarmPin,HIGH);
    delay(50);
    digitalWrite(alarmPin,LOW);
    delay(50);
    digitalWrite(alarmPin,HIGH);
    delay(50);
    digitalWrite(alarmPin,LOW);
    delay(50);
    digitalWrite(alarmPin,HIGH);
    delay(50);
    digitalWrite(alarmPin,LOW);
    delay(50);
    digitalWrite(alarmPin,HIGH);
    delay(50);
    digitalWrite(alarmPin,LOW);
    delay(50);
    /*if(counter == 2)
    {
      shouldRun = false;
    }*/

    if (Serial.read() == '6') {
      shouldRun = false;
    }
    
    delay(500);
    //counter++;
  }
}
