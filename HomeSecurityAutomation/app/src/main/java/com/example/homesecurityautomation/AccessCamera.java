package com.example.homesecurityautomation;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

//This class is for the AccessCamera page after clicking AccessCamera in the MainControlActivity page
public class AccessCamera extends AppCompatActivity implements View.OnClickListener{

    private ImageView mainPic;
    private ImageButton back, gallery;
    private Button takePic, call;
    String action = "";
    private ProgressBar mProgressCircle;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    Photo photo;

    //This method loads up the buttons and onclicklisteners as soon as the page is first loaded
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access_camera);

        mainPic = findViewById(R.id.mainPic);
        back = findViewById(R.id.back);
        gallery = findViewById(R.id.gallery);
        takePic = findViewById(R.id.takePic);
        call = findViewById(R.id.call);
        databaseReference = FirebaseDatabase.getInstance().getReference("Recent_Picture");
        //storageReference = FirebaseStorage.getInstance().getReference();



        back.setOnClickListener(this);
        gallery.setOnClickListener(this);
        call.setOnClickListener(this);
        takePic.setOnClickListener(this);
    }

    //This method provides onclick events for each of the buttons given in this page
    @Override
    public void onClick(View view)
    {
        if(view == back)
        {
            finish();
            startActivity(new Intent( this, MainControlActivity.class));
        }

        if(view == gallery)
        {
            finish();
            startActivity(new Intent(this,Pictures.class));
        }
        if(view == call)
        {
            Toast toast = Toast.makeText(getApplicationContext(), "Calling 9-1-1", Toast.LENGTH_SHORT); toast.show();
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "2019884763", null)));

        }
        if(view == takePic)
        {
            TakePicture();
            try {
                // thread to sleep for 1000 milliseconds
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e);
            }
            LoadPic();
        }
    }

    public void TakePicture()
    {
        action = "TAKEPIC";
        Socket_AsyncTask myAppSocket = new Socket_AsyncTask();
        //Socket_AsyncTask cmd_action = new Socket_AsyncTask();
        //Log.d("create socket", "socket");
        myAppSocket.setMessage(action);
        myAppSocket.execute();
        LoadPic();
        mProgressCircle.setVisibility(View.INVISIBLE);

    }

    public void LoadPic(){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    photo = postSnapshot.getValue(Photo.class);
                    Log.d("picture", photo.getPhotoURL());
                }

                //mProgressCircle.setVisibility(View.VISIBLE);
                mainPic.setImageURI(Uri.parse(photo.getPhotoURL()));

                //mAdapter.setOnItemClickListener(Pictures.this);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AccessCamera.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                //mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

    }

}
