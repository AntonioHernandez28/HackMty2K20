package com.example.hackmty2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    EditText cUser, cPassword;
    Button BotonLogin, BotonReg;
    FirebaseAuth fAuth;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cUser = findViewById(R.id.userText);
        cPassword = findViewById(R.id.PasswordText);
        BotonLogin = findViewById(R.id.LoginBtn);
        BotonReg = findViewById(R.id.RegisterBtn);

        fAuth = FirebaseAuth.getInstance();

        BotonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = cUser.getText().toString().trim();
                String Password = cPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    cUser.setError("Email can not be empty");
                    return;
                }

                if(TextUtils.isEmpty(Password)){
                    cPassword.setError("Password can not be empty");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "User Logeado", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Home.class));
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        BotonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });


    }
}