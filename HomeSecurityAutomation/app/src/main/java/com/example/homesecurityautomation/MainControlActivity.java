package com.example.homesecurityautomation;


import android.content.Intent;
import android.graphics.Color;
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
import java.util.ArrayList;
import java.util.List;


//This class in the main control activity for the user. It allows the user to control the lights, alarm, and other features of the system. The class also helps the user navigate to the other features.
public class MainControlActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private TextView textViewWelcome;
    private Button camera, call, uploadButton, lightON, alarmON, lightOFF, alarmOFF, LogoutButton;
    private ImageButton settings;
    //private Switch lightSwitch, alarmSwitch;
    private Button homeButton, awayButton, offButton;
    DatabaseReference databaseReference;
    User userP;
    String action = "";
    List<Boolean> adminSwitchList;

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


        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userID = user.getEmail().replace(".","_");
        databaseReference = FirebaseDatabase.getInstance().getReference("users/" + userID);
        lightON = findViewById(R.id.lightON);
        lightOFF = findViewById(R.id.lightOFF);
        textViewWelcome = findViewById(R.id.textViewWelcome);
        textViewWelcome.setText("Welcome " + user.getEmail());
        LogoutButton = findViewById(R.id.LogoutButton);
        camera = findViewById(R.id.camera);
        call = findViewById(R.id.call);
        alarmON = findViewById(R.id.alarmON);
        alarmOFF = findViewById(R.id.alarmOFF);
        homeButton = findViewById(R.id.homeButton);
        awayButton = findViewById(R.id.awayButton);
        offButton = findViewById(R.id.offButton);
        settings = findViewById(R.id.settings);
        uploadButton = findViewById(R.id.Upload);
        LogoutButton.setOnClickListener(this);
        settings.setOnClickListener(this);
        uploadButton.setOnClickListener(this);


        adminSwitchList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("AdminSettingUsers");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    Boolean temp = postSnapshot.getValue(Boolean.class);
                    adminSwitchList.add(temp);
                }
                //initTable();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("users/" + userID);

        //Sets up the listener to retrieve the current user information and privileges.
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                userP = dataSnapshot.getValue(User.class);

                if(userP.getLights() && adminSwitchList.get(2))
                {
                    lightON.setOnClickListener(MainControlActivity.this);
                    lightOFF.setOnClickListener(MainControlActivity.this);
                }
                else
                {
                    lightON.setBackgroundColor(Color.parseColor("#808080"));
                    lightOFF.setBackgroundColor(Color.parseColor("#808080"));
                }

                if(userP.getAlarm() && adminSwitchList.get(0))
                {
                    alarmON.setOnClickListener(MainControlActivity.this);
                    alarmOFF.setOnClickListener(MainControlActivity.this);
                }
                else
                {
                    alarmON.setBackgroundColor(Color.parseColor("#808080"));
                    alarmOFF.setBackgroundColor(Color.parseColor("#808080"));
                }

                if(userP.getMode())
                {
                    homeButton.setOnClickListener(MainControlActivity.this);
                    awayButton.setOnClickListener(MainControlActivity.this);
                    offButton.setOnClickListener(MainControlActivity.this);
                }
                else
                {
                    homeButton.setBackgroundColor(Color.parseColor("#808080"));
                    awayButton.setBackgroundColor(Color.parseColor("#808080"));
                    offButton.setBackgroundColor(Color.parseColor("#808080"));
                }
                if(userP.getCamera() && adminSwitchList.get(1))
                {
                    camera.setOnClickListener(MainControlActivity.this);
                }
                else
                {
                    camera.setBackgroundColor(Color.parseColor("#808080"));
                }

                if(userP.getCall())
                {
                    call.setOnClickListener(MainControlActivity.this);
                }
                else
                {
                    call.setBackgroundColor(Color.parseColor("#808080"));
                }
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
            if(adminSwitchList.get(1))
            {
                if(userP.getCamera()) {
                    finish();
                    startActivity(new Intent(this, AccessCamera.class));
                }
                else{
                    Toast.makeText(this, "User does not have access to this feature. Please contact the Administrator", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(this, "User does not have access to this feature. Please contact the Administrator", Toast.LENGTH_SHORT).show();
            }
            return;
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
                Toast.makeText(this, "User does not have access to this feature. Please contact the Administrator", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        if(view == lightON)
        {
            if(adminSwitchList.get(2))
            {
                if(userP.getLights())
                {
                    action = "ON";
                    ExecuteAction(action);
                    Toast.makeText(this, "Changing lights", Toast.LENGTH_SHORT).show();
                }

                else{
                    Toast.makeText(this, "User does not have access to this feature. Please contact the Administrator", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(this, "User does not have access to this feature. Please contact the Administrator", Toast.LENGTH_SHORT).show();
            }

            return;
        }
        if(view == lightOFF)
        {
            if(adminSwitchList.get(2))
            {
                if(userP.getLights())
                {
                    action = "OFF";
                    ExecuteAction(action);
                    Toast.makeText(this, "Changing lights", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "User does not have access to this feature. Please contact the Adminstrator", Toast.LENGTH_SHORT).show();
                }
            }

            else
            {
                Toast.makeText(this, "User does not have access to this feature. Please contact the Adminstrator", Toast.LENGTH_SHORT).show();
            }

            return;

        }
        if(view == alarmON)
        {
            if(adminSwitchList.get(0))
            {
                if(userP.getAlarm())
                {
                    Toast.makeText(this, "Alarm is armed", Toast.LENGTH_SHORT).show();
                    action = "ARMED";
                    ExecuteAction(action);
                }
                else{
                    Toast.makeText(this, "User does not have access to this feature. Please contact the Adminstrator", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(this, "User does not have access to this feature. Please contact the Adminstrator", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        if(view == alarmOFF)
        {
            if(adminSwitchList.get(0))
            {
                if(userP.getAlarm())
                {
                    Toast.makeText(this, "Alarm is disarmed", Toast.LENGTH_SHORT).show();
                    action = "DISARMED";
                    ExecuteAction(action);
                }
                else{
                    Toast.makeText(this, "User does not have access to this feature. Please contact the Administrator", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(this, "User does not have access to this feature. Please contact the Administrator", Toast.LENGTH_SHORT).show();
            }
            return;

        }

        if(view == homeButton || view == awayButton || view == offButton)
        {
            if(userP.getMode()) {

                if (view == homeButton) {
                    action = "HOMEMODE";
                } else if (view == awayButton) {
                    action = "AWAYMODE";
                } else if (view == offButton) {
                    action = "OFFMODE";
                }
                ExecuteAction(action);
            }
            else {
                Toast.makeText(this, "User does not have access to this feature. Please contact the Administrator", Toast.LENGTH_SHORT).show();

            }

        }




    }

    //This method is used to send an http request to the server to turn on the lights.
    public void ExecuteAction(String action)
    {

        Socket_AsyncTask myAppSocket = new Socket_AsyncTask();
        //Socket_AsyncTask cmd_action = new Socket_AsyncTask();
        //Log.d("create socket", "socket");
        myAppSocket.setMessage(action);
        myAppSocket.execute();

        //Log.d("Execute socket", "execute");

    }

}




