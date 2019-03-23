package com.example.homesecurityautomation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//Below for fingerprint
import android.app.KeyguardManager;
import android.hardware.fingerprint.FingerprintManager;
import android.widget.Toast;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.security.keystore.KeyProperties;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;

import java.lang.reflect.Array;
import java.security.KeyStore;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.InvalidAlgorithmParameterException;
import java.io.IOException;
import java.security.NoSuchProviderException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.crypto.KeyGenerator;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//Above for fingerprint

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button LoginButton;
    private Button SetupButton;
    private Button EnableFPButton;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView TextViewSignin;
    private ProgressBar progressbar;
    private FirebaseAuth firebaseAuth;

    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private static final String KEY_NAME = "example_key";
    private Cipher cipher;
    private FingerprintManager.CryptoObject cryptoObject;
    private boolean fingerprintSuccess = false;
    FingerprintHandler helper;
    private List<User> userlist;
    private String newPass;
    private boolean isPressed = false;


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
        EnableFPButton = findViewById(R.id.EnableFPButton);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        TextViewSignin = findViewById(R.id.TextViewSignin);

        LoginButton.setOnClickListener(this);
        SetupButton.setOnClickListener(this);
        EnableFPButton.setOnClickListener(this);
        TextViewSignin.setOnClickListener(this);

        //Fingerprint
        keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
        if (!keyguardManager.isKeyguardSecure())
        {
            Toast.makeText(this,
                    "Lock screen security not enabled in Settings",
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.USE_FINGERPRINT) !=
                PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this,
                    "Fingerprint authentication permission not enabled",
                    Toast.LENGTH_LONG).show();

            return;
        }

        if (!fingerprintManager.hasEnrolledFingerprints())
        {
            // This happens when no fingerprints are registered.
            Toast.makeText(this,
                    "Register at least one fingerprint in Settings",
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (!fingerprintManager.hasEnrolledFingerprints())
        {

            // This happens when no fingerprints are registered.
            Toast.makeText(this,
                    "Register at least one fingerprint in Settings",
                    Toast.LENGTH_LONG).show();
            return;
        }

//        generateKey();
//        //boolean fingerprintSuccess = false;
//        if (cipherInit())
//        {
//            /*cryptoObject =
//                    new FingerprintManager.CryptoObject(cipher); */
//            cryptoObject = new FingerprintManager.CryptoObject(cipher);
//            helper = new FingerprintHandler(this);
//            helper.startAuth(fingerprintManager, cryptoObject);
//            fingerprintSuccess = helper.isSuccess();
//            String result = "" + fingerprintSuccess;
//            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
//        }

    }
    protected void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES,
                    "AndroidKeyStore");
        } catch (NoSuchAlgorithmException |
                NoSuchProviderException e) {
            throw new RuntimeException(
                    "Failed to get KeyGenerator instance", e);
        }

        try {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException
                | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    private void FingerPrintScan(){
        helper.startAuth(fingerprintManager, cryptoObject);
        this.fingerprintSuccess = helper.isSuccess();
        String result = "" + this.fingerprintSuccess;
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        //return fingerprintSuccess;
    }

    private void LoginUser(){
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        //Boolean fingerprintSuccess = false;

        if(TextUtils.isEmpty(email))
        {
            //email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        ///////////////////////
        //FingerPrintScan();
        //////////////////////
        //Toast.makeText(this, "Passed first step", Toast.LENGTH_SHORT).show();
        //fingerprintSuccess = true;

        if(!fingerprintSuccess && isPressed)
        {
            FingerPrintScan();
        }




        Log.d("The success is ", "" + fingerprintSuccess);
        //final ArrayList<User> userlist = new ArrayList<User>();
        if(fingerprintSuccess)
        {
            Toast.makeText(this, "SUCCESS", Toast.LENGTH_SHORT).show();

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
            userlist = new ArrayList<User>();
           databaseReference.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                       User foundUser = postSnapshot.getValue(User.class);
                       Log.d("User is ", foundUser.getUsername());
                       String foundname = foundUser.getUsername();
                       if (foundname.equals(email))
                       {
                           userlist.add(foundUser);
                           Log.d("Yes", userlist.get(0).getPassword());
                           attemptLogin(userlist.get(0).getUsername(),userlist.get(0).getPassword());
                           return;
                       }
                   }
                   return;
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });
           /*if(!userlist.isEmpty())
           {
               password = userlist.remove(0).getPassword();
           }*/
           //password = userlist.get(0).getPassword();

            /*firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        //start use activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), MainControlActivity.class));

                    }

                }
            }); */
           //dataSnapshot.getRef(databaseReference);
        }
        if (TextUtils.isEmpty(password))
        {
            if(!fingerprintSuccess)
            {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        else {
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

    }

    public void attemptLogin(String username, String password)
    {
        firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
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

        if(view == EnableFPButton)
        {
            if(!isPressed)
            {
                final String email1 = editTextEmail.getText().toString().trim();

                if(TextUtils.isEmpty(email1))
                {
                    //email is empty
                    Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                isPressed = true;
                generateKey();
                //boolean fingerprintSuccess = false;
                if (cipherInit())
                {
            /*cryptoObject =
                    new FingerprintManager.CryptoObject(cipher); */
                    cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    helper = new FingerprintHandler(this);
                    helper.startAuth(fingerprintManager, cryptoObject);
                    fingerprintSuccess = helper.isSuccess();
                    String result = "" + fingerprintSuccess;
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(this, "Fingerprint enabled", Toast.LENGTH_SHORT).show();

            }

        }
    }
}
