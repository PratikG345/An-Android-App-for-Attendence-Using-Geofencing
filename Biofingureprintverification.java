package com.example.student_attendance_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executor;

public class Biofingureprintverification extends AppCompatActivity {

    BiometricPrompt prompt;
    BiometricPrompt.PromptInfo promptInfo;
    ConstraintLayout layout;
    String currentDate,currentTime,name,number,dep,shift;
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biofingureprintverification);



        SharedPreferences sh = getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE);
         name = sh.getString("name", "");
        number = sh.getString("number", "");
        dep = sh.getString("dep", "");
        shift = sh.getString("shift", "");
        Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();


        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate())
        {
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(getApplicationContext(),"Device Doesn't Have Fingerprint",Toast.LENGTH_LONG).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(getApplicationContext(),"Not Working",Toast.LENGTH_LONG).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(getApplicationContext(),"Not Fingerprint Assigned",Toast.LENGTH_LONG).show();
        }


        Executor executor = ContextCompat.getMainExecutor(this);
        prompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                Toast.makeText(getApplicationContext(),"Authenticate Success",Toast.LENGTH_LONG).show();

                currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                savedata(currentDate,currentTime,name,number,dep,shift);


            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        });

        promptInfo  = new BiometricPrompt.PromptInfo.Builder().setTitle("Hello")
                .setDescription("Use").setDeviceCredentialAllowed(true).build();
        prompt.authenticate(promptInfo);


    }

    private void savedata(String currentDate, String currentTime, String name, String number, String dep, String shift) {


        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("Attend");

        Attendance attendance = new Attendance(currentDate,currentTime,name,number,dep,shift);

        //Profile profile = new Profile(name,number,address,department,shift,imageurl.toString(),currentDate,currentTime,elocation);
        databaseReference.push().setValue(attendance);

        Toast.makeText(getApplicationContext(),"Uploaded",Toast.LENGTH_LONG).show();
    }
}