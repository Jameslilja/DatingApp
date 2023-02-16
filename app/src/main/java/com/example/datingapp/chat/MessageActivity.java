package com.example.datingapp.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.datingapp.MainActivity;
import com.example.datingapp.NewUserFirstTimeLogin;
import com.example.datingapp.R;
import com.example.datingapp.SearchUserAndQualificationActivity;
import com.example.datingapp.UpdateProfileActivity;
import com.example.datingapp.backend.User;
import com.example.datingapp.retrofit.RetrofitService;
import com.example.datingapp.retrofit.UserApi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {
    RecyclerView recyclerViewMessages;
    EditText editTextMessagesInput;;
    ProgressBar progressBarMessages;
    ImageView imageViewSendMessage;

    Message message;
    MessageAdapter messageAdapter;
    ArrayList<Message> messages;

    //test
    private String myUsername;
    private String usernameOfRoommate;

    private String myEmail;
    private String emailOfRoommate;
    private String chatRoomId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        message = new Message();
        myEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        emailOfRoommate = getIntent().getStringExtra("email_of_roommate");

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        userApi.findUserByEmail(emailOfRoommate).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                usernameOfRoommate = String.valueOf(response.body());
                message.setReceiver(usernameOfRoommate);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Logger.getLogger(NewUserFirstTimeLogin.class.getName()).log(Level.SEVERE, "Error occurred", t);
            }
        });

        userApi.findUserByEmail(myEmail).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                myUsername = String.valueOf(response.body().getUsername());
                message.setSender(myUsername);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);
        editTextMessagesInput = findViewById(R.id.editTextMessages);
        progressBarMessages = findViewById(R.id.progressBarMessage);
        imageViewSendMessage = findViewById(R.id.imageViewMessage);

        messages = new ArrayList<>();

        imageViewSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("messages/" + chatRoomId).push().setValue(new Message(FirebaseAuth.getInstance().getCurrentUser().getEmail(), emailOfRoommate, editTextMessagesInput.getText().toString()));
                editTextMessagesInput.setText("");
            }
        });
        messageAdapter = new MessageAdapter(messages, myUsername, usernameOfRoommate, MessageActivity.this);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMessages.setAdapter(messageAdapter);

        setUpChatRoom();
        System.out.println("SENDER: " + message.getSender());
        System.out.println("RECEIVER: " + message.getReceiver());

    }

    private void setUpChatRoom(){
        FirebaseDatabase.getInstance().getReference("user/" + FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String myEmail = snapshot.getValue(UserChat.class).getEmail();

                if (emailOfRoommate.compareTo(myEmail)>0){
                    chatRoomId = myUsername + usernameOfRoommate;
                    System.out.println("rad 86 i MessageAct: " + myUsername + usernameOfRoommate);
                } else if (emailOfRoommate.compareTo(myEmail) == 0){
                    chatRoomId = myUsername + usernameOfRoommate;
                    System.out.println("rad 89 i MessageAct: " + myUsername + usernameOfRoommate);
                } else {
                    chatRoomId = usernameOfRoommate + myUsername;
                    System.out.println("rad92 i MessageAct: " + usernameOfRoommate + myUsername);
                }

                attachMessageListener(chatRoomId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void attachMessageListener(String chatRoomId){
        FirebaseDatabase.getInstance().getReference("messages/" + chatRoomId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    messages.add(dataSnapshot.getValue(Message.class));
                }
                messageAdapter.notifyDataSetChanged();
                recyclerViewMessages.scrollToPosition(messages.size()-1);
                recyclerViewMessages.setVisibility(View.VISIBLE);
                progressBarMessages.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
            startActivity(new Intent(MessageActivity.this, UpdateProfileActivity.class));
        }

        if(item.getItemId() == R.id.menuItemSearch){
            startActivity(new Intent(MessageActivity.this, SearchUserAndQualificationActivity.class));
        }

        if(item.getItemId() == R.id.menuItemGoBack){
            startActivity(new Intent(MessageActivity.this, FriendsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}