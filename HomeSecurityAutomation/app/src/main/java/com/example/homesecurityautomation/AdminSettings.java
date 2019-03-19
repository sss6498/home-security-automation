package com.example.homesecurityautomation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

public class AdminSettings extends AppCompatActivity implements View.OnClickListener {

    private Button NewUserButton, FaceRecButton, back;
    private TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_settings);

        NewUserButton = findViewById(R.id.NewUserButton);
        FaceRecButton = findViewById(R.id.FaceRecButton);
        back = findViewById(R.id.back);

        back.setOnClickListener(this);
        FaceRecButton.setOnClickListener(this);
        NewUserButton.setOnClickListener(this);

    }

    public void onClick(View view)
    {
        if(view == back)
        {
            finish();
            startActivity(new Intent(this, MainControlActivity.class));
        }

        if(view == NewUserButton)
        {
            finish();
            startActivity(new Intent(this, NewUserSetup.class));
        }

        if(view == FaceRecButton)
        {
            finish();
            startActivity(new Intent(this, RegisteredFaces.class));
        }


    }
}
