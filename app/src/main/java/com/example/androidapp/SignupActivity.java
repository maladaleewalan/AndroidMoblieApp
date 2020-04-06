package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextFirstname,editTextLastname,editTextTel;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    String email,password,firstname,lastname,tel,role = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextFirstname = (EditText) findViewById(R.id.editTextFirstname);
        editTextLastname = (EditText) findViewById(R.id.editTextLastname);
        editTextTel = (EditText) findViewById(R.id.editTextTel);
        radioGroup = findViewById(R.id.radioGroup);
        //radioButtonDriver = findViewById(R.id.radioButtonDriver);
        //radioButtonStudent = findViewById(R.id.radioButtonStudent);
        //radioButtonStudent = findViewById(R.id.radioButtonOther);





        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //registerUser();
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();
                firstname = editTextFirstname.getText().toString();
                lastname = editTextLastname.getText().toString();
                tel = editTextTel.getText().toString();


                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(SignupActivity.this, "Please enter email!", Toast.LENGTH_SHORT).show();
                    return;

                }
                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(SignupActivity.this, "Please enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(firstname)) {
                    Toast.makeText(SignupActivity.this, "Please enter firstname!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(lastname)) {
                    Toast.makeText(SignupActivity.this, "Please enter lastname!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(tel)) {
                    Toast.makeText(SignupActivity.this, "Please enter tel!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length()<8) {
                    editTextPassword.setError("Password Must be >= 8 Character");
                    return;
                }


                progressDialog.setMessage("Sign up...");
                progressDialog.show();

                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                role = String.valueOf(radioButton.getText());

                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                        if(task.isSuccessful()) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("users");
                            String id = myRef.push().getKey();

                            User newUser = new User(id,email,firstname,lastname,tel,role);

                            myRef.child(id).setValue(newUser);

                            Toast.makeText(SignupActivity.this, "Sign up success", Toast.LENGTH_SHORT).show();

                            editTextPassword.setText("");
                            editTextTel.setText("");
                            editTextEmail.setText("");
                            editTextFirstname.setText("");
                            editTextLastname.setText("");

                            next();

                        }
                        else {
                            Toast.makeText(SignupActivity.this, "Clound not Sign up. Please try again", Toast.LENGTH_SHORT).show();

                        }
                        progressDialog.dismiss();

                    }
                });

            }
        });
    }

    private void registerUser() {
//        email = editTextEmail.getText().toString();
//        password = editTextPassword.getText().toString();
//        firstname = editTextFirstname.getText().toString();
//        lastname = editTextLastname.getText().toString();
//        tel = editTextTel.getText().toString();
//
//
//        if(TextUtils.isEmpty(email)) {
//            Toast.makeText(this, "Please enter email!", Toast.LENGTH_SHORT).show();
//            return;
//
//        }
//        if(TextUtils.isEmpty(password)) {
//            Toast.makeText(this, "Please enter password!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(TextUtils.isEmpty(firstname)) {
//            Toast.makeText(this, "Please enter firstname!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(TextUtils.isEmpty(lastname)) {
//            Toast.makeText(this, "Please enter lastname!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(TextUtils.isEmpty(tel)) {
//            Toast.makeText(this, "Please enter tel!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if(password.length()<8) {
//            editTextPassword.setError("Password Must be >= 8 Character");
//            return;
//        }
//
//
//        progressDialog.setMessage("Sign up...");
//        progressDialog.show();
//
//        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
//                if(task.isSuccessful()) {
//
//                    Toast.makeText(SignupActivity.this, "Sign up success", Toast.LENGTH_SHORT).show();
//
//                    String id = myRefUser.push().getKey();
//
//                    User newUser = new User(id,email,firstname,lastname,tel,role);
//
//                    myRefUser.child(id).setValue(newUser);
//                }
//                else {
//                    Toast.makeText(SignupActivity.this, "Clould not Sign up. Please try again", Toast.LENGTH_SHORT).show();
//
//                }
//                progressDialog.dismiss();
//
//            }
//        });

    }

    public void next() {
        Intent intent = new Intent(this,PicProfileActivity.class);
        startActivity(intent);
    }



}
