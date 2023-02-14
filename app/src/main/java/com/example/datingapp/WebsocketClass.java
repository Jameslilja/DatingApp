package com.example.datingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.webjars.stompjs.Stomp;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class WebsocketClass extends AppCompatActivity {
    private String name;
    private WebSocketClient webSocketClient;
    private StompClient mStompclient;
    private String SERVER_PATH = "http://localhost:8080/ws";
    private EditText messageEdit;
    private View sendBtn;
    private RecyclerView recyclerView;

    private Map<integer, string> buttonDestinationMap;

    private void connectToServer() {
        mStompclient = Stomp.over(WebSocket.class, "http://localhost:8080/socket");

        mStompclient.connect();

        mStompclient.send("(\"/start/initial\", \"{\"username\":\"User1\"}\"").subscribe();

        mStompclient.topic("/topic/messages").subscribe(message -> {

        });
    }

    private void sendMessage(String recipient, String messageEdit) {
        mStompclient.send("/app/sendMessage", "{"recipient":"" + recipient + "","payload":"" + message + ""}").subscribe();
    }
        webSocketClient.setConnectTimeout(10000);
        webSocketClient.setReadTimeout(60000);
        webSocketClient.enableAutomaticReconnection(5000);
        webSocketClient.connect();
}




       /* stompClient = new StompClient("ws://localhost:8080/ws");
                stompClient.connect();
                buttonDestinationMap = new HashMap<>();
        buttonDestinationMap.put(R.id.edit_text);
        }
        }
private void createWebSocketClient(){
        URI uri;
        try {
        uri = new URI("http://localhost:8080/ws");
        }
        catch (URISyntaxException e) {
        e.printStackTrace();
        return;
        }
        webSocketClient = new WebSocketClient(uri) {
@Override
public void onException(Exception e){
        System.out.println(e.getMessage());
        }
@Override
public void onClosedReceived {
        Log.i("WebSocket", "Closed");
        System.out.println("onClosedReceived");
        }
        };
        webSocketClient.setConnectTimeout(10000);
        webSocketClient.setReadTimeout(60000);
        webSocketClient.enableAutomaticReconnection(5000);
        webSocketClient.connect();
        }
public void sendMessage(View view){
        Log.i("Websocket", "Button was clicked");

        String destination = buttonDestinationMap.get(view.getId());
        stompClient.send(destination, "1");
        }*/