int alarmPin = 8;
int buttonPin = 1;

boolean shouldRun = true;

void setup() {
  pinMode(alarmPin,OUTPUT);
  pinMode(buttonPin,INPUT);
}

void loop() {

  
   if(digitalRead(buttonPin)==LOW)
   { 
   runAlarm();
   }
   delay(20);
   
}

int runAlarm()
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

