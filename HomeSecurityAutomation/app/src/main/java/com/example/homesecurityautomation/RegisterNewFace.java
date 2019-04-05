package com.example.homesecurityautomation;

import android.content.Intent;
import android.graphics.Camera;
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

//this class will utilize the camera api to set up a page where the user can take a picture of themselves to register thier face in teh system

//This class is used for the RegisterNewFace page

public class RegisterNewFace extends AppCompatActivity implements View.OnClickListener {

    //The following two lines setup the objects that will be used later
    private Button addFace, camButton, back, retakeButton;
    DatabaseReference databaseReference;
    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    PictureCallback rawCallback;
    ShutterCallback shutterCallback;
    PictureCallback jpegCallback;

    //This method occurs when the page is first loaded
    @Override
    //this method gets called everytime the page is opened. It sets up the back button
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_face);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
    }

    //This method provides an onCLick event for the back button
    public void onClick(View view) {
        if (view == back) {
            finish();
            startActivity(new Intent(this, RegisteredFaces.class));
        }
    }
}
