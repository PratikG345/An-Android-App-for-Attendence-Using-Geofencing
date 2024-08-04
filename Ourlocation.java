package com.example.student_attendance_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Ourlocation extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;

    Button getLocation;
    private final static int REQUEST_CODE = 100;

    private StorageReference mstorage;
    ImageView img;
    EditText edname,ednumber,edaddress,eddepartment,edshift;
    String name,number,address,department,shift,currentDate,currentTime,elocation;
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    Uri imageurl;

    Button btnlocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ourlocation);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        mstorage = FirebaseStorage.getInstance().getReference();
        img = findViewById(R.id.myImage);

        edname = findViewById(R.id.name);
        ednumber = findViewById(R.id.number);
        edaddress = findViewById(R.id.address);
        eddepartment = findViewById(R.id.department);
        edshift = findViewById(R.id.shift);
        btnlocation = findViewById(R.id.btninsert);

        btnlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getlocation();
            }
        });



    }

    private void getlocation() {

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){


            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            if (location != null){



                                try {
                                    Geocoder geocoder = new Geocoder(Ourlocation.this, Locale.getDefault());
                                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    Toast.makeText(getApplicationContext(),addresses.get(0).getLocality(),Toast.LENGTH_LONG).show();
                                    elocation = addresses.get(0).getAddressLine(0);

//                                    if(addresses.get(0).getLocality().equals("Nashik")){
//                                        elocation = addresses.get(0).getAddressLine(0);
//
//                                        Toast.makeText(getApplicationContext(),elocation,Toast.LENGTH_LONG).show();
//                                    }



//                                    lattitude.setText("Lattitude: "+addresses.get(0).getLatitude());
//                                    longitude.setText("Longitude: "+addresses.get(0).getLongitude());
//                                    address.setText("Address: "+addresses.get(0).getAddressLine(0));
//                                    city.setText("City: "+addresses.get(0).getLocality());
//                                    country.setText("Country: "+addresses.get(0).getCountryName());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            }

                        }
                    });


        }
    }

    public void UploadImage(View v){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,101);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK)
        {
            if(requestCode == 101)
            {
                onCaptureImage(data);
            }

        }
    }

    private void onCaptureImage(Intent data) {




        Bitmap thumbnail = (Bitmap)  data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG,90,bytes);

        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), thumbnail, "Title", null);


        imageurl = Uri.parse(path);
        System.out.println("........................................................"+imageurl);

        uploadToFirebase(imageurl.toString());

    }


    private void uploadToFirebase(String image) {



        name = edname.getText().toString();
        number = ednumber.getText().toString();
        address = edaddress.getText().toString();
        department = eddepartment.getText().toString();
        shift = edshift.getText().toString();


        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("EmployeeInfo");

        Profile profile = new Profile(name,number,address,department,shift,imageurl.toString(),currentDate,currentTime,elocation);
        databaseReference.push().setValue(profile);

        StorageReference sr = mstorage.child("myimages/a.jpg");
        sr.putFile(Uri.parse(image)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(),"Upload",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_LONG).show();
            }
        });

        SharedPreferences sharedpreferences = getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("name", name);
        editor.putString("number", number);
        editor.putString("dep", department);
        editor.putString("shift", shift);
        editor.commit();
        editor.apply();
    }
}