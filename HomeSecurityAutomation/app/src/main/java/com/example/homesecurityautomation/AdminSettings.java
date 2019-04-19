package com.example.homesecurityautomation;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//This class controls the Administrator Settings page and allows the admin of the app to view certain data or access features that are designated only for the admin.s
public class AdminSettings extends AppCompatActivity implements View.OnClickListener {

    private Button NewUserButton, FaceRecButton, back, deleteUser;
    private Switch EnableLight, EnableCamera, EnableAlarm;
    private Switch enableL, enableC, enableA;
    private TableLayout table;
    String action = "";
    DatabaseReference databaseReference;
    List<User> userList;

    //This method starts up the current activity and displays the administrator settings to the user.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_settings);

        NewUserButton = findViewById(R.id.NewUserButton);
        FaceRecButton = findViewById(R.id.FaceRecButton);
        enableL = findViewById(R.id.EnableLight);
        enableC = findViewById(R.id.EnableCamera);
        enableA = findViewById(R.id.EnableAlarm);
        back = findViewById(R.id.back);
        table = findViewById(R.id.table);
        deleteUser = findViewById(R.id.deleteUser);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        userList = new ArrayList<>();

        //Adds a listener for the database to retrieve data relating to each user privilege and put the data into user objects.
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    User user = postSnapshot.getValue(User.class);
                    Log.d("user adding", user.getUsername());

                    userList.add(user);
                }
                initTable();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        back.setOnClickListener(this);
        FaceRecButton.setOnClickListener(this);
        NewUserButton.setOnClickListener(this);
        deleteUser.setOnClickListener(this);
        enableL.setOnClickListener(this);
        enableC.setOnClickListener(this);
        enableA.setOnClickListener(this);

    }

    //This method is used to respond to the button events for each class. It takes in a view object and performs the appropriate actions depending on which button was pressed.
    public void onClick(View view)
    {
        if(view == back)
        {
            finish();
            startActivity(new Intent(this, MainControlActivity.class));
        }

        if(view == NewUserButton)
        {
            finish();
            startActivity(new Intent(this, NewUserSetup.class));
        }

        if(view == FaceRecButton)
        {
            finish();
            startActivity(new Intent(this, RegisteredFaces.class));
        }

        if(view == deleteUser)
        {
            finish();
            startActivity(new Intent(this, DeleteUser.class));
        }
        if(view == enableL)
        {
            if(enableL.isChecked())
            {
                Toast.makeText(this, "Changing lights", Toast.LENGTH_SHORT).show();
            }
            if(!enableL.isChecked())
            {

            }
        }
        if(view == enableC)
        {

        }
        if(view == enableA)
        {

        }
    }

    public void ChangeVals(int device, boolean status, ArrayList<User> AllUsers)
    {
        //0 Light, 1 Camera, 2 Alarm
        //0 off, 1 on
        String component = "lights";
        for(User user: AllUsers)
        {
            //databaseReference = FirebaseDatabase.getInstance().getReference("users");
            //databaseReference.child(user.getUsername()).child(component).setValue(false);
        }

    }



    public ArrayList<User> getEnableVal()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        final ArrayList<User> AllUsers = new ArrayList<User>();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    User user = postSnapshot.getValue(User.class);
                    Log.d("user adding", user.getUsername());

                    AllUsers.add(user);
                }
                initTable();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return AllUsers;
    }

    //This method uses the user list array list and dynamically generates a table for the admin to view each user's privileges.
    public void initTable()
    {
        int i = 0;

        TableRow header = new TableRow(this);
        TableRow.LayoutParams lpOne = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        header.setLayoutParams(lpOne);
        TextView one = new TextView(this);
        one.setText("USERS");
        TextView mode = new TextView(this);
        mode.setText("MODE");
        ViewGroup.LayoutParams im = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       /*
        ImageView lightImage = new ImageView(this);
        lightImage.setImageResource(R.drawable.lightbulb_3);
        lightImage.setLayoutParams(im);

        lightImage.setMaxHeight(40);
        lightImage.setMaxWidth(50);
        ImageView alarmImage = new ImageView(this);
        alarmImage.setImageResource(R.drawable.alarm_bell);
        alarmImage.setLayoutParams(im);
        alarmImage.setMaxHeight(10);
        alarmImage.setMaxWidth(10);
        ImageView cameraImage = new ImageView(this);
        cameraImage.setImageResource(R.drawable.camera);
        cameraImage.setMaxHeight(40);
        cameraImage.setMaxWidth(50);
        ImageView phoneImage = new ImageView(this);
        phoneImage.setImageResource(R.drawable.telephone);
        phoneImage.setLayoutParams(im);
        */
        View view = new View(this);
        view.setMinimumHeight(2);
        view.setBackgroundColor(Color.BLACK);

        TextView light = new TextView(this);
        light.setText("Lights");
        light.setPadding(6,2,6,2);
        TextView alarm = new TextView(this);
        alarm.setText("Alarm");
        alarm.setPadding(6,2,6,2);
        TextView camera = new TextView(this);
        camera.setText("Camera");
        camera.setPadding(6,2,6,2);
        TextView phone = new TextView(this);
        phone.setText("911");
        phone.setPadding(6,2,6,2);

        header.addView(one);
        header.addView(light);
        header.addView(alarm);
        header.addView(camera);
        header.addView(phone);
        header.addView(mode);
        table.addView(header,i);
        i++;
        table.addView(view);
        i++;

        Log.d("in initTable", "making table");

        //This loop takes in each user in the userList array list creates a row for the user and fills in the table according to the privileges.
        for (User u: userList) {

            TableRow row= new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            TextView userText = new TextView(this);
            userText.setText(u.getUsername().substring(0,u.getUsername().indexOf('@')));
            row.addView(userText);
            row.addView(xOrSpace(u.getLights()));
            row.addView(xOrSpace(u.getAlarm()));
            row.addView(xOrSpace(u.getCamera()));
            row.addView(xOrSpace(u.getCall()));
            row.addView(xOrSpace(u.getMode()));
            row.setShowDividers(TableRow.SHOW_DIVIDER_MIDDLE);
            table.addView(row,i);
            i++;
            View line = new View(this);
            line.setMinimumHeight(2);
            line.setBackgroundColor(Color.BLACK);
            table.addView(line);
            i++;
        }
        table.setShowDividers(TableLayout.SHOW_DIVIDER_MIDDLE);

    }

    //This method is used by initTable() to write in an "x" or leave a blank in the user privilege table to show whether a user has a designated privilege to a certain feature.
    public TextView xOrSpace(Boolean value)
    {
        TextView text = new TextView(this);
        if(value)
        {
            text.setText("X");
        }
        else{
            text.setText(" ");

        }
        text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        return text;
    }
}
