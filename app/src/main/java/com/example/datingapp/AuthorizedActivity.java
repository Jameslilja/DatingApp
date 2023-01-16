package com.example.datingapp;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import androidx.appcompat.widget.AppCompatButton;

public class AuthorizedActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorized);

        AppCompatButton buttonGoBack = findViewById(R.id.buttonGoBackAuthorized);
        buttonGoBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.activity_main);
            }
        });

    }

}