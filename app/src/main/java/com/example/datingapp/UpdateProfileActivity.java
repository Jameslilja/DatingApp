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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.datingapp.backend.User;
import com.example.datingapp.backend.UserPreferences;
import com.example.datingapp.backend.UserQualifications;
import com.example.datingapp.retrofit.RetrofitService;
import com.example.datingapp.retrofit.UserApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity {
    // används för att kunna välja kvalifikationer
    MaterialCardView selectCardUpdate;
    TextView textViewQualificationsUpdate;
    boolean [] selectedQualificationsUpdate;
    ArrayList<Integer> qualificationListUpdate = new ArrayList<>();
    String [] qualificationArrayUpdate = {"Datorkunskaper", "Kommunikation", "Problemlösning", "Tidshantering", "Överförbara kompetenser"};
    ArrayList<String> selectedQualificationsToSendUpdate;
    UserQualifications userQualificationsUpdate = new UserQualifications();
    String qualificationUpdate1;
    String qualificationUpdate2;
    String qualificationUpdate3;
    String qualificationUpdate4;
    String qualificationUpdate5;

    // preferences
    MaterialCardView selectCardPreferencesUpdate;
    TextView textViewPreferencesUpdate;
    boolean [] selectedPreferencesUpdate;
    ArrayList<Integer> preferenceListUpdate = new ArrayList<>();
    String [] preferenceArrayUpdate = {"Datorkunnig", "Bra på att kommunicera", "Bra på att lösa problem", "Hanterar tiden bra", "Pedagogisk"};
    ArrayList<String> selectedPreferencesToSendUpdate;
    UserPreferences userPreferencesUpdate = new UserPreferences();
    String preferenceUpdate1;
    String preferenceUpdate2;
    String preferenceUpdate3;
    String preferenceUpdate4;
    String preferenceUpdate5;

    //dessa används för searchView
    SearchView searchViewCityUpdate;
    ListView listViewCityUpdate;
    ArrayList<String> arrayListCitiesInSwedenUpdate;
    ArrayAdapter adapterUpdate;
    String selectedCityUpdate;

    //update password
    AlertDialog.Builder builder;
    Button buttonUpDatePrivateInfo;

    //delete account
    Button buttonDeleteAccount;

    //API TEST
    EditText editTextUsernameUpdate;
    EditText editTextFirstnameUpdate;
    EditText editTextGenderUpdate;
    EditText editTextLastnameUpdate;
    EditText getEditTextGenderUpdate;
    EditText editTextDescriptionUpdate;
    FirebaseUser firebaseUserUpdate;
    String emailUpdate;
    Long userIdUpdate;
    User userUpdate = new User();

    //övriga
    Button buttonGoToMainUpdate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        //API TEST
        editTextUsernameUpdate = findViewById(R.id.editTextUsernameUpdate);
        firebaseUserUpdate = FirebaseAuth.getInstance().getCurrentUser();
        editTextFirstnameUpdate = findViewById(R.id.editTextFirstnameUpdate);
        editTextGenderUpdate = findViewById(R.id.editTextGenderUpdate);
        editTextLastnameUpdate = findViewById(R.id.editTextLastnameUpdate);
        getEditTextGenderUpdate = findViewById(R.id.editTextGenderUpdate);
        editTextDescriptionUpdate = findViewById(R.id.editTextDescriptionUpdate);

        buttonGoToMainUpdate = findViewById(R.id.buttonGoToMainUpdate);

        buttonUpDatePrivateInfo = findViewById(R.id.buttonUpdatePrivateInfo);

        buttonDeleteAccount = findViewById(R.id.buttonDeleteAccount);

        buttonDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteAccountDialog();
            }
        });

        //UPDATE
        buttonUpDatePrivateInfo.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SearchUserAndQualificationActivity.class);
            startActivity(intent);
            finish();
        });

        buttonGoToMainUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View View) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();


                User userToSendUpdate = new User();
                userToSendUpdate.setCity(selectedCityUpdate);
                userToSendUpdate.setDescription(editTextDescriptionUpdate.getText().toString());
                userToSendUpdate.setEmail(firebaseUserUpdate.getEmail());
                userToSendUpdate.setFirstname(editTextFirstnameUpdate.getText().toString());
                userToSendUpdate.setGender(editTextGenderUpdate.getText().toString());
                userToSendUpdate.setLastname(editTextLastnameUpdate.getText().toString());
                userToSendUpdate.setUsername(editTextUsernameUpdate.getText().toString());

                RetrofitService retrofitService = new RetrofitService();
                UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

                emailUpdate = firebaseUserUpdate.getEmail();

                userApi.findUserByEmail(emailUpdate).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        userIdUpdate = response.body().getId();
                        userQualificationsUpdate.setId(userIdUpdate);
                        userPreferencesUpdate.setId(userIdUpdate);

                        userUpdate.setId(userIdUpdate);
                        userUpdate.setCity(selectedCityUpdate);
                        userUpdate.setDescription(editTextDescriptionUpdate.getText().toString());
                        userUpdate.setEmail(firebaseUserUpdate.getEmail());
                        userUpdate.setFirstname(editTextFirstnameUpdate.getText().toString());
                        userUpdate.setGender(editTextGenderUpdate.getText().toString());
                        userUpdate.setLastname(editTextLastnameUpdate.getText().toString());
                        userUpdate.setUsername(editTextUsernameUpdate.getText().toString());

                        userPreferencesUpdate.setUserId(response.body().getId());
                        userQualificationsUpdate.setUserId(response.body().getId());

                        userApi.updateUser(userUpdate).enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                System.out.println("Succeeded: " + response.body());
                                System.out.println("userUpdate: " + response.code());
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                System.out.println("Kunde ej uppdatera");
                                System.out.println("hmm" + response.code());
                                Toast.makeText(UpdateProfileActivity.this, "FAIL: ", Toast.LENGTH_SHORT).show();
                                Logger.getLogger(NewUserFirstTimeLogin.class.getName()).log(Level.SEVERE, "Error occurred: ", t);
                            }
                        });
                        if (response.body().getId() != null) {
                            userApi.updateUserPreferences(userPreferencesUpdate).enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    System.out.println("Succeeded: " + response.body());
                                    System.out.println("preferenceUpdate: " + response.code());
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    System.out.println("Kunde ej uppdatera");
                                    System.out.println("hmm" + response.code());
                                    Toast.makeText(UpdateProfileActivity.this, "FAIL: ", Toast.LENGTH_SHORT).show();
                                    Logger.getLogger(NewUserFirstTimeLogin.class.getName()).log(Level.SEVERE, "Error occurred: ", t);
                                }
                            });
                        }
                        if (response.body().getId() != null) {
                            userApi.updateUserQualifications(userQualificationsUpdate).enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    System.out.println("Succeeded: " + response.body());
                                    System.out.println("qualificationUpdate: " + response.code());
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    System.out.println("Kunde ej uppdatera");
                                    System.out.println("hmm" + response.code());
                                    Toast.makeText(UpdateProfileActivity.this, "FAIL: ", Toast.LENGTH_SHORT).show();
                                    Logger.getLogger(NewUserFirstTimeLogin.class.getName()).log(Level.SEVERE, "Error occurred: ", t);
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(UpdateProfileActivity.this, "FAIL", Toast.LENGTH_SHORT).show();
                        Logger.getLogger(NewUserFirstTimeLogin.class.getName()).log(Level.SEVERE, "Error occurred", t);
                    }
                });
        }
        });

        //qualifications
        selectCardUpdate = findViewById(R.id.selectCardUpdate);
        textViewQualificationsUpdate = findViewById(R.id.textViewQualificationsUpdate);
        selectedQualificationsUpdate = new boolean[qualificationArrayUpdate.length];

        selectCardUpdate.setOnClickListener(view -> showQualificationsDialog());

        //preferences
        selectCardPreferencesUpdate = findViewById(R.id.selectCardPreferencesUpdate);
        textViewPreferencesUpdate = findViewById(R.id.textViewPreferencesUpdate);
        selectedPreferencesUpdate = new boolean[preferenceArrayUpdate.length];

        selectCardPreferencesUpdate.setOnClickListener(view -> {
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
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    //qualifications
    private void showQualificationsDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfileActivity.this);

        builder.setTitle("Välj kvalifikationer");
        builder.setCancelable(false);

        builder.setMultiChoiceItems(qualificationArrayUpdate, selectedQualificationsUpdate, (dialogInterface, which, b) -> {
            selectedQualificationsToSendUpdate = new ArrayList<>();
            if (b){
                qualificationListUpdate.add(which);
                if (selectedQualificationsUpdate[0]) {
                    qualificationUpdate1 = qualificationArrayUpdate[0];
                    userQualificationsUpdate.setQ1(qualificationArrayUpdate[0]);
                    selectedQualificationsToSendUpdate.add(qualificationArrayUpdate[0]);
                }
                if (selectedQualificationsUpdate[1]) {
                    qualificationUpdate2 = qualificationArrayUpdate[1];
                    userQualificationsUpdate.setQ2(qualificationArrayUpdate[1]);
                    selectedQualificationsToSendUpdate.add(qualificationArrayUpdate[1]);
                }
                if (selectedQualificationsUpdate[2]) {
                    qualificationUpdate3 = qualificationArrayUpdate[2];
                    userQualificationsUpdate.setQ3(qualificationArrayUpdate[2]);
                    selectedQualificationsToSendUpdate.add(qualificationArrayUpdate[2]);
                }
                if (selectedQualificationsUpdate[3]) {
                    qualificationUpdate4 = qualificationArrayUpdate[3];
                    userQualificationsUpdate.setQ4(qualificationArrayUpdate[3]);
                    selectedQualificationsToSendUpdate.add(qualificationArrayUpdate[3]);
                }
                if (selectedQualificationsUpdate[4]) {
                    qualificationUpdate5 = qualificationArrayUpdate[4];
                    userQualificationsUpdate.setQ5(qualificationArrayUpdate[4]);
                    selectedQualificationsToSendUpdate.add(qualificationArrayUpdate[4]);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfileActivity.this);

        builder.setTitle("Välj preferenser");
        builder.setCancelable(false);

        builder.setMultiChoiceItems(preferenceArrayUpdate, selectedPreferencesUpdate, (dialogInterface, which, b) -> {
            selectedPreferencesToSendUpdate = new ArrayList<>();
            if (b){
                preferenceListUpdate.add(which);
                if (selectedPreferencesUpdate[0]) {
                    preferenceUpdate1 = preferenceArrayUpdate[0];
                    userPreferencesUpdate.setP1(preferenceArrayUpdate[0]);
                    selectedPreferencesToSendUpdate.add(preferenceArrayUpdate[0]);
                }
                if (selectedPreferencesUpdate[1]) {
                    preferenceUpdate2 = preferenceArrayUpdate[1];
                    userPreferencesUpdate.setP2(preferenceArrayUpdate[1]);
                    selectedPreferencesToSendUpdate.add(preferenceArrayUpdate[1]);
                }
                if (selectedPreferencesUpdate[2]) {
                    preferenceUpdate3 = preferenceArrayUpdate[2];
                    userPreferencesUpdate.setP3(preferenceArrayUpdate[2]);
                    selectedPreferencesToSendUpdate.add(preferenceArrayUpdate[2]);
                }
                if (selectedPreferencesUpdate[3]) {
                    preferenceUpdate4 = preferenceArrayUpdate[3];
                    userPreferencesUpdate.setP4(preferenceArrayUpdate[3]);
                    selectedPreferencesToSendUpdate.add(preferenceArrayUpdate[0]);
                }
                if (selectedPreferencesUpdate[4]) {
                    preferenceUpdate5 = preferenceArrayUpdate[4];
                    userPreferencesUpdate.setP5(preferenceArrayUpdate[4]);
                    selectedPreferencesToSendUpdate.add(preferenceArrayUpdate[4]);
                }
            } else {
                preferenceListUpdate.remove(which);
            }
        }).setPositiveButton("Välj", (dialog, which) -> {

            //create string builder
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < preferenceListUpdate.size(); i++){

                stringBuilder.append(preferenceArrayUpdate[preferenceListUpdate.get(i)]);

                //check condition
                if (i != preferenceListUpdate.size() - 1){
                    //when i is not equal to qualification list size
                    //then add a comma
                    stringBuilder.append(", ");
                }

                //setting selected qualifications to textView
                textViewPreferencesUpdate.setText(stringBuilder.toString());
            }
        }).setNegativeButton("Avbryt", (dialog, which) -> dialog.dismiss()).setNeutralButton("Avmarkera alla", (dialog, which) -> {
            //clearing all selected qualifications on click
            for (int i = 0; i < selectedPreferencesUpdate.length; i ++){
                selectedPreferencesUpdate[i] = false;

                preferenceListUpdate.clear();
                textViewPreferencesUpdate.setText("");
            }
        });
        builder.show();
    }

    // tar bort kontot från firebase men inte från databasen, där det ännu inte existerar
    private void showDeleteAccountDialog() {
        builder = new AlertDialog.Builder(this);

        builder.setMessage("Vill du verkligen radera kontot?")
                .setCancelable(false)
                .setPositiveButton("Radera mitt konto!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        firebaseUserUpdate.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Konto borttaget",
                                                    Toast.LENGTH_SHORT).show();
                                        }}
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "Konto borttaget",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });

                        //Login eller Register istället för Splash screen?
                        Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
                        startActivity(intent);
                        finish();

                        //inte säker på om detta behövs
                        FirebaseAuth.getInstance().signOut();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.show();
    }
}