package com.example.datingapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Arrays;

public class UpdateProfile extends AppCompatActivity {
    //selectCard, textViewQualifications, selectedQualifications, qualificationsList och qualificationArray
    // används för att kunna välja kvalifikationer
    MaterialCardView selectCardUpdate;
    TextView textViewQualificationsUpdate;
    boolean [] selectedQualificationsUpdate;
    ArrayList<Integer> qualificationListUpdate = new ArrayList<>();
    String [] qualificationArrayUpdate = {"Datorkunskaper", "Kommunikation", "Problemlösning", "Tidshantering", "Överförbara kompetenser"};

    //dessa används för searchView
    SearchView searchViewCityUpdate;
    ListView listViewCityUpdate;
    ArrayList<String> arrayListCitiesInSwedenUpdate;
    ArrayAdapter adapterUpdate;
    String selectedCityUpdate;

    //updatePasswordOrDelete
    AlertDialog.Builder builder;
    Button buttonUpDatePrivateInfo;

    //övriga
    Button buttonGoToMainUpdate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        buttonGoToMainUpdate = findViewById(R.id.buttonGoToMainUpdate);

        buttonUpDatePrivateInfo = findViewById(R.id.buttonUpdatePrivateInfo);

        buttonUpDatePrivateInfo.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), TestEmpty.class);
            startActivity(intent);
            finish();
        });


        buttonGoToMainUpdate.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });

        //qualifications
        selectCardUpdate = findViewById(R.id.selectCardUpdate);
        textViewQualificationsUpdate = findViewById(R.id.textViewQualificationsUpdate);
        selectedQualificationsUpdate = new boolean[qualificationArrayUpdate.length];

        //qualifications
        selectCardUpdate.setOnClickListener(view -> showQualificationsDialog());

        //searchView
        searchViewCityUpdate = findViewById(R.id.searchCity);
        listViewCityUpdate = findViewById(R.id.listViewSearchCity);

        arrayListCitiesInSwedenUpdate = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.citiesInSwedenArray)));

        adapterUpdate = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayListCitiesInSwedenUpdate);
        listViewCityUpdate.setAdapter(adapterUpdate);

        searchViewCityUpdate.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                adapterUpdate.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterUpdate.getFilter().filter(newText);
                listViewCityUpdate.setVisibility(View.VISIBLE);
                return false;
            }
        });

        listViewCityUpdate.setOnItemClickListener((adapterView, view, i, l) -> {
            selectedCityUpdate = listViewCityUpdate.getItemAtPosition(i).toString();

            listViewCityUpdate.setVisibility(View.INVISIBLE);

            searchViewCityUpdate.setQueryHint(selectedCityUpdate);

            listViewCityUpdate.setSelection(i);

            closeKeyboard();

            Toast.makeText(getApplicationContext(), selectedCityUpdate + " valt.", Toast.LENGTH_LONG).show();

        });

        //UpdateOrChangePassword
        buttonUpDatePrivateInfo = (Button) findViewById(R.id.buttonUpdatePrivateInfo);
        builder = new AlertDialog.Builder(this);
        buttonUpDatePrivateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Uncomment the below code to Set the message and title from the strings.xml file
                builder.setMessage(R.string.dialog_messageUpdatePassword) .setTitle(R.string.dialog_titleUpdatePassword);

                //Setting message manually and performing action on button click
                builder.setMessage("Vill du stänga rutan?!")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getApplicationContext(),"Du har klickat på 'JA'",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),"Du har klickat på 'NEJ'",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Hejsan");
                alert.show();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfile.this);

        builder.setTitle("Välj kvalifikationer");
        builder.setCancelable(false);

        builder.setMultiChoiceItems(qualificationArrayUpdate, selectedQualificationsUpdate, (dialogInterface, which, b) -> {
            if (b){
                qualificationListUpdate.add(which);
            } else {
                qualificationListUpdate.remove(which);
            }
        }).setPositiveButton("Välj", (dialog, which) -> {

            //create string builder
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < qualificationListUpdate.size(); i++){

                stringBuilder.append(qualificationArrayUpdate[qualificationListUpdate.get(i)]);

                //check condition
                if (i != qualificationListUpdate.size() - 1){
                    //when i is not equal to qualification list size
                    //then add a comma
                    stringBuilder.append(", ");
                }

                //setting selected qualifications to textView
                textViewQualificationsUpdate.setText(stringBuilder.toString());
            }
        }).setNegativeButton("Avbryt", (dialog, which) -> dialog.dismiss()).setNeutralButton("Avmarkera alla", (dialog, which) -> {
            //clearing all selected qualifications on click
            for (int i = 0; i < selectedQualificationsUpdate.length; i ++){
                selectedQualificationsUpdate[i] = false;

                qualificationListUpdate.clear();
                textViewQualificationsUpdate.setText("");
            }
        });
        builder.show();
    }


}