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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

public class FormCarActivity extends AppCompatActivity {

    public static final int ImageBack = 1;
    Uri uri = Uri.parse("");
    private Button buttonPicNext;
    private ProgressDialog progressDialog;
    private StorageReference Folder;

    EditText editTextRegis;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_car);

        if(firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }


        buttonPicNext = findViewById(R.id.buttonPicNext);
        editTextRegis = findViewById(R.id.editTextRegis);

        progressDialog = new ProgressDialog(this);
        Folder = FirebaseStorage.getInstance().getReference().child("ImageCarFolder");


        final Spinner mySpinner = (Spinner) findViewById(R.id.spinner);
        final ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(FormCarActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.place));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);


        buttonPicNext.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Uploading...Please Wait");
                progressDialog.show();


                StorageReference Imagename = Folder.child("image"+uri.getLastPathSegment());
                if(uri.equals(Uri.parse(""))) {
                    Toast.makeText(FormCarActivity.this, "Please upload car picture!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                    return ;
                }

                Imagename.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("cars");

                        String regis = editTextRegis.getText().toString();
                        String place = mySpinner.getSelectedItem().toString();

                        if(place.equals("เลือกตำแหน่งใกล้เคียง")) {
                            Toast.makeText(FormCarActivity.this, "กรุณาเลือกตำแหน่งใกล้เคียง!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(TextUtils.isEmpty(regis)) {
                            Toast.makeText(FormCarActivity.this, "กรุณาใส่เลขทะเบียนรถ!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Car newCar = new Car(regis,place,String.valueOf(uri),firebaseAuth.getCurrentUser().getUid());

                        myRef.push().setValue(newCar);

                        Toast.makeText(FormCarActivity.this, "Upload Success", Toast.LENGTH_SHORT).show();

                        next();

                    }
                });
            }
        });
    }



    public void next() {
        Intent intent = new Intent(this,AppActivity.class);
        startActivity(intent);
    }

    public void uploadClick(View v) {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(FormCarActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},200);
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
                ImageView imageView = (ImageView) findViewById((R.id.imageMotocycle));
                imageView.setImageURI(uri);

            }
        }
    }
}
