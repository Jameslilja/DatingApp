package com.example.datingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondProfileActivity extends AppCompatActivity {
    //ImageView chatWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_profile);

        /*chatWindow = findViewById(R.id.chatWindow);

        chatWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondProfileActivity.this,ReceivedMessageHolder.class);
                startActivity(intent);
            }
        });*/
    }
}


//ReceivedMessageHolder ska kopplas ihop med just chattikonen på second profile.