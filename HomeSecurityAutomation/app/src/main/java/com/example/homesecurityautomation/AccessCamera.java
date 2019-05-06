//Written, Tested, and Debugged By: Nikunj Jhaveri, Miraj Patel, Nirav Patel

package com.example.homesecurityautomation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;



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
    //private ProgressBar ProgressCircle;
    DatabaseReference databaseReference;
    DatabaseReference dataFace;
    String photoURI, FaceStatus;
    TextView faceRecStatus;
    ValueEventListener faceListener;


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
        faceRecStatus = findViewById(R.id.faceRecStatus);

        //ProgressCircle = findViewById(R.id.progressBar);
        //ProgressCircle.setVisibility(View.INVISIBLE);
        databaseReference = FirebaseDatabase.getInstance().getReference("Recent_Pictures");
        dataFace = FirebaseDatabase.getInstance().getReference("Face_Rec_Status");

        faceListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    FaceStatus = postSnapshot.getValue(String.class);
                    Log.d("FaceStatus", FaceStatus);
                }
                //dataFace.child("Face_Status").setValue("Testing");


                try {
                Thread.sleep(2000);
            }
                catch(Exception e)
            {
                System.out.println(e);
            }

                try {
                if(FaceStatus.equals("Testing..."))
                {
                    faceRecStatus.setText("Running Algorithm...");
                    faceRecStatus.setBackgroundColor(Color.YELLOW);
                }
                else if(FaceStatus.equals("True"))
                {
                    faceRecStatus.setText("GOOD");
                    faceRecStatus.setBackgroundColor(Color.GREEN);
                    dataFace.removeEventListener(faceListener);
                    dataFace.child("Face_Status").setValue("Testing...");
                }
                else if(FaceStatus.equals("False"))
                {
                    faceRecStatus.setText("BAD");
                    faceRecStatus.setBackgroundColor(Color.RED);
                    dataFace.removeEventListener(faceListener);
                    dataFace.child("Face_Status").setValue("Testing...");
                    IntruderDetected();
                }
            }
                catch(Exception e)
            {
                System.out.println(e);
            }

        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(AccessCamera.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            //mProgressCircle.setVisibility(View.INVISIBLE);
        }
    };
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
            //ProgressCircle.setVisibility(View.VISIBLE);
            TakePicture();
            try {
                // thread to sleep for 1000 milliseconds
                Thread.sleep(5000);
            } catch (Exception e) {
                System.out.println(e);
            }
            LoadPic();
            dataFace.addValueEventListener(faceListener);
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
        //mProgressCircle.setVisibility(View.INVISIBLE);

    }

    public void LoadPic(){

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    photoURI = postSnapshot.getValue(String.class);
                    Log.d("picture", photoURI);
                }


                try {
                    Thread.sleep(600);
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }

                try {
                    //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(photoURI));
                    Glide.with(getApplicationContext())
                            .load(photoURI)
                            .into(mainPic);
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }



                //mainPic.setImageURI(Uri.parse(photoURI));

                //mAdapter.setOnItemClickListener(Pictures.this);


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AccessCamera.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                //mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });

        //ProgressCircle.setVisibility(View.INVISIBLE);

    }

    public void IntruderDetected()
    {
        action = "ALARMON";
        Socket_AsyncTask myAppSocket = new Socket_AsyncTask();
        //Socket_AsyncTask cmd_action = new Socket_AsyncTask();
        //Log.d("create socket", "socket");
        myAppSocket.setMessage(action);
        myAppSocket.execute();

    }

    /*
    public void UpdateFaceRecStatus()
    {
        dataFace.addValueEventListener(faceListener);
    }
*/
}
