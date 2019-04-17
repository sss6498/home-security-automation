package com.example.homesecurityautomation;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

 public class Socket_AsyncTask extends AsyncTask<Void, Void, Void>
    {
        Socket socket;

        String txtAddress= "192.168.30.70:9090";
        //String txtAddress= "192.168.30.70:9090";
        public static String wifiModuleIp = "";
        public static int wifiModulePort = 0;
        public static String CMD = "0";

        public void getIPandPort()
        {
            String iPandPort = txtAddress;
            Log.d("MYTEST" , "IP STRING: " + iPandPort);
            String temp[] = iPandPort.split(":");
            wifiModuleIp = temp[0];
            wifiModulePort = Integer.parseInt(temp[1]);
            Log.d("MYTEST" , "IP: " + wifiModuleIp);
            Log.d("MYTEST" , "Port: " + wifiModulePort);

        }

        public void SendMessage(String message) {
            CMD = message;
            try {
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeBytes(CMD);
                dataOutputStream.flush();
                dataOutputStream.close();
            }
            catch (UnknownHostException e)
            {
                e.printStackTrace();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            return;

        }



        @Override
        protected Void doInBackground(Void... params){
            try{
                Log.d("IN TRY", "TRYING");
                getIPandPort();
                InetAddress inetAddress = InetAddress.getByName(wifiModuleIp);
                Log.d("IP ADDRESS: ", inetAddress.getHostAddress());
                Log.d("PORT ADDRESS: ",wifiModulePort+"");
                socket = new Socket(inetAddress,wifiModulePort);
                Log.d("PORT ADDRESS: ","ports");
                if (socket.getInetAddress().isReachable(1000))
                {
                    Log.d("CONNECTED", "No Error");
                }
                else{
                    Log.d("Failed connection", "ERROR");
                }
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeBytes(CMD);
                dataOutputStream.flush();
                dataOutputStream.close();
                socket.close();
            }
            catch (UnknownHostException e)
            {
                e.printStackTrace();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            return null;
        }
    }

