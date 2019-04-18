package com.example.homesecurityautomation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

//This class is for the pictures page
public class Pictures extends AppCompatActivity implements View.OnClickListener{
    private ProgressBar progress;
    FirebaseStorage storage;
    StorageReference storageRef;
    DatabaseReference databaseReference;
    private GridView gridview;
    private Button back;
    List<Photo> photos;
    GridViewAdapter adapter;
    private ProgressBar mProgressCircle;




    //This method occurs when the page is first loaded
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);
        databaseReference = FirebaseDatabase.getInstance().getReference("All_Image_Uploads_Database");

        /*
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference("Stored_Images");
        */
        photos = new ArrayList<>();
        gridview = findViewById(R.id.gridview);
        back = findViewById(R.id.back);
        mProgressCircle = findViewById(R.id.progressCircle);

        //The following two Override methods take a DataSnapshot and iterate through the list of data in the firebase
        Log.d("set up page", "time to get data");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Photo pic = postSnapshot.getValue(Photo.class);
                    photos.add(pic);
                    Log.d("picture", pic.getPhotoURL());
                }

                adapter = new GridViewAdapter(Pictures.this, photos);

                gridview.setAdapter(adapter);

                //mAdapter.setOnItemClickListener(Pictures.this);

                mProgressCircle.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Pictures.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });


        //adapter = new CustomAdapter(this, R.layout.photo_pic, photos);
        //gridview.setAdapter(adapter);

        back.setOnClickListener(this);


    }

    //This method provides the onClick activity for the back button
    @Override
    public void onClick(View view) {
        if(view == back)
        {
            finish();
            startActivity(new Intent(this, AccessCamera.class));
        }
    }
}
