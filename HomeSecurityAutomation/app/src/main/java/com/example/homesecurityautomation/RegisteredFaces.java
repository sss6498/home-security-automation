package com.example.homesecurityautomation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
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

//This class is for the RegisteredFaces page
public class RegisteredFaces extends AppCompatActivity implements View.OnClickListener{

    private Button back, addNewFace;
    FirebaseStorage storage;
    StorageReference storageRef;
    DatabaseReference databaseReference;
    private GridView gridview;
    List<Photo> photos;
    GridViewAdapter adapter;
    private ProgressBar mProgressCircle;
    //This method occurs upon loading the page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_faces);

        back = findViewById(R.id.back);
        addNewFace = findViewById(R.id.AddNewFace);

        back.setOnClickListener(this);
        addNewFace.setOnClickListener(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("Faces_Database");

        /*
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference("Stored_Images");
        */
        photos = new ArrayList<>();
        gridview = findViewById(R.id.gridview);

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

                adapter = new GridViewAdapter(RegisteredFaces.this, photos);

                gridview.setAdapter(adapter);

                //mAdapter.setOnItemClickListener(Pictures.this);

                mProgressCircle.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(RegisteredFaces.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
        mProgressCircle.setVisibility(View.INVISIBLE);
    }

    public void onClick(View view)
    {
        if(view == back)
        {
            finish();
            startActivity(new Intent(this, AdminSettings.class));
        }

        if(view == addNewFace)
        {
            finish();
            startActivity(new Intent(this, RegisterNewFace.class));
        }


    }
}
