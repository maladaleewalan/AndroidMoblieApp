package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PicProfileActivity extends AppCompatActivity {

    private StorageReference Folder;
    public static final int ImageBack = 1;
    Uri uri = Uri.parse("");
    private Button buttonPicNext;
    private ProgressDialog progressDialog;

    String role = "";

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_profile);

        Folder = FirebaseStorage.getInstance().getReference().child("ImageFolder");
        buttonPicNext = findViewById(R.id.buttonPicNext);
        progressDialog = new ProgressDialog(this);


        buttonPicNext.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Uploading...Please Wait");
                progressDialog.show();


                StorageReference Imagename = Folder.child("image"+uri.getLastPathSegment());
                Imagename.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("users");
                        myRef.child(firebaseAuth.getCurrentUser().getUid()).child("profilePic").setValue(String.valueOf(uri));

                        myRef.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                String key = dataSnapshot.getKey();
                                if(key.equals(firebaseAuth.getCurrentUser().getUid())) {
                                    role = dataSnapshot.child("role").getValue(String.class);

                                    if(role.equals("Driver")) {
                                        Intent intent = new Intent(PicProfileActivity.this,FormCarActivity.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        Intent intent = new Intent(PicProfileActivity.this,AppActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        Toast.makeText(PicProfileActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();





                    }
                });
            }
        });
    }


    public void uploadClick(View view) {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PicProfileActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},200);
        }
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,ImageBack);
        return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ImageBack) {
            if(resultCode == RESULT_OK) {
                uri = data.getData();
                ImageView imageView = (ImageView) findViewById((R.id.profilepic));
                imageView.setImageURI(uri);

            }
        }
    }


}
