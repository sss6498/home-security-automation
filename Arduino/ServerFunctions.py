import serial as sl				#initializes serial input
ser = sl.Serial('/dev/ttyACM1',9600)	#Sets serial to port
from socket import *



#List of string commands to read from mobile application
ctrCmd = ['LIGHTON','LIGHTOFF','TAKEPIC', 'HOMEMODE', 'AWAYMODE', 'ALARMON', 'ALARMOFF', 'ARMED', 'DISARMED' ]

#set port
HOST = ''
PORT = 21567
BUFSIZE = 1024
ADDR = (HOST,PORT)

tcpSerSock = socket(AF_INET, SOCK_STREAM)
tcpSerSock.bind(ADDR)
tcpSerSock.listen(5)

#waiting for client to connect
while True:
        print 'Waiting for connection'
        tcpCliSock,addr = tcpSerSock.accept()
        print '...connected from :', addr
	  #Sends mobile app data to the arduino using serial connection
        try:
                while True:
                        data = ''
                        data = tcpCliSock.recv(BUFSIZE)
                        if not data:
                                break
                        if data == ctrCmd[0]: #Turn Light on
                                ser.write('L')
                                print 'VOLTAGE HIGH'
                        if data == ctrCmd[1]: #Turn light off
                                ser.write('O')
                                print 'VOLTAGE LOW' 
                        if data == ctrCmd[2]: #Take picture
                            ser.write('0')
                        
                        if data == ctrCmd[3]: #Homemode
                            ser.write('H')
                            
                        if data == ctrCmd[4]: #AwayMode
                            ser.write('A')
                            
                        if data == ctrCmd[5]: #AlarmOn
                            ser.write('8')
                            
                        if data == ctrCmd[6]: #AlarmOff
                            ser.write('6')
                            
                        if data == ctrCmd[7]: #Armed
                            ser.write('S')
                            
                        if data == ctrCmd[8]: #Disarmed
                            ser.write('D')
        except KeyboardInterrupt:
                print 'Program Closed!'
tcpSerSock.close();
