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

public class AdminSettings extends AppCompatActivity implements View.OnClickListener {

    private Button NewUserButton, FaceRecButton, back;
    private TableLayout table;
    DatabaseReference databaseReference;
    List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_settings);

        NewUserButton = findViewById(R.id.NewUserButton);
        FaceRecButton = findViewById(R.id.FaceRecButton);
        back = findViewById(R.id.back);
        table = findViewById(R.id.table);
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        userList = new ArrayList<>();

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
        Log.d("before table", "Before");
        //initTable();
        Log.d("After Table", "After");
    }

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


    }

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
        View view = new View(this);
        view.setMinimumHeight(2);
        view.setBackgroundColor(Color.BLACK);

        header.addView(one);
        header.addView(lightImage);
        //header.addView(alarmImage);
        //header.addView(cameraImage);
        header.addView(phoneImage);
        header.addView(mode);
        table.addView(header,i);
        i++;
        table.addView(view);
        i++;

        Log.d("in initTable", "making table");
        for (User u: userList) {

            TableRow row= new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            TextView userText = new TextView(this);
            userText.setText(u.getUsername());
            row.addView(userText);
            row.addView(xOrSpace(u.getLights()));
            row.addView(xOrSpace(u.getAlarm()));
            row.addView(xOrSpace(u.getCamera()));
            row.addView(xOrSpace(u.getCall()));
            row.addView(xOrSpace(u.getMode()));
            Log.d("value", u.getAlarm().toString());
            table.addView(row,i);
            i++;
            //table.addView(view);
            //i++;
        }

    }

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
        return text;
    }
}
