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
    private GridView photoList;
    private Button back;
    List<Photo> photos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference("Stored_Images");
        photos = new ArrayList<>();
        photoList = findViewById(R.id.photoList);
        back = findViewById(R.id.back);





    }
}
