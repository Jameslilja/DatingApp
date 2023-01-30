package com.example.datingapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

public class UpdateProfile extends AppCompatActivity {
    // qualifications
    MaterialCardView selectCardUpdate;
    TextView textViewQualificationsUpdate;
    boolean [] selectedQualificationsUpdate;
    ArrayList<Integer> qualificationListUpdate = new ArrayList<>();
    String [] qualificationArrayUpdate = {"Datorkunskaper", "Kommunikation", "Problemlösning", "Tidshantering", "Överförbara kompetenser"};
    ArrayList<String> selectedQualificationsToSend;

    // preferences
    MaterialCardView selectCardPreferences;
    TextView textViewPreferences;
    boolean [] selectedPreferences;
    ArrayList<Integer> preferenceList = new ArrayList<>();
    String [] preferenceArray = {"Datorkunnig", "Bra på att kommunicera", "Bra på att lösa problem", "Hanterar tiden bra", "Pedagogisk"};
    ArrayList<String> selectedPreferencesToSend;

    // search view
    SearchView searchViewCityUpdate;
    ListView listViewCityUpdate;
    ArrayList<String> arrayListCitiesInSwedenUpdate;
    ArrayAdapter adapterUpdate;
    String selectedCityUpdate;

    //updatePasswordOrDelete, work in progress
    AlertDialog.Builder builder;
    Button buttonUpDatePrivateInfo;

    // api post, work in progress
    private TextView responseTV;
    private ProgressBar loadingPB;
    EditText editTextUsername;
    EditText editTextFirstname;
    EditText editTextLastname;
    EditText editTextDescription;
    FirebaseUser user;

    Button buttonGoToMainUpdate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        //API TEST
        responseTV = findViewById(R.id.idTVResponse);
        loadingPB = findViewById(R.id.idLoadingPB);
        editTextUsername = findViewById(R.id.editTextUsernameUpdate);
        user = FirebaseAuth.getInstance().getCurrentUser();
        editTextFirstname = findViewById(R.id.editTextFirstnameUpdate);
        editTextLastname = findViewById(R.id.editTextLastnameUpdate);
        editTextDescription = findViewById(R.id.editTextDescriptionUpdate);


        buttonGoToMainUpdate = findViewById(R.id.buttonGoToMainUpdate);

        buttonUpDatePrivateInfo = findViewById(R.id.buttonUpdatePrivateInfo);

        buttonUpDatePrivateInfo.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), TestEmpty.class);
            startActivity(intent);
            finish();
        });


        buttonGoToMainUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View View) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

            System.out.println("FIREBASE USER ID" + user.getUid()); //fungerar
            System.out.println("USERNAME: " + editTextUsername.getText().toString()); //fungerar
            System.out.println("EMAIL: " + user.getEmail()); //fungerar
            System.out.println("FIRST NAME: " + editTextFirstname.getText().toString()); //fungerar
            System.out.println("LAST NAME: " + editTextLastname.getText().toString()); //fungerar
            System.out.println("CITY: " + selectedCityUpdate); //fungerar
            System.out.println("DESCRIPTION: " + editTextDescription.getText().toString()); //fungerar
            System.out.println("QUALIFICATIONS: " + selectedQualificationsToSend); //fungerar
            System.out.println("PREFERENCES: " + selectedPreferencesToSend); //fungerar

            //API TEST
            postData(user.getUid(),
                    editTextUsername.getText().toString(), //
                    user.getEmail(), //
                    editTextFirstname.getText().toString(), //
                    editTextLastname.getText().toString(), //
                    selectedCityUpdate, //
                    editTextDescription.getText().toString(), //
                    String.valueOf(selectedQualificationsToSend),
                    String.valueOf(selectedPreferencesToSend)); //
        }
        });

        //qualifications
        selectCardUpdate = findViewById(R.id.selectCardUpdate);
        textViewQualificationsUpdate = findViewById(R.id.textViewQualificationsUpdate);
        selectedQualificationsUpdate = new boolean[qualificationArrayUpdate.length];

        selectCardUpdate.setOnClickListener(view -> showQualificationsDialog());

        //preferences
        selectCardPreferences = findViewById(R.id.selectCardPreferencesUpdate);
        textViewPreferences = findViewById(R.id.textViewPreferencesUpdate);
        selectedPreferences = new boolean[preferenceArray.length];

        selectCardPreferences.setOnClickListener(view -> {
            showPreferencesUpdateDialog();
        });

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
            selectedQualificationsToSend = new ArrayList<>();
            if (b){
                qualificationListUpdate.add(which);
                if (selectedQualificationsUpdate[0]) {
                    selectedQualificationsToSend.add(qualificationArrayUpdate[0]);
                }
                if (selectedQualificationsUpdate[1]) {
                    selectedQualificationsToSend.add(qualificationArrayUpdate[1]);
                }
                if (selectedQualificationsUpdate[2]) {
                    selectedQualificationsToSend.add(qualificationArrayUpdate[2]);
                }
                if (selectedQualificationsUpdate[3]) {
                    selectedQualificationsToSend.add(qualificationArrayUpdate[3]);
                }
                if (selectedQualificationsUpdate[4]) {
                    selectedQualificationsToSend.add(qualificationArrayUpdate[4]);
                }
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

    private void showPreferencesUpdateDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfile.this);

        builder.setTitle("Välj preferenser");
        builder.setCancelable(false);

        builder.setMultiChoiceItems(preferenceArray, selectedPreferences, (dialogInterface, which, b) -> {
            selectedPreferencesToSend = new ArrayList<>();
            if (b){
                preferenceList.add(which);
                if (selectedPreferences[0]) {
                    selectedPreferencesToSend.add(preferenceArray[0]);
                }
                if (selectedPreferences[1]) {
                    selectedPreferencesToSend.add(preferenceArray[1]);
                }
                if (selectedPreferences[2]) {
                    selectedPreferencesToSend.add(preferenceArray[2]);
                }
                if (selectedPreferences[3]) {
                    selectedPreferencesToSend.add(preferenceArray[3]);
                }
                if (selectedPreferences[4]) {
                    selectedPreferencesToSend.add(preferenceArray[4]);
                }
            } else {
                preferenceList.remove(which);
            }
        }).setPositiveButton("Välj", (dialog, which) -> {

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
        }).setNegativeButton("Avbryt", (dialog, which) -> dialog.dismiss()).setNeutralButton("Avmarkera alla", (dialog, which) -> {
            //clearing all selected qualifications on click
            for (int i = 0; i < selectedPreferences.length; i ++){
                selectedPreferences[i] = false;

                preferenceList.clear();
                textViewPreferences.setText("");
            }
        });
        builder.show();
    }

    private void postData(String user_uid, String username, String email, String firstname, String lastname, String city, String description, String qualifications, String preferences) {

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
        DataModal modal = new DataModal(user_uid, username, email, firstname, lastname, city, description, qualifications, preferences);

        // calling a method to create a post and passing our modal class.
        Call<DataModal> call = retrofitAPI.createPost(modal);

        // on below line we are executing our method.
        call.enqueue(new Callback<DataModal>() {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                // this method is called when we get response from our api.
                Toast.makeText(UpdateProfile.this, "Data added to API", Toast.LENGTH_SHORT).show();

                // below line is for hiding our progress bar.
                loadingPB.setVisibility(View.GONE);

                // we are getting response from our body
                // and passing it to our modal class.
                DataModal responseFromAPI = response.body();

                // on below line we are getting our data from modal class and adding it to our string.
                String responseString = "Response Code : " + response.code() + "\nUser_uid : " + responseFromAPI.getUserUid() + response.code() + "\nUsername : " + responseFromAPI.getUsername() + "\n" + "Email : " + responseFromAPI.getEmail() + "\n" + "First name : " + responseFromAPI.getFirstname() + "\n" + "Last name : " + responseFromAPI.getCity() + "\n" + "Description : " + responseFromAPI.getDescription()  + "\n" + "Qualifications : " + responseFromAPI.getQualifications() + "\n" + "Preferences : " + responseFromAPI.getPreferences();

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