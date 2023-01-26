package com.example.datingapp;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Arrays;

public class NewUserFirstTimeLogin extends AppCompatActivity {
    //selectCard, textViewQualifications, selectedQualifications, qualificationsList och qualificationArray
    // används för att kunna välja kvalifikationer
    MaterialCardView selectCard;
    TextView textViewQualifications;
    boolean [] selectedQualifications;
    ArrayList<Integer> qualificationList = new ArrayList<>();
    String [] qualificationArray = {"Datorkunskaper", "Kommunikation", "Problemlösning", "Tidshantering", "Överförbara kompetenser"};

    //dessa används för searchView
    SearchView searchViewCity;
    ListView listViewCity;
    ArrayList<String> arrayListCitiesInSweden;
    ArrayAdapter adapter;
    String selectedCity;

    //övriga
    Button buttonGoToMain;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_first_time_login);

        buttonGoToMain = findViewById(R.id.buttonGoToMain);

        buttonGoToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //qualifications
        selectCard = findViewById(R.id.selectCard);
        textViewQualifications = findViewById(R.id.textViewQualifications);
        selectedQualifications = new boolean[qualificationArray.length];

        //qualifications
        selectCard.setOnClickListener(view -> {

            showQualificationsDialog();
        });

        //searchView
        searchViewCity = findViewById(R.id.searchCity);
        listViewCity = findViewById(R.id.listViewSearchCity);

        arrayListCitiesInSweden = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.citiesInSwedenArray)));

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayListCitiesInSweden);
        listViewCity.setAdapter(adapter);

        searchViewCity.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                listViewCity.setVisibility(View.VISIBLE);
                return false;
            }
        });

        listViewCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCity = listViewCity.getItemAtPosition(i).toString();

                listViewCity.setVisibility(View.INVISIBLE);

                searchViewCity.setQueryHint(selectedCity);

                listViewCity.setSelection(i);

                closeKeyboard();

                Toast.makeText(getApplicationContext(), selectedCity + " valt.", Toast.LENGTH_LONG).show();

            }
        });

    }

    private void closeKeyboard()
    {
        // this will give us the view
        // which is currently focus
        // in this layout
        View view = this.getCurrentFocus();

        // if nothing is currently
        // focus then this will protect
        // the app from crash
        if (view != null) {

            // now assign the system
            // service to InputMethodManager
            InputMethodManager manager
                    = (InputMethodManager)
                    getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            manager
                    .hideSoftInputFromWindow(
                            view.getWindowToken(), 0);
        }
    }

    //qualifications
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