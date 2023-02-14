package com.example.datingapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datingapp.models.Conversation;
import com.example.datingapp.models.User;
import com.google.gson.Gson;
import com.example.datingapp.models.Message;

import org.java_websocket.WebSocket;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MessageListActivity extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private OkHttpClient client;
    private WebSocket webSocket;


    /*@Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mMessageRecycler = (RecyclerView) findViewById(R.id.recycler_view);
        mMessageAdapter = new MessageListAdapter(this, messageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);*/
    }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chat);

            client = new OkHttpClient();
            Request request = new Request.Builder().url("http://localhost:8080/ws").build();
            webSocket = client.newWebSocket(request, new ChatWebSocketListener());
        }

        public void sendMessage(String messageContent, User sender, User receiver) {
            Message chatMessage = new Message();
            chatMessage.getId();
            chatMessage.getSender();
            chatMessage.getMessageContent();
            chatMessage.getReceiver();
            chatMessage.getMessageContent();
            Conversation conversation = new Conversation(UUID.randomUUID().toString(), sender, receiver, new ArrayList<Message>());
            conversation.getMessages().add(chatMessage);
            Gson gson = new Gson();
            webSocket.send(gson.toJson(conversation));

        }
}

