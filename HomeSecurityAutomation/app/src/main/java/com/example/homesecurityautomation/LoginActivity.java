package com.example.homesecurityautomation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button LoginButton;
    private Button SetupButton;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView TextViewSignin;
    private ProgressBar progressbar;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(this);
        progressbar = new ProgressBar(this);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null)
        {
            //user activity
            startActivity(new Intent(getApplicationContext(), MainControlActivity.class));

        }

        LoginButton = findViewById(R.id.LoginButton);
        SetupButton = findViewById(R.id.SetupButton);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        TextViewSignin = findViewById(R.id.TextViewSignin);

        LoginButton.setOnClickListener(this);
        SetupButton.setOnClickListener(this);
        TextViewSignin.setOnClickListener(this);
    }


    private void LoginUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    //start use activity
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainControlActivity.class));

                }

            }
        });

    }

    @Override
    public void onClick(View view)
    {
        if(view ==SetupButton) {
            if(User.numUsers > 0)
            {
                Toast.makeText(LoginActivity.this, "At least one user already exists", Toast.LENGTH_SHORT).show();
                return;
            }
            finish();
            startActivity(new Intent(this, FirstTimeUserSetup.class));
        }

        if(view == LoginButton)
        {
            LoginUser();
        }

        if(view == TextViewSignin)
        {
            //
        }
    }
}
