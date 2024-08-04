package com.example.student_attendance_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Addprofile extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    FirebaseDatabase firebaseDatabase ;
    DatabaseReference mref;
    FirebaseStorage firebaseStorage;
    ImageButton imageButton ;
    EditText edfirst,edlast,edprice;
    Button btninsert;

    String type,hours;
    private static final int code=1;
    Uri imageurl = null;


    EditText edname,edaddress,edcontact,editem1,editem1price,editem2,editem2price,edexpre;
    String lati,longi,currentDate,currentTime;

    int experinace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addprofile);

        sharedpreferences = getSharedPreferences("pdfdata", Context.MODE_PRIVATE);

        imageButton = findViewById(R.id.imageButton2);
        edname = findViewById(R.id.edname);
        edaddress = findViewById(R.id.edaddress);
        edcontact = findViewById(R.id.edmobile);

        edprice = findViewById(R.id.edprice1);
        edexpre = findViewById(R.id.edexp);

        btninsert = findViewById(R.id.btninsert);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mref = firebaseDatabase.getReference().child("profile");
        firebaseStorage = FirebaseStorage.getInstance();

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,code);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == code && resultCode == RESULT_OK)
        {

            imageurl = data.getData();
            imageButton.setImageURI(imageurl);
        }

        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = edname.getText().toString().trim();
                String place1 = edaddress.getText().toString().trim();
                String address = edcontact.getText().toString().trim();

                String shift = edprice.getText().toString().trim();

                String department = edexpre.getText().toString().trim();





                StorageReference filepath =firebaseStorage.getReference().child("imagepost").child(imageurl.getLastPathSegment());
                filepath.putFile(imageurl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Task<Uri> downloadurl =taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {

                                String t =task.getResult().toString();
                                DatabaseReference newpost =mref.push();

                                newpost.child("shop name").setValue(name);
                                newpost.child("mobileno").setValue(place1);
                                newpost.child("address").setValue(address);
                                newpost.child("shift").setValue(shift);
                                newpost.child("department").setValue(department);
                                newpost.child("date").setValue(currentDate);
                                newpost.child("time").setValue(currentTime);

                                newpost.child("imageurl").setValue(task.getResult().toString());
                                Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                                //String key = newpost.getKey().toString();
                                //Toast.makeText(getApplicationContext(),key.toString(),Toast.LENGTH_SHORT).show();

                                //sharedata(name,key,address);



                            }
                        });
                    }
                });
            }
        });
    }


}