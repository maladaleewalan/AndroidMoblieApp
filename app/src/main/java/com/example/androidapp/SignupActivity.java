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


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(editTextEmail.getText().toString())) {
                    Toast.makeText(SignupActivity.this, "Please enter email!", Toast.LENGTH_SHORT).show();
                    return;

                }
                if(TextUtils.isEmpty(editTextPassword.getText().toString())) {
                    Toast.makeText(SignupActivity.this, "Please enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(editTextFirstname.getText().toString())) {
                    Toast.makeText(SignupActivity.this, "Please enter firstname!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(editTextLastname.getText().toString())) {
                    Toast.makeText(SignupActivity.this, "Please enter lastname!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(editTextTel.getText().toString())) {
                    Toast.makeText(SignupActivity.this, "Please enter tel!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(editTextPassword.getText().toString().length()<8) {
                    editTextPassword.setError("Password Must be >= 8 Character");
                    return;
                }

                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();
                firstname = editTextFirstname.getText().toString();
                lastname = editTextLastname.getText().toString();
                tel = editTextTel.getText().toString();

                progressDialog.setMessage("Sign up...Please Wait");
                progressDialog.show();

                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                role = String.valueOf(radioButton.getText());

                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Sign up success", Toast.LENGTH_SHORT).show();

                            editTextPassword.setText("");
                            editTextTel.setText("");
                            editTextEmail.setText("");
                            editTextFirstname.setText("");
                            editTextLastname.setText("");


                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("users");

                            User newUser = new User(email,firstname,lastname,tel,role);

                            myRef.child(firebaseAuth.getCurrentUser().getUid()).setValue(newUser);

                            next();

                        }
                        else {
                            Toast.makeText(SignupActivity.this, "Could not Sign up. Please try again", Toast.LENGTH_SHORT).show();

                        }
                        progressDialog.dismiss();

                    }
                });

            }
        });
    }

    public void next() {
        Intent intent = new Intent(SignupActivity.this,PicProfileActivity.class);
        startActivity(intent);
    }

    public void ClickSignin(View v) {
        Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
        startActivity(intent);
    }



}
