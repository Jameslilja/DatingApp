package com.example.datingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatButton buttonLogIn = findViewById(R.id.buttonLogIn);
        AppCompatButton buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
            setContentView(R.layout.authorized);
        }
        });
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.signup);
            }
        });
    }
}

