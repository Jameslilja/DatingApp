package com.example.datingapp;

import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class SentMessageHolder extends RecyclerView.ViewHolder {
    TextView messageText, timeText;

    SentMessageHolder(View itemView){
        super(itemView);

        messageText = (TextView) itemView.findViewById(R.id.message_from_me);
        messageText = (TextView) itemView.findViewById(R.id.message_from_me_Time);
    }

    void bind (UserMessage message){
        messageText.setText(message.getMessage());

        timeText.setText(DateUtils.formatDateTime(message.getCreatedAt()));
    }
}