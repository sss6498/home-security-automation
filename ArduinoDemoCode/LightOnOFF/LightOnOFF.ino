/*
 * The purpose of this code is to show during the demo
 * that we can manipulate a signal to turn light on and
 * off. Currently, the input is read manually through the 
 * serial monitor. It takes a value of 0 to turn the light
 * on, and value of 1 to turn the light off. In the future,
 * we will be using a signal sent from the phone to turn
 * on/off lights.
 */

int led = 9;    //Set pin value of led
void setup() {
  pinMode(led,INPUT);   
  Serial.begin(9600);   //Starts Serial monitor
}

void loop() { 
    int x = Serial.read();    //Reads for serial input value
    if(x!=-1)         //if user does not input anything, display command typed
    {
    Serial.println(x);
    }
    if(x==48)     //if user inputs a 0, turn on the LED
    {
      digitalWrite(led,HIGH);
    }
    if(x==49)   //if user inputs a 1, turn off the LED
    {
      digitalWrite(led,LOW);
    }
    
  
}
