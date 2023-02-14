package com.example.datingapp;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketListener;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.PingFrame;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.Handshakedata;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class ChatWebSocket extends WebSocketListener {

    @Override
    public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(WebSocket conn, Draft draft, ClientHandshake request) throws InvalidDataException {
        return null;
    }

    @Override
    public void onWebsocketHandshakeReceivedAsClient(WebSocket conn, ClientHandshake request, ServerHandshake response) throws InvalidDataException {

    }

    @Override
    public void onWebsocketHandshakeSentAsClient(WebSocket conn, ClientHandshake request) throws InvalidDataException {

    }

    @Override
    public void onWebsocketMessage(WebSocket conn, String message) {

    }

    @Override
    public void onWebsocketMessage(WebSocket conn, ByteBuffer blob) {

    }

    @Override
    public void onWebsocketOpen(WebSocket conn, Handshakedata d) {

    }

    @Override
    public void onWebsocketClose(WebSocket ws, int code, String reason, boolean remote) {

    }

    @Override
    public void onWebsocketClosing(WebSocket ws, int code, String reason, boolean remote) {

    }

    @Override
    public void onWebsocketCloseInitiated(WebSocket ws, int code, String reason) {

    }

    @Override
    public void onWebsocketError(WebSocket conn, Exception ex) {

    }

    @Override
    public void onWebsocketPing(WebSocket conn, Framedata f) {

    }

    @Override
    public PingFrame onPreparePing(WebSocket conn) {
        return null;
    }

    @Override
    public void onWebsocketPong(WebSocket conn, Framedata f) {

    }

    @Override
    public void onWriteDemand(WebSocket conn) {

    }

    @Override
    public InetSocketAddress getLocalSocketAddress(WebSocket conn) {
        return null;
    }

    @Override
    public InetSocketAddress getRemoteSocketAddress(WebSocket conn) {
        return null;
    }
}
