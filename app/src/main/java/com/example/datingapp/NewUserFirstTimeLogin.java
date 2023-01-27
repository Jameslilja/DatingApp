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
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    EditText editTextUsername;
    EditText editTextFirstname;
    EditText editTextLastname;
    EditText editTextDescription;
    FirebaseUser user;

    //API TEST
    private TextView responseTV;
    private ProgressBar loadingPB;
    ArrayList<String> adapterQualifications;
    ArrayList<String> qualifications;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_first_time_login);

        //API TEST
        responseTV = findViewById(R.id.idTVResponse);
        loadingPB = findViewById(R.id.idLoadingPB);

        buttonGoToMain = findViewById(R.id.buttonGoToMain);

        editTextUsername = findViewById(R.id.editTextUsername);
        user = FirebaseAuth.getInstance().getCurrentUser();
        editTextFirstname = findViewById(R.id.editTextFirstname);
        editTextLastname = findViewById(R.id.editTextLastname);
        editTextDescription = findViewById(R.id.editTextDescription);



        //city finns längre ner men inte här, ändra postData till onClicken längre ner?


        //SKA ÄVEN SKICKA TILL BACKEND OCH DATABAS
        buttonGoToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();

                System.out.println("******************");
                System.out.println("******************");

                System.out.println("FIREBASE USER ID" + user.getUid());
                System.out.println("USERNAME: " + editTextUsername.getText().toString());
                System.out.println("EMAIL: " + user.getEmail());
                System.out.println("FIRST NAME: " + editTextFirstname.getText().toString());
                System.out.println("LAST NAME: " + editTextLastname.getText().toString());
                System.out.println("CITY: " + selectedCity);
                System.out.println("DESCRIPTION: " + editTextDescription.getText().toString());
                System.out.println("QUALIFICATIONS 1: " + qualifications);
                System.out.println("QUALIFICATIONS 2: " + qualificationArray);
                System.out.println("QUALIFICATIONS 3: " + qualificationList); //GER POSITIONEN I ARRAYEN

                System.out.println("******************");
                System.out.println("******************");

                //API TEST
                postData(user.getUid(),
                        editTextUsername.getText().toString(), //Fungerar
                        user.getEmail(), //fungerar
                        editTextFirstname.getText().toString(), //Fungerar
                        editTextLastname.getText().toString(), //Fungerar
                        selectedCity, //Fungerar
                        editTextDescription.getText().toString(), //Fungerar
                        qualifications); //Fungerar ej
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

    private void postData(String user_uid, String username, String email, String firstname, String lastname, String city, String description, ArrayList<String> qualifications) {

        // below line is for displaying our progress bar.
        loadingPB.setVisibility(View.VISIBLE);

        // on below line we are creating a retrofit
        // builder and passing our base url
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/api/")
                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // at last we are building our retrofit builder.
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        // passing data from our text fields to our modal class.
        DataModal modal = new DataModal(user_uid, username, email, firstname, lastname, city, description, qualifications);

        // calling a method to create a post and passing our modal class.
        Call<DataModal> call = retrofitAPI.createPost(modal);

        // on below line we are executing our method.
        call.enqueue(new Callback<DataModal>() {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                // this method is called when we get response from our api.
                Toast.makeText(NewUserFirstTimeLogin.this, "Data added to API", Toast.LENGTH_SHORT).show();

                // below line is for hiding our progress bar.
                loadingPB.setVisibility(View.GONE);

                // we are getting response from our body
                // and passing it to our modal class.
                DataModal responseFromAPI = response.body();

                // on below line we are getting our data from modal class and adding it to our string.
                String responseString = "Response Code : " + response.code() + "\nUser_uid : " + responseFromAPI.getUserUid() + response.code() + "\nUsername : " + responseFromAPI.getUsername() + "\n" + "Email : " + responseFromAPI.getEmail() + "\n" + "First name : " + responseFromAPI.getFirstname() + "\n" + "Last name : " + responseFromAPI.getCity() + "\n" + "Description : " + responseFromAPI.getDescription()  + "\n" + "Qualifications : " + responseFromAPI.getQualifications();

                // below line we are setting our
                // string to our text view.
                responseTV.setText(responseString);
            }

            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {
                // setting text to our text view when
                // we get error response from API.
                responseTV.setText("Error found is : " + t.getMessage());
            }
        });
    }

}