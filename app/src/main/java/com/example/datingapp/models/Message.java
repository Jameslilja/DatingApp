package com.example.datingapp.models;

public class Message {
    private Long id;
    private String messageContent;
    private User sender;
    private User receiver;
    private Conversation conversation;

    public Message() {

    }

    public Message(Long id, String messageContent, User sender, User receiver, Conversation conversation) {
        this.id = id;
        this.messageContent = messageContent;
        this.sender = sender;
        this.conversation = conversation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}


