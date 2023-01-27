package com.example.datingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.myToolbar);

        setSupportActionBar(toolbar);

        AppCompatButton button = (AppCompatButton) findViewById(R.id.buttonLogIn);
        AppCompatButton buttonSignUp = (AppCompatButton) findViewById(R.id.buttonSignUp);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });
    }
        public void openRegisterActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        }

        //button.setOnClickListener(new View.OnClickListener() {
        //public void onClick(View v) {
        // Do something in response to button click
        //}
        //});
    }

