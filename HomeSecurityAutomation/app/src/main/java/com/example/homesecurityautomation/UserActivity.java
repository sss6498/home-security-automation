package com.example.homesecurityautomation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserActivity extends AppCompatActivity implements View.OnClickListener {

    private Button LogoutButton;
    private FirebaseAuth firebaseAuth;
    private TextView textViewWelcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        textViewWelcome = findViewById(R.id.textViewWelcome);
        textViewWelcome.setText("Welcome " + user.getEmail());
        LogoutButton = findViewById(R.id.LogoutButton);

        LogoutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if(view == LogoutButton)
        {
            firebaseAuth.signOut();;
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

    }
}
