package com.example.homesecurityautomation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Pictures extends AppCompatActivity {
    private ProgressBar progress;
    FirebaseStorage storage;
    StorageReference storageRef;
    private GridView gridview;
    private Button back;
    List<Photo> photos;
    CustomAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference("Stored_Images");
        photos = new ArrayList<>();
        gridview = findViewById(R.id.gridview);
        back = findViewById(R.id.back);


        //adapter = new CustomAdapter(this, R.layout.photo_pic, photos);
        //gridview.setAdapter(adapter);





    }
}
