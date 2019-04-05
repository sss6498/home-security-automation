/*
 * This code was created for the purpose of the Demo.
 * This code is to show the functionalitiy of the 
 * Acccelerometer and the alarm. When the accelerometer 
 * reads an analog value past a threshold, it should run
 * the alarm. The purpose of this is to show how the
 * accelerometer is going to be impleted into our system.
 * In the actual system, it will be connected to the door,
 * and whenever an intruder breaks through the door, the 
 * accelerometer will trigger the alarm.
 */

#include "MPU9250.h"

// an MPU9250 object with the MPU-9250 sensor on I2C bus 0 with address 0x68
MPU9250 IMU(Wire,0x68);
int status;
int alarmPin = 8;         //Initializes pin to connect alarm
int loopCounter = 0;      
int initialValue = 0;
void setup() {
  // serial to display data
  pinMode(alarmPin,OUTPUT);
  Serial.begin(9600);
  while(!Serial) {}

  // start communication with IMU 
  status = IMU.begin();
  if (status < 0) {
    Serial.println("IMU initialization unsuccessful");
    Serial.println("Check IMU wiring or try cycling power");
    Serial.print("Status: ");
    Serial.println(status);
    while(1) {}
  }
  // setting the accelerometer full scale range to +/-8G 
  IMU.setAccelRange(MPU9250::ACCEL_RANGE_8G);
  // setting the gyroscope full scale range to +/-500 deg/s
  IMU.setGyroRange(MPU9250::GYRO_RANGE_500DPS);
  // setting DLPF bandwidth to 20 Hz
  IMU.setDlpfBandwidth(MPU9250::DLPF_BANDWIDTH_20HZ);
  // setting SRD to 19 for a 50 Hz update rate
  IMU.setSrd(19);  
}

void loop() {
  // read the sensor
  IMU.readSensor();
  if(loopCounter == 0)
  {
    initialValue = IMU.getAccelZ_mss();       //Save the intial value of accelerometer
     loopCounter++;
  }
  if(IMU.getAccelZ_mss()>initialValue+2||IMU.getAccelZ_mss()<initialValue-2)      //If accelerometer reads value greater than threshold, run alarm
  {
    runAlarm();
  }
  
  // display the data
  Serial.print(IMU.getAccelX_mss());
  Serial.print("\t");
  Serial.print(IMU.getAccelY_mss());
  Serial.print("\t");
  Serial.print(IMU.getAccelZ_mss());
  Serial.print("\t");
  delay(90);
}


int runAlarm()    //Runs alarm, alarm runs 3 times with 3 beeps each cycle
{
  int counter = 0;
  boolean shouldRun = true;
  while(shouldRun)
  {
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
    if(counter == 2)
    {
      shouldRun = false;
    }
    delay(500);
    counter++;
  }
}


