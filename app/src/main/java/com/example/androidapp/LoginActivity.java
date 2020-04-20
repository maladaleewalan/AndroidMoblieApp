package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity{

    private Button butonSignin;
    private EditText editTextEmail,editTextPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        firebaseAuth = FirebaseAuth.getInstance();

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
            Toast.makeText(LoginActivity.this, "กรุณาใส่อีเมลล์!", Toast.LENGTH_SHORT).show();
            return;

        }
        if(TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "กรุณาใส่รหัสผ่าน!", Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("Sign in...Please Wait");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();


                if(task.isSuccessful()) {
                    DatabaseReference d = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseAuth.getCurrentUser().getUid());
                    d.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String role = dataSnapshot.child("role").getValue(String.class);
                            nextPage(role);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else {
                    Toast.makeText(LoginActivity.this, "สมัครไม่สำเร็จ...กรุณาลองอีกครั้ง", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void ClickSignup(View v) {
        Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(intent);
    }

    public void nextPage(String role) {

        if(role.equals("Driver")) {
            Toast.makeText(LoginActivity.this, "กำลัง login!", Toast.LENGTH_SHORT).show();

            editTextEmail.setText("");
            editTextPassword.setText("");

            Intent intent = new Intent(LoginActivity.this,AllCallActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
            startActivity(intent);
        }else if(role.equals("Student/Teacher/Officer") || role.equals("Other")) {
            Toast.makeText(LoginActivity.this, "กำลัง login!", Toast.LENGTH_SHORT).show();

            editTextEmail.setText("");
            editTextPassword.setText("");

            Intent intent = new Intent(LoginActivity.this,AppActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            finish();
            startActivity(intent);
        }
    }
}
