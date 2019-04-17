package com.example.homesecurityautomation;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


//This class in the main control activity for the user. It allows the user to control the lights, alarm, and other features of the system. The class also helps the user navigate to the other features.
public class MainControlActivity extends AppCompatActivity implements View.OnClickListener {

    //Rasberry pi connection
    Socket_AsyncTask myAppSocket = null;
    //SET THE IP ADDRESS INTO THIS STRING VARIABLE BELOW
    /*
    String txtAddress= "192.168.30.70:9090";
    public static String wifiModuleIp = "";
    public static int wifiModulePort = 0;
    public static String CMD = "0";
*/


    private Button LogoutButton;
    private FirebaseAuth firebaseAuth;
    private TextView textViewWelcome;
    private Button camera, call, uploadButton;
    private ImageButton settings;
    private Switch lightSwitch, alarmSwitch;
    private ToggleButton homeButton, awayButton, offButton;
    DatabaseReference databaseReference;
    User userP;

    //This method sets up the MainControlActivity page by initializing the UI elements and setting up the features for the user to interact with.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_control);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        myAppSocket = new Socket_AsyncTask();
        myAppSocket.execute();


        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userID = user.getEmail().replace(".","_");
        databaseReference = FirebaseDatabase.getInstance().getReference("users/" + userID);

        //Sets up the listener to retrieve the current user information and privileges.
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                userP = dataSnapshot.getValue(User.class);
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("MainControlActivity", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };

        databaseReference.addListenerForSingleValueEvent(userListener);
        textViewWelcome = findViewById(R.id.textViewWelcome);
        textViewWelcome.setText("Welcome " + user.getEmail());
        LogoutButton = findViewById(R.id.LogoutButton);
        camera = findViewById(R.id.camera);
        call = findViewById(R.id.call);
        lightSwitch = findViewById(R.id.lightSwitch);
        alarmSwitch = findViewById(R.id.alarmSwitch);
        homeButton = findViewById(R.id.homeButton);
        awayButton = findViewById(R.id.awayButton);
        offButton = findViewById(R.id.offButton);
        settings = findViewById(R.id.settings);
        uploadButton = findViewById(R.id.Upload);


        final TextView textView = (TextView) findViewById(R.id.text);
// ...

// Instantiate the RequestQueue.
       /* RequestQueue queue = Volley.newRequestQueue(this);
        String url ="172.20.10.5";

// Request a string response from the provided URL.

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        textView.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
*/
        LogoutButton.setOnClickListener(this);
        camera.setOnClickListener(this);
        settings.setOnClickListener(this);
        call.setOnClickListener(this);
        lightSwitch.setOnClickListener(this);
        uploadButton.setOnClickListener(this);
    }

    //This method responds to the listener objects such as the buttons and switches on the UI and will redirect to perform the appropriate actions based on the user input.
    @Override
    public void onClick(View view){
        if(view == uploadButton)
        {
            finish();
            startActivity(new Intent(this, Uploads.class));
        }
        if(view == LogoutButton)
        {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        if(view == camera)
        {
            finish();
            startActivity(new Intent(this, AccessCamera.class));
        }

        if(view == settings)
        {
            if(userP.getAdmin()) {
                finish();
                startActivity(new Intent(this, AdminSettings.class));
            }
            else
            {
                finish();
                startActivity(new Intent(this, UserSettings.class));

            }
        }
        if(view == call)
        {
            Toast.makeText(this, "Pressed Call", Toast.LENGTH_SHORT).show();

            if(userP.getCall())
            {
                Toast.makeText(this, "CALLING 9-1-1", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "User does not have access to this feature. Please contact the Adminstrator", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        if(view == lightSwitch)
        {
            String action = "";
            if(userP.getLights() && lightSwitch.isChecked())
            {
                action = "ON";
                turnLights(action);
                Toast.makeText(this, "Changing lights", Toast.LENGTH_SHORT).show();
            }
            else if(userP.getLights() && !lightSwitch.isChecked())
            {
                action = "OFF";
                turnLights(action);
                Toast.makeText(this, "Changing lights", Toast.LENGTH_SHORT).show();
            }
            else{
                lightSwitch.toggle();
                Toast.makeText(this, "User does not have access to this feature. Please contact the Adminstrator", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        if(view == alarmSwitch)
        {
            if(userP.getAlarm())
            {
                if(alarmSwitch.isChecked())
                {
                    Toast.makeText(this, "Alarm is armed", Toast.LENGTH_SHORT).show();
                }
                else if(alarmSwitch.isChecked())
                {
                    Toast.makeText(this, "Alarm is disarmed", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                alarmSwitch.toggle();
                Toast.makeText(this, "User does not have access to this feature. Please contact the Adminstrator", Toast.LENGTH_SHORT).show();
            }
            return;
        }



    }

    //This method is used to send an http request to the server to turn on the lights.
    public void turnLights(String action)
    {
        //myAppSocket.getIPandPort();

        //Socket_AsyncTask cmd_action = new Socket_AsyncTask();
        //Log.d("create socket", "socket");
        myAppSocket.SendMessage(action);
        //Log.d("Execute socket", "execute");

    }

    /*
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


    public class Socket_AsyncTask extends AsyncTask<Void, Void, Void>
    {
        Socket socket;

        @Override
        protected Void doInBackground(Void... params){
            try{
                Log.d("IN TRY", "TRYING");
                InetAddress inetAddress = InetAddress.getByName(MainControlActivity.wifiModuleIp);
                Log.d("IP ADDRESS: ", inetAddress.getHostAddress());
                Log.d("PORT ADDRESS: ",MainControlActivity.wifiModulePort+"");
                socket = new Socket(inetAddress, MainControlActivity.wifiModulePort);
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
    */
}




