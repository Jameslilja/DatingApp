package com.example.datingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);

        return true;
    }

    //Appbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuItemSettings){
            startActivity(new Intent(TestActivity.this, UpdateProfileActivity.class));
        }

        if(item.getItemId() == R.id.menuItemSearch){
            startActivity(new Intent(TestActivity.this, SearchUserAndQualificationActivity.class));
        }

        if(item.getItemId() == R.id.menuItemGoBack){
            startActivity(new Intent(TestActivity.this, MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}