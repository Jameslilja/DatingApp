package com.example.datingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

//Söka efter användare baserat på användarnamn eller bara kvalifikationer.
// (Dropdown för kvalifikationer och sökfunktion för användarnamn?)
public class TestEmpty extends AppCompatActivity {
    // initialize variables
    TextView textViewQualificationsSearch;
    boolean[] selectedQualificationsSearch;
    ArrayList<Integer> qualificationsSearchList = new ArrayList<>();
    String[] qualificationsSearchArray = {"Datorkunskaper", "Kommunikation", "Problemlösning", "Tidshantering", "Överförbara kompetenser"};
    ArrayList<String> selectedQualificationsSearchToSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_empty);

        showQualificationsSearchDialog();
    }

    private void showQualificationsSearchDialog() {

        // assign variable
        textViewQualificationsSearch = findViewById(R.id.textViewSelectQualificationsSearch);

        // initialize selected language array
        selectedQualificationsSearch = new boolean[qualificationsSearchArray.length];

        textViewQualificationsSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(TestEmpty.this);

                // set title
                builder.setTitle("Välj kvalifikationer att söka efter");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(qualificationsSearchArray, selectedQualificationsSearch, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which, boolean b) {
                        selectedQualificationsSearchToSend = new ArrayList<>();
                        // check condition
                        if (b) {
                            // Sort array list
                            //Collections.sort(qualificationsSearchList);

                            qualificationsSearchList.add(which);
                            if (selectedQualificationsSearch[0]) {
                                selectedQualificationsSearchToSend.add(qualificationsSearchArray[0]);
                                System.out.println(selectedQualificationsSearchToSend);
                            }
                            if (selectedQualificationsSearch[1]) {
                                selectedQualificationsSearchToSend.add(qualificationsSearchArray[1]);
                                System.out.println(selectedQualificationsSearchToSend);
                            }
                            if (selectedQualificationsSearch[2]) {
                                selectedQualificationsSearchToSend.add(qualificationsSearchArray[2]);
                                System.out.println(selectedQualificationsSearchToSend);
                            }
                            if (selectedQualificationsSearch[3]) {
                                selectedQualificationsSearchToSend.add(qualificationsSearchArray[3]);
                                System.out.println(selectedQualificationsSearchToSend);
                            }
                            if (selectedQualificationsSearch[4]) {
                                selectedQualificationsSearchToSend.add(qualificationsSearchArray[4]);
                                System.out.println(selectedQualificationsSearchToSend);
                            }
                        } else {
                            qualificationsSearchList.remove(which);
                        }
                    }
                }).setPositiveButton("Välj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < qualificationsSearchList.size(); j++) {
                            // concat array value
                            stringBuilder.append(qualificationsSearchArray[qualificationsSearchList.get(j)]);
                            // check condition
                            if (j != qualificationsSearchList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        textViewQualificationsSearch.setText(stringBuilder.toString());
                        System.out.println("När man klickat ja: " + selectedQualificationsSearchToSend);
                    }
                }).setNegativeButton("Avbryt", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedQualificationsSearchToSend.clear();

                        dialogInterface.dismiss();
                    }
                }).setNeutralButton("Rensa val", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedQualificationsSearch.length; j++) {
                            // remove all selection
                            selectedQualificationsSearch[j] = false;
                            // clear language list
                            qualificationsSearchList.clear();
                            // clear text view value
                            textViewQualificationsSearch.setText("");
                        }
                        selectedQualificationsSearchToSend.clear();
                    }
                });
                builder.show();
            }
        });
    }
}

//System.out.println("FUNGERA: " + selectedQualificationsSearchToSend);
        //System.out.println("qualificationsSearch: " + selectedQualificationsSearch);
        //System.out.println("qualificationsSearchList: " + qualificationsSearchList);