package com.example.homesecurityautomation;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewUserSetup extends AppCompatActivity implements View.OnClickListener{

    //#2aac4a - green
    //#b91d1d - red

    private ToggleButton lightsToggle, alarmToggle, callToggle, modeToggle, pictureToggle;
    private Button back, createAccount;
    private EditText emailText, passwordText;
    FirebaseAuth firebaseAuth;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_setup);

        lightsToggle = findViewById(R.id.lightsToggle);
        alarmToggle = findViewById(R.id.alarmToggle);
        callToggle = findViewById(R.id.callToggle);
        pictureToggle = findViewById(R.id.pictureToggle);
        modeToggle = findViewById(R.id.modeToggle);
        back = findViewById(R.id.back);
        createAccount = findViewById(R.id.creatAccount);
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);

        back.setOnClickListener(this);
        lightsToggle.setOnClickListener(this);
        alarmToggle.setOnClickListener(this);
        callToggle.setOnClickListener(this);
        pictureToggle.setOnClickListener(this);
        modeToggle.setOnClickListener(this);
        createAccount.setOnClickListener(this);
    }

    public void onClick(View view)
    {
        if(view == back)
        {
            finish();
            startActivity(new Intent(this, AdminSettings.class));
        }

        if(view == createAccount)
        {
            registerUser();
        }

        if(view == lightsToggle)
        {
            if(lightsToggle.isChecked())
            {
                lightsToggle.setBackgroundColor(Color.GREEN);
            }
            if(!lightsToggle.isChecked())
            {
                lightsToggle.setBackgroundColor(Color.RED);
            }
        }

        if(view == alarmToggle)
        {
            if(alarmToggle.isChecked())
            {
                alarmToggle.setBackgroundColor(Color.GREEN);
            }
            if(!alarmToggle.isChecked())
            {
                alarmToggle.setBackgroundColor(Color.RED);
            }
        }

        if(view == pictureToggle)
        {
            if(pictureToggle.isChecked())
            {
                pictureToggle.setBackgroundColor(Color.GREEN);
            }
            if(!pictureToggle.isChecked())
            {
                pictureToggle.setBackgroundColor(Color.RED);
            }
        }

        if(view == modeToggle)
        {
            if(modeToggle.isChecked())
            {
                modeToggle.setBackgroundColor(Color.GREEN);
            }
            if(!modeToggle.isChecked())
            {
                modeToggle.setBackgroundColor(Color.RED);
            }
        }

        if(view == callToggle)
        {
            if(callToggle.isChecked())
            {
                callToggle.setBackgroundColor(Color.GREEN);
            }
            if(!callToggle.isChecked())
            {
                callToggle.setBackgroundColor(Color.RED);
            }
        }
    }

    public void registerUser()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        final String email = emailText.getText().toString().trim();
        final String password = passwordText.getText().toString().trim();

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
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //user is registered
                    //will start profle actiivty here.
                    String ModEmail = email.replace(".", "_");
                    User user = new User(email, password,lightsToggle.isChecked(), alarmToggle.isChecked(), callToggle.isChecked(), pictureToggle.isChecked(), modeToggle.isChecked(), false);
                    database.child("users").child(ModEmail).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(NewUserSetup.this, "Registered Successfully", Toast.LENGTH_SHORT).show();

                            // ...
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(NewUserSetup.this, "User Privileges were not registered successfully ", Toast.LENGTH_SHORT).show();

                                }
                            });
                    //addUserPrivileges(user);
                    //finish();
                    //startActivity(new Intent(getApplicationContext(), MainControlActivity.class));
                }
                else{
                    Toast.makeText(NewUserSetup.this, "Could not register, Please Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}
