package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button butonSignin;
    private EditText editTextEmail,editTextPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(),AppActivity.class));
        }


        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        butonSignin = (Button) findViewById(R.id.buttonPicNext);

        progressDialog = new ProgressDialog(this);
        butonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

    }

    private void userLogin() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if(TextUtils.isEmpty(email)) {
            Toast.makeText(LoginActivity.this, "Please enter email!", Toast.LENGTH_SHORT).show();
            return;

        }
        if(TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Please enter password!", Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("Sign in...Please Wait");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if(task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(getApplicationContext(),AppActivity.class));
                }
                else {
                    Toast.makeText(LoginActivity.this, "Sign in fail. Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onClick(View v) {

    }

    public void ClickSignup(View v) {
        Intent intent = new Intent(this,SignupActivity.class);
        startActivity(intent);
    }
}
