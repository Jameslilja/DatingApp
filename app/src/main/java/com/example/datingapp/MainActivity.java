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

        AppCompatButton button = (AppCompatButton) findViewById(R.id.buttonLogIn);
        button.setOnClickListener(new View.OnClickListener() {
        public void onClick(View v) {
        // Do something in response to button click
        }
        });
    }
}

