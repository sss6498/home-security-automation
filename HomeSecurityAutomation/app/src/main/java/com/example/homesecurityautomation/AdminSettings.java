package com.example.homesecurityautomation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    User user = postSnapshot.getValue(User.class);

                    userList.add(user);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        initTable();



        back.setOnClickListener(this);
        FaceRecButton.setOnClickListener(this);
        NewUserButton.setOnClickListener(this);

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
        int i = 1;
        for (User u: userList) {

            TableRow row= new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            TextView userText = new TextView(this);
            userText.setText(u.getUsername());
            row.addView(userText);
            row.addView();
            table.addView(row,i);
            i++;
        }
    }
}
