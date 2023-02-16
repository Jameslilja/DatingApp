package com.example.datingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.datingapp.models.Message;
import com.example.datingapp.models.User;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private Context mContext;
    private List<Message> mMessageList;

    public MessageListAdapter(Context context, List<Message> messageList) {
        mContext = context;
        mMessageList = messageList;
    }
    public void addMessage(Message message) {
        mMessageList.add(message);
        notifyItemInserted(mMessageList.size() - 1);
    }

    @Override
    public int getItemCount(){
        return mMessageList.size();
    }

    @Override
    public int getItemViewType(int position){
        User currentUser = new User();
        Message message = (Message) mMessageList.get(position);

        if (message.getSender().getId().equals(currentUser.getId())){
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_chat_me_perspective, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_chat_from_other_perspective, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position){
        Message message = (Message) mMessageList.get(position);

        switch (holder.getItemViewType()){
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder)holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder)holder).bind(message);
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder{
        TextView messageText, timeText;

        SentMessageHolder(View itemView){
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.message_from_me);
            timeText = (TextView) itemView.findViewById(R.id.message_from_me_Time);
        }
        void bind(Message message){
            messageText.setText(message.getMessageContent());
            //timeText.setText(DateUtils.formatDateTime(message.getCreatedAt()));
        }
    }
    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;


        ReceivedMessageHolder (View itemView){
            super(itemView);

            messageText = (TextView) itemView.findViewById(R.id.chat_from_user);
            timeText = (TextView) itemView.findViewById(R.id.message_from_other_Time);
            nameText = (TextView) itemView.findViewById(R.id.message_from_other);

        }
        void bind (Message message) {
            messageText.setText(message.getMessageContent());
           //timeText.setText(DateUtils.formatDateTime(message.getCreatedAt()));
            nameText.setText(message.getSender().getUserName());
        }
    }
}
