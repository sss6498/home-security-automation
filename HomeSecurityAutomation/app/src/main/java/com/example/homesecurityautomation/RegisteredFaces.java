package com.example.homesecurityautomation;

import android.content.Intent;
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

//This class is for the RegisteredFaces page
public class RegisteredFaces extends AppCompatActivity implements View.OnClickListener{

    private Button back, addNewFace;
    //This method occurs upon loading the page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_faces);

        back = findViewById(R.id.back);
        addNewFace = findViewById(R.id.AddNewFace);

        back.setOnClickListener(this);
        addNewFace.setOnClickListener(this);
    }

    public void onClick(View view)
    {
        if(view == back)
        {
            finish();
            startActivity(new Intent(this, UserSettings.class));
        }

        if(view == addNewFace)
        {
            finish();
            startActivity(new Intent(this, RegisterNewFace.class));
        }


    }
}
