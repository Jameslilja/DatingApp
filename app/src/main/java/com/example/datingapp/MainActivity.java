package com.example.datingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    Button buttonLogOut;
    TextView textView;
    FirebaseUser user;

    Button buttonGoToSearch;
    Button buttonUpdateProfile;
    Button buttonGoToConversations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonGoToSearch = findViewById(R.id.goToSearchButton);
        buttonUpdateProfile = findViewById(R.id.buttonProfileSettings);
        buttonGoToConversations = findViewById(R.id.goToConversationsButton);
        auth = FirebaseAuth.getInstance();
        buttonLogOut = findViewById(R.id.logout);
        user = auth.getCurrentUser();

        buttonGoToConversations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FriendsListActivity.class);
                startActivity(intent);
            }
        });

        //search
        buttonGoToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SearchUserAndQualificationActivity.class);
                        startActivity(intent);
            }
        });

        //LOGOUT
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        buttonUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UpdateProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}


