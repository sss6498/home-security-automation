package com.example.homesecurityautomation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

//This class is for the AccessCamera page after clicking AccessCamera in the MainControlActivity page
public class AccessCamera extends AppCompatActivity implements View.OnClickListener{

    private ImageView mainPic;
    private ImageButton back, gallery;
    private Button takePic, call;
    String action = "";

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
        }
        if(view == takePic)
        {
            TakePicture();
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

    }
}
