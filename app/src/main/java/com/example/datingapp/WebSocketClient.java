package com.example.datingapp;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.example.datingapp.models.Message;
import com.google.gson.Gson;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class WebSocketClient extends WebSocketListener implements StompSessionHandler {
    private String serverUrl = "ws://localhost:8080/ws"; // replace with your server URL
    private String stompTopic = "/topic/messages"; // replace with your STOMP topic
    private StompSession stompSession;
    private StompClient stompClient;
    private MessageListAdapter messageListAdapter;

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        stompSession = session;
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {

    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {

    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return null;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        // WebSocket connection opened
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(0, TimeUnit.MILLISECONDS).build();
        Request request = new Request.Builder().url(serverUrl).build();
        client.newWebSocket(request, this);
        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, serverUrl);
        stompClient.connect();
        stompClient.topic("/topic/messages").subscribe();
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        // WebSocket message received
        if (text.startsWith("MESSAGE\n")) {
            // Parse the STOMP message
            String[] parts = text.split("\n");
            String destination = parts[1].substring(parts[1].indexOf(':') + 1);
            String body = parts[parts.length - 1];
            if (destination.equals(stompTopic)) {
                // Handle the message
                System.out.println("Received message: " + body);
                Message message = new Gson().fromJson(body, Message.class);

                messageListAdapter.addMessage(message);
            }
        }
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        // WebSocket connection closed
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        // WebSocket connection failed
    }

    public void sendMessage(Message message) {
        if (message.getMessageContent().trim().equals("")) {
            return;
        }
        message.setSender(message.getSender());
        message.setReceiver(message.getReceiver());
        message.setMessageContent(message.getMessageContent());

        Gson gson = new Gson();
        String json = gson.toJson(message);
        if (stompClient != null && stompClient.isConnected()) {
            stompClient.send("/app/sendMessage", json);
            messageListAdapter.addMessage(message);
        } else {
            Log.e(TAG, "Stomp client not connected");
        }
    }
}


