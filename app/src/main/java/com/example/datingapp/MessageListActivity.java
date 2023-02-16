package com.example.datingapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datingapp.models.Message;
import com.example.datingapp.models.User;

import java.util.ArrayList;
import java.util.List;

public class MessageListActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private WebSocketClient webSocketClient;
    private User currentUser;
    private User chattingUser;

    public MessageListActivity(User currentUser, User chattingUser) {
        this.currentUser = currentUser;
        this.chattingUser = chattingUser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        List<Message> messageList = new ArrayList<>();
        mMessageRecycler = (RecyclerView) findViewById(R.id.recycler_view);
        mMessageAdapter = new MessageListAdapter(this, messageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);

        }
    public void onSendButtonClick(View view) {
        EditText messageEditText = findViewById(R.id.edit_text);
        String messageText = messageEditText.getText().toString().trim();

        if (!messageText.isEmpty()) {
            Message message = new Message();
            message.setMessageContent(messageText);
            message.setSender(currentUser);
            message.setReceiver(chattingUser);

            webSocketClient.sendMessage(message);
            messageEditText.setText("");
        }
    }
}