package com.example.datingapp.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datingapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {

    ArrayList<Message> messages;
    String senderEmail;
    String receiverEmail;
    Context context;

    public MessageAdapter(ArrayList<Message> messages, String senderEmail, String receiverEmail, Context context) {
        this.messages = messages;
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.context = context;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_holder, parent, false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        holder.textViewMessage.setText(messages.get(position).getContent());

        ConstraintLayout constraintLayout = holder.ccll;

        if (messages.get(position).getSender().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
            constraintSet.clear(R.id.cardViewProfile, ConstraintSet.LEFT);
            constraintSet.clear(R.id.textViewMessageContent, ConstraintSet.LEFT);
            constraintSet.connect(R.id.cardViewProfile, ConstraintSet.RIGHT, R.id.constraintLayoutMessageHolder, ConstraintSet.RIGHT, 0);
            constraintSet.connect(R.id.textViewMessageContent, ConstraintSet.RIGHT, R.id.cardViewProfile, ConstraintSet.RIGHT, 0);
            constraintSet.applyTo(constraintLayout);
        } else {
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(constraintLayout);
            constraintSet.clear(R.id.cardViewProfile, ConstraintSet.RIGHT);
            constraintSet.clear(R.id.textViewMessageContent, ConstraintSet.RIGHT);
            constraintSet.connect(R.id.cardViewProfile, ConstraintSet.LEFT, R.id.constraintLayoutMessageHolder, ConstraintSet.LEFT, 0);
            constraintSet.connect(R.id.textViewMessageContent, ConstraintSet.LEFT, R.id.cardViewProfile, ConstraintSet.RIGHT, 0);
            constraintSet.applyTo(constraintLayout);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class MessageHolder extends RecyclerView.ViewHolder{
        ConstraintLayout  ccll;
        TextView textViewMessage;
        TextView textViewProfileEmail;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);

            ccll = itemView.findViewById(R.id.constraintLayoutMessageHolder);
            textViewMessage = itemView.findViewById(R.id.textViewMessageContent);
            textViewProfileEmail = itemView.findViewById(R.id.textViewProfileEmail);
        }
    }
}
