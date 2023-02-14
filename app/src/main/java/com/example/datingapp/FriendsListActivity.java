package com.example.datingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FriendsListActivity extends AppCompatActivity {
    ImageButton imageButtonGoBackConversation;
    FloatingActionButton floatingActionButtonAddNewConversation;
    Button buttonGoBackFriends;
    AlertDialog.Builder builder;

    //search conversations
    androidx.appcompat.widget.SearchView searchViewConversations;
    ListView listViewConversations;
    ArrayList<String> arrayListConversations;
    ArrayAdapter adapterConversations;
    String selectedConversation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        //getConversations();
        searchConversation();

        imageButtonGoBackConversation = findViewById(R.id.goBackImageButton);
        searchViewConversations = findViewById(R.id.searchViewConversations);
        listViewConversations = findViewById(R.id.listViewSearchConversations);
        buttonGoBackFriends = findViewById(R.id.goBackFriendButton);

        floatingActionButtonAddNewConversation = findViewById(R.id.newConversationFloatingActionButton);

        floatingActionButtonAddNewConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNewConversationDialog();
            }
        });
    }

    private void showNewConversationDialog() {
        builder = new AlertDialog.Builder(this);

        builder.setTitle("Välj en person att chatta med")
                .setCancelable(false)
                .setPositiveButton("Börja chatta", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                       //VAD SOM HÄNDER NÄR MAN KLICKAR PÅ "POSITIVA KNAPPEN"
                    }
                })
                .setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void searchConversation(){
        searchViewConversations.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapterConversations.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterConversations.getFilter().filter(newText);
                listViewConversations.setVisibility(View.VISIBLE);
                return false;
            }
        });

        listViewConversations.setOnItemClickListener((adapterView, view, i, l) -> {
            selectedConversation = listViewConversations.getItemAtPosition(i).toString();
            listViewConversations.setVisibility(View.INVISIBLE);
            searchViewConversations.setQueryHint(selectedConversation);
            listViewConversations.setSelection(i);

            //trams
            System.out.println("vald konversation är: " + selectedConversation);
            Toast.makeText(getApplicationContext(), selectedConversation + " valt.", Toast.LENGTH_LONG).show();

        });
    }
}