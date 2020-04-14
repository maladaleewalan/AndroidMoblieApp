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

import java.util.HashMap;

public class PicProfileActivity extends AppCompatActivity {

    private StorageReference Folder;
    public static final int ImageCode = 1;
    private Uri uri;
    private Button buttonPicNext;
    private ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String nextpage = "";

    FirebaseDatabase database = FirebaseDatabase.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_profile);

        Log.i("stay", "onCreate: PicProfileActivity");

        Folder = FirebaseStorage.getInstance().getReference().child("ImageFolder");
        buttonPicNext = findViewById(R.id.buttonPicNext);
        progressDialog = new ProgressDialog(this);
        uri = Uri.parse("");

//        if(firebaseAuth.getCurrentUser() == null) {
//            Intent intent = new Intent(PicProfileActivity.this,LoginActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            finish();
//            startActivity(intent);
//        }

        buttonPicNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Uploading...Please Wait");
                progressDialog.show();

                final StorageReference Imagename = Folder.child("image"+uri.getLastPathSegment());
                if(uri.equals(Uri.parse(""))) {
                    Toast.makeText(PicProfileActivity.this, "Please upload profile picture!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                    return ;
                }

                Imagename.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();


                        Imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                DatabaseReference myRefUser = FirebaseDatabase.getInstance().getReference().child("users");
                                myRefUser.child(firebaseAuth.getCurrentUser().getUid()).child("profilePic").setValue(String.valueOf(uri));

                            }
                        });


                        Intent intent = getIntent();
                        String roleUser = intent.getStringExtra("roleUser");
                        if (roleUser.equals("Driver")) {
                            uri = Uri.parse("");

                            intent = new Intent(PicProfileActivity.this, FormCarActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            finish();
                            startActivity(intent);

                        } else {
                            uri = Uri.parse("");

                            intent = new Intent(PicProfileActivity.this, AppActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            finish();
                            startActivity(intent);
                        }

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
        startActivityForResult(intent, ImageCode);
        return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ImageCode) {
            if(resultCode == RESULT_OK) {
                uri = data.getData();
                Log.i("uriprofile", "onActivityResult: "+uri);
                ImageView imageView = (ImageView) findViewById((R.id.profilepic));
                imageView.setImageURI(uri);

            }
        }
    }


}
