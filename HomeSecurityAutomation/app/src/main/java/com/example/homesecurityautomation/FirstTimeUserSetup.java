package com.example.homesecurityautomation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//This class is used for the FirstTimeUserSetup page
public class FirstTimeUserSetup extends AppCompatActivity implements View.OnClickListener{

    private Button back, createAccount;
    private EditText emailText, passwordText;
    FirebaseAuth firebaseAuth;
    DatabaseReference database;

    //When this page is first created, it creates the buttons and onclicklisteners for the page
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_user_setup);

        back = findViewById(R.id.back);
        createAccount = findViewById(R.id.createAccount);
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);

        back.setOnClickListener(this);
        createAccount.setOnClickListener(this);
    }

    //This method sets the events for each button given on this page when they are clicked
    @Override
    public void onClick(View view) {
        if(view == back)
        {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        if(view == createAccount)
        {
            registerUser();
        }
    }

    //This method is for registering a new user to the firebase
    public void registerUser()
    {
        //Initializes firebase api for authentication and database access
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        final String email = emailText.getText().toString().trim();
        final String password = passwordText.getText().toString().trim();

        //checks if email box is empty
        if(TextUtils.isEmpty(email))
        {
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        //checks if password box is empty
        if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }
        //Creates user with email and password
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //user is registered
                    //will start profle actiivty here.
                    String ModEmail = email.replace(".", "_");
                    User user = new User(email, password,true, true, true, true, true, true);
                    database.child("users").child(ModEmail).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(FirstTimeUserSetup.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            User.numUsers++;

                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(FirstTimeUserSetup.this, "User Privileges were not registered successfully ", Toast.LENGTH_SHORT).show();

                                }
                            });
                    //addUserPrivileges(user);
                    //finish();
                    //startActivity(new Intent(getApplicationContext(), MainControlActivity.class));
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                else{
                    Toast.makeText(FirstTimeUserSetup.this, "Could not register, Please Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
