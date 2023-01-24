package com.example.datingapp;


import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class NewUserFirstTimeLogin extends AppCompatActivity {

    MaterialCardView selectCard;
    TextView textViewQualifications;
    boolean [] selectedQualifications;
    ArrayList<Integer> qualificationList = new ArrayList<>();
    String [] qualificationArray = {"Datorkunskaper", "Kommunikation", "Problemlösning", "Tidshantering", "Överförbara kompetenser"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_first_time_login);

        //initialize all views
        selectCard = findViewById(R.id.selectCard);
        textViewQualifications = findViewById(R.id.textViewQualifications);
        selectedQualifications = new boolean[qualificationArray.length];

        selectCard.setOnClickListener(view -> {

            showQualificationsDialog();
        });

    }

    private void showQualificationsDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(NewUserFirstTimeLogin.this);

        builder.setTitle("Välj kvalifikationer");
        builder.setCancelable(false);

        builder.setMultiChoiceItems(qualificationArray, selectedQualifications, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which, boolean b) {
                if (b){
                    qualificationList.add(which);
                } else {
                    qualificationList.remove(which);
                }
            }
        }).setPositiveButton("Välj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //create string builder
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < qualificationList.size(); i++){

                    stringBuilder.append(qualificationArray[qualificationList.get(i)]);

                    //check condition
                    if (i != qualificationList.size() - 1){
                        //when i is not equal to qualification list size
                        //then add a comma
                        stringBuilder.append(", ");
                    }

                    //setting selected qualifications to textView
                    textViewQualifications.setText(stringBuilder.toString());
                }
            }
        }).setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setNeutralButton("Avmarkera alla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //clearing all selected qualifications on click
                for (int i = 0; i < selectedQualifications.length; i ++){
                    selectedQualifications[i] = false;

                    qualificationList.clear();
                    textViewQualifications.setText("");
                }
            }
        });
        builder.show();
    }
}