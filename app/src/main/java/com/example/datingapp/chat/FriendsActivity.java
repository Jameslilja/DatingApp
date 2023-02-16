package com.example.datingapp.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.datingapp.MainActivity;
import com.example.datingapp.R;
import com.example.datingapp.SearchUserAndQualificationActivity;
import com.example.datingapp.UpdateProfileActivity;
import com.example.datingapp.retrofit.RetrofitService;
import com.example.datingapp.retrofit.UserApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FriendsActivity extends AppCompatActivity {
    RecyclerView recyclerViewFriends;
    ArrayList<UserChat> usersFriends;
    ProgressBar progressBarFriends;
    UsersAdapter usersAdapter;
    UsersAdapter.OnUserClickListener onUserClickListener;
    String myEmail;
    SwipeRefreshLayout swipeRefreshLayoutFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        swipeRefreshLayoutFriends = findViewById(R.id.swipeLayoutFriends);

        swipeRefreshLayoutFriends.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUsers();
                swipeRefreshLayoutFriends.setRefreshing(false);
            }
        });

        recyclerViewFriends = findViewById(R.id.recyclerViewFriends);
        usersFriends = new ArrayList<>();
        progressBarFriends = findViewById(R.id.progressBarFriends);
        onUserClickListener = new UsersAdapter.OnUserClickListener() {
            @Override
            public void onUserClicked(int position) {
                startActivity(new Intent(FriendsActivity.this, MessageActivity.class)
                        .putExtra("email_of_roommate", usersFriends.get(position).getEmail())
                        .putExtra("my_email", myEmail));
            }
        };
        getUsers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    private void getUsers(){
        usersFriends.clear();

        FirebaseDatabase.getInstance().getReference("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    usersFriends.add(dataSnapshot.getValue(UserChat.class));
                }
                usersAdapter = new UsersAdapter(usersFriends,FriendsActivity.this, onUserClickListener);
                recyclerViewFriends.setLayoutManager(new LinearLayoutManager(FriendsActivity.this));
                recyclerViewFriends.setAdapter(usersAdapter);
                progressBarFriends.setVisibility(View.GONE);
                recyclerViewFriends.setVisibility(View.VISIBLE);

                for(UserChat userChat: usersFriends){ //denna är sjukt oklar
                    if (userChat.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){ //ändrade från .getEmail()
                        myEmail = userChat.getEmail(); //denna är sjukt oklar
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //Appbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuItemSettings){
            startActivity(new Intent(FriendsActivity.this, UpdateProfileActivity.class));
        }

        if(item.getItemId() == R.id.menuItemSearch){
            startActivity(new Intent(FriendsActivity.this, SearchUserAndQualificationActivity.class));
        }

        if(item.getItemId() == R.id.menuItemGoBack){
            startActivity(new Intent(FriendsActivity.this, MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}