package com.example.datingapp;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.datingapp.backend.User;
import com.example.datingapp.backend.UserPreferences;
import com.example.datingapp.retrofit.RetrofitService;
import com.example.datingapp.retrofit.UserApi;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewUserFirstTimeLogin extends AppCompatActivity {
    // qualifications
    MaterialCardView selectCard;
    TextView textViewQualifications;
    boolean [] selectedQualifications;
    ArrayList<Integer> qualificationList = new ArrayList<>();
    String [] qualificationArray = {"Datorkunskaper", "Kommunikation", "Problemlösning", "Tidshantering", "Överförbara kompetenser"};
    ArrayList<String> selectedQualificationsToSend;
    /*
    String qualifications1;
    String qualifications2;
    String qualifications3;
    String qualifications4;
    String qualifications5;
     */

    // preferences
    MaterialCardView selectCardPreferences;
    TextView textViewPreferences;
    boolean [] selectedPreferences;
    ArrayList<Integer> preferenceList = new ArrayList<>();
    String [] preferenceArray = {"Datorkunnig", "Bra på att kommunicera", "Bra på att lösa problem", "Hanterar tiden bra", "Pedagogisk"};
    ArrayList<String> selectedPreferencesToSend;
    String preference1 = "EJ VALD";
    String preference2 = "EJ VALD";
    String preference3 = "EJ VALD";
    String preference4 = "EJ VALD";
    String preference5= "EJ VALD";

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
    EditText editTextGender;
    EditText editTextDescription;
    String userPassword = "Doesn't matter";
    FirebaseUser user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_first_time_login);

        buttonGoToMain = findViewById(R.id.buttonGoToMain);

        editTextUsername = findViewById(R.id.editTextUsername);
        user = FirebaseAuth.getInstance().getCurrentUser();
        editTextFirstname = findViewById(R.id.editTextFirstname);
        editTextLastname = findViewById(R.id.editTextLastname);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextGender = findViewById(R.id.editTextGender);

        //SKA ÄVEN SKICKA TILL BACKEND OCH DATABAS
        buttonGoToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();

                System.out.println("FIREBASE USER ID" + user.getUid()); //fungerar
                System.out.println("USERNAME: " + editTextUsername.getText().toString()); //fungerar
                System.out.println("EMAIL: " + user.getEmail()); //fungerar
                System.out.println("FIRST NAME: " + editTextFirstname.getText().toString()); //fungerar
                System.out.println("LAST NAME: " + editTextLastname.getText().toString()); //fungerar
                System.out.println("CITY: " + selectedCity); //fungerar
                System.out.println("DESCRIPTION: " + editTextDescription.getText().toString()); //fungerar
                System.out.println("QUALIFICATIONS: " + selectedQualificationsToSend); //fungerar
                System.out.println("PREFERENCES: " + selectedPreferencesToSend); //fungerar
                System.out.println("GENDER: " + editTextGender.getText().toString()); //bör fungera

                RetrofitService retrofitService = new RetrofitService();
                UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

                User userToSend = new User();

                String firebase_id = user.getUid();
                String description = editTextDescription.getText().toString();
                String email = user.getEmail();
                String firstname = editTextFirstname.getText().toString();
                String gender = editTextGender.getText().toString();
                String lastName = editTextLastname.getText().toString();
                String password = "LUL";
                String username = editTextUsername.getText().toString();

                userToSend.setFirebase_id(firebase_id);
                userToSend.setCity(selectedCity);
                userToSend.setDescription(description);
                userToSend.setEmail(email);
                userToSend.setFirstname(firstname);
                userToSend.setGender(gender);
                userToSend.setLastname(lastName);
                userToSend.setPassword(password);
                userToSend.setUsername(username);


                /*
                if (preference1.isEmpty()){
                    preference1 = "EJ VALD";
                }
                if (preference2.isEmpty()){
                    preference2 = "EJ VALD";
                }
                if (preference3.isEmpty()){
                    preference3 = "EJ VALD";
                }
                if (preference4.isEmpty()){
                    preference4 = "EJ VALD";
                }
                if (preference5.isEmpty()){
                    preference5 = "EJ VALD";
                }
                 */

                /*
                if (qualifications1.isEmpty()){
                    qualifications1 = "EJ VALD";
                }
                if (qualifications2.isEmpty()){
                    qualifications2 = "EJ VALD";
                }
                if (qualifications3.isEmpty()){
                    qualifications3 = "EJ VALD";
                }
                if (qualifications4.isEmpty()){
                    qualifications4 = "EJ VALD";
                }
                if (qualifications5.isEmpty()){
                    qualifications5 = "EJ VALD";
                } */

                UserPreferences userPreferencesToSend = new UserPreferences();
                userPreferencesToSend.setUserId(user.getUid());
                userPreferencesToSend.setP1(preference1);
                userPreferencesToSend.setP2(preference2);
                userPreferencesToSend.setP3(preference3);
                userPreferencesToSend.setP4(preference4);
                userPreferencesToSend.setP5(preference5);

                System.out.println("p1: " + preference1);
                System.out.println("p2: " + preference2);
                System.out.println("p3: " + preference3);
                System.out.println("p4: " + preference4);
                System.out.println("p5: " + preference5);

                userApi.savePreferences(userPreferencesToSend).enqueue(new Callback<UserPreferences>() {
                    @Override
                    public void onResponse(Call<UserPreferences> call, Response<UserPreferences> response) {
                        System.out.println("PREFERENCES ADDED TO DATABASE");
                        Toast.makeText(NewUserFirstTimeLogin.this, "FUNGERADE", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<UserPreferences> call, Throwable t) {
                        System.out.println("PREFERENCE FAIL");
                        Toast.makeText(NewUserFirstTimeLogin.this, "FAIL", Toast.LENGTH_SHORT).show();
                        Logger.getLogger(NewUserFirstTimeLogin.class.getName()).log(Level.SEVERE, "Error occurred", t);
                    }
                });

                userApi.registerUser(userToSend).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        System.out.println("USER ADDED TO DATABASE");
                        Toast.makeText(NewUserFirstTimeLogin.this, "FUNGERADE", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        System.out.println("USER FAILLLL!!");
                        Toast.makeText(NewUserFirstTimeLogin.this, "FAIL", Toast.LENGTH_SHORT).show();
                        Logger.getLogger(NewUserFirstTimeLogin.class.getName()).log(Level.SEVERE, "Error occurred", t);
                    }
                });

            }

        });

        //qualifications
        selectCard = findViewById(R.id.selectCard);
        textViewQualifications = findViewById(R.id.textViewQualifications);
        selectedQualifications = new boolean[qualificationArray.length];

        selectCard.setOnClickListener(view -> {

            showQualificationsDialog();
        });

        //preferences
        selectCardPreferences = findViewById(R.id.selectCardPreferences);
        textViewPreferences = findViewById(R.id.textViewPreferences);
        selectedPreferences = new boolean[preferenceArray.length];

        selectCardPreferences.setOnClickListener(view -> {
            showPreferencesDialog();
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

    private void showQualificationsDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(NewUserFirstTimeLogin.this);

        builder.setTitle("Välj kvalifikationer");
        builder.setCancelable(false);

        builder.setMultiChoiceItems(qualificationArray, selectedQualifications, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which, boolean b) {
                selectedQualificationsToSend = new ArrayList<>();
                if (b){
                    qualificationList.add(which);
                    if (selectedQualifications[0]) {
                        //qualifications1 = qualificationArray[0];
                        selectedQualificationsToSend.add(qualificationArray[0]);
                    }
                    if (selectedQualifications[1]) {
                        //qualifications2 = qualificationArray[1];
                        selectedQualificationsToSend.add(qualificationArray[1]);
                    }
                    if (selectedQualifications[2]) {
                        //qualifications3 = qualificationArray[2];
                        selectedQualificationsToSend.add(qualificationArray[2]);
                    }
                    if (selectedQualifications[3]) {
                        //qualifications4 = qualificationArray[3];
                        selectedQualificationsToSend.add(qualificationArray[3]);
                    }
                    if (selectedQualifications[4]) {
                        //qualifications5 = qualificationArray[4];
                        selectedQualificationsToSend.add(qualificationArray[4]);
                    }
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

    private void showPreferencesDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(NewUserFirstTimeLogin.this);

        builder.setTitle("Välj preferenser");
        builder.setCancelable(false);

        builder.setMultiChoiceItems(preferenceArray, selectedPreferences, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which, boolean b) {
                selectedPreferencesToSend = new ArrayList<>();
                if (b){
                    preferenceList.add(which);
                    if (selectedPreferences[0]) {
                        preference1 = preferenceArray[0];
                        selectedPreferencesToSend.add(preferenceArray[0]);
                    }
                    if (selectedPreferences[1]) {
                        preference2 = preferenceArray[1];
                        selectedPreferencesToSend.add(preferenceArray[1]);
                    }
                    if (selectedPreferences[2]) {
                        preference3 = preferenceArray[2];
                        selectedPreferencesToSend.add(preferenceArray[2]);
                    }
                    if (selectedPreferences[3]) {
                        preference4 = preferenceArray[3];
                        selectedPreferencesToSend.add(preferenceArray[3]);
                    }
                    if (selectedPreferences[4]) {
                        preference5 = preferenceArray[4];
                        selectedPreferencesToSend.add(preferenceArray[4]);
                    }
                } else {
                    preferenceList.remove(which);
                }
            }
        }).setPositiveButton("Välj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //create string builder
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < preferenceList.size(); i++){
                    stringBuilder.append(preferenceArray[preferenceList.get(i)]);

                    //check condition
                    if (i != preferenceList.size() - 1){
                        //when i is not equal to qualification list size
                        //then add a comma
                        stringBuilder.append(", ");
                    }
                    //setting selected qualifications to textView
                    textViewPreferences.setText(stringBuilder.toString());
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
                for (int i = 0; i < selectedPreferences.length; i ++){
                    selectedPreferences[i] = false;

                    preferenceList.clear();
                    textViewPreferences.setText("");
                }
            }
        });
        builder.show();
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
}