package com.example.homesecurityautomation;

import android.app.DownloadManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;


import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;
//import com.google.android.gms.common.api.Response;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainControlActivity extends AppCompatActivity implements View.OnClickListener {

    private Button LogoutButton;
    private FirebaseAuth firebaseAuth;
    private TextView textViewWelcome;
    private Button camera, call;
    private ImageButton settings;
    private Switch lightSwitch, alarmSwitch;
    private ToggleButton homeButton, awayButton, offButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_control);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        textViewWelcome = findViewById(R.id.textViewWelcome);
        textViewWelcome.setText("Welcome " + user.getEmail());
        LogoutButton = findViewById(R.id.LogoutButton);
        camera = findViewById(R.id.camera);
        call = findViewById(R.id.call);
        lightSwitch = findViewById(R.id.lightSwitch);
        alarmSwitch = findViewById(R.id.alarmSwitch);
        homeButton = findViewById(R.id.homeButton);
        awayButton = findViewById(R.id.awayButton);
        offButton = findViewById(R.id.offButton);
        settings = findViewById(R.id.settings);

        final TextView textView = (TextView) findViewById(R.id.text);
// ...

// Instantiate the RequestQueue.
       /* RequestQueue queue = Volley.newRequestQueue(this);
        String url ="172.20.10.5";

// Request a string response from the provided URL.

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        textView.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
*/
        LogoutButton.setOnClickListener(this);
        camera.setOnClickListener(this);
        settings.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if(view == LogoutButton)
        {
            firebaseAuth.signOut();;
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        if(view == camera)
        {
            finish();
            startActivity(new Intent(this, AccessCamera.class));
        }
        if(view == settings)
        {
            finish();
            startActivity(new Intent(this, AdminSettings.class));
        }



    }

    public void turnLightsOn()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="172.20.10.5";

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //textView.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}
