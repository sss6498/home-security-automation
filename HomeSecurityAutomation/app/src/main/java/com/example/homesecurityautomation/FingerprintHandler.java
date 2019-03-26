package com.example.homesecurityautomation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

//This class handles what occurs when the fingerprint sensor is involved
public class FingerprintHandler extends
        FingerprintManager.AuthenticationCallback {

    private CancellationSignal cancellationSignal;
    private Context appContext;
    private boolean success = false;
    private String user = "";
    private String password = "";

    public FingerprintHandler(Context context) {
        appContext = context;
    }

    //This method enables and determines results of a fingerprint scan
    public void startAuth(FingerprintManager manager,
                          FingerprintManager.CryptoObject cryptoObject) {

        cancellationSignal = new CancellationSignal();

        if (ActivityCompat.checkSelfPermission(appContext,
                Manifest.permission.USE_FINGERPRINT) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    //The below 4 methods occur depending on what occurred after the fingerprint authentication
    //Primarily a failed or succeeded response will occur and they are exactly what they are named to be.
    @Override
    public void onAuthenticationError(int errMsgId,
                                      CharSequence errString) {
        /*Toast.makeText(appContext,
                "Authentication error\n" + errString,
                Toast.LENGTH_LONG).show(); */
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId,
                                     CharSequence helpString) {
        Toast.makeText(appContext,
                "Authentication help\n" + helpString,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(appContext,
                "Authentication failed.",
                Toast.LENGTH_LONG).show();
        this.success = false;
    }

    @Override
    public void onAuthenticationSucceeded(
            FingerprintManager.AuthenticationResult result) {
        this.success = true;
        Toast.makeText(appContext,
                "Authentication succeeded." ,
                Toast.LENGTH_LONG).show();



    }

    //This method simply provides a means to get the success boolean for operation in other classes for
    //the given FingerprintHandler instance.
    public boolean isSuccess()
    {
        return this.success;
    }



}
