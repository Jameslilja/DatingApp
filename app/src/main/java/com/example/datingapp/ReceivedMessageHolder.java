package com.example.datingapp;

import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
    TextView messageText, timeText, nameText;
    ImageView profileImage;

    ReceivedMessageHolder(View itemView){
        super(itemView);
        messageText = (TextView),itemView.findViewById(R.id.chat_from_user);
        timeText = (TextView) itemView.findViewById(R.id.card_gchat_message_other);
        nameText = (TextView) itemView.findViewById(R.id.message_from_other);
        profileImage = (TextView)itemView.findViewById(R.id.image_gchat_profile_other);

    }

    void bind (UserMessage message) {
        messageText.setText(message.getMessage());
        timeText.setText(DateUtils.formatDateTime(message.getCreatedAt()));
        nameText.setText(message.getSender().getNickname());
        Utils.displayRoundImageFromUrl(mcContext,message.getSender().getProfileUrl(),profileImage);


    }
}
