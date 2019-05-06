//Written, Tested, and Debugged By: Nikunj Jhaveri, Miraj Patel, Nirav Patel
package com.example.homesecurityautomation;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

//this class is meant to control all of the features on the user settings pages, including the back button, the privileges layout
public class UserSettings extends AppCompatActivity implements View.OnClickListener{


    //this is preparing the instances of objects necessary for the obejects in the UI page
    private Button back;
    private TableLayout table;
    DatabaseReference databaseReference;
    List<User> userList;

    //this method automatically gets called when the page is opened we use this to set up the back button and the table
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);


        //the following four lines set up the declared objects
        back = findViewById(R.id.back);
        table = findViewById(R.id.table);
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
    }
    public void onClick(View view)
    {
        if (view == back) {
            finish();
            startActivity(new Intent(this, MainControlActivity.class));
        }
    }

    //this method makes the table of privileges and makes the names of the columns
    public void initTable() {
        int i = 0;

        TableRow header = new TableRow(this);
        TableRow.LayoutParams lpOne = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        header.setLayoutParams(lpOne);
        TextView one = new TextView(this);
        one.setText("USERS");
        TextView mode = new TextView(this);
        mode.setText("MODE");
        ViewGroup.LayoutParams im = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


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
