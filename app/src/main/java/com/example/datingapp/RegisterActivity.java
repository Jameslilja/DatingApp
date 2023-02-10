package com.example.datingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword;
    Button buttonReg;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textViewLogInNow;

    @Override
        public void onStart() {
            super.onStart();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser != null){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
        
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.emailSignUp);
        editTextPassword = findViewById(R.id.passwordSignUp);
        buttonReg = findViewById(R.id.buttonSignUp);
        progressBar = findViewById(R.id.progressBarSignUp);
        textViewLogInNow = findViewById(R.id.registerNow); //logInNow i klippet?!

        textViewLogInNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  progressBar.setVisibility(View.VISIBLE);
                  String email, password;
                  email = String.valueOf(editTextEmail.getText());
                  password = String.valueOf(editTextPassword.getText());

                  if (TextUtils.isEmpty(email)){
                      Toast.makeText(RegisterActivity.this, "Fyll i er e-post", Toast.LENGTH_SHORT).show();
                      return;
                  }

                  if (TextUtils.isEmpty(password)){
                      Toast.makeText(RegisterActivity.this, "Fyll i ert lösenord", Toast.LENGTH_SHORT).show();
                      return;
                  }

                   mAuth.createUserWithEmailAndPassword(email, password)
                           .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task) {
                                   progressBar.setVisibility(View.GONE);
                                   if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "Konto skapat.",
                                                Toast.LENGTH_SHORT).show();
                                       Intent intent = new Intent(getApplicationContext(), NewUserFirstTimeLogin.class);
                                       startActivity(intent);
                                       finish();
                                   } else {
                                       // If sign in fails, display a message to the user.
                                       Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                               Toast.LENGTH_SHORT).show();
                                   }
                               }
                           });
            }
        });
    }
}