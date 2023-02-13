package com.example.datingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datingapp.backend.Qualification;
import com.example.datingapp.backend.User;
import com.example.datingapp.retrofit.RetrofitService;
import com.example.datingapp.retrofit.UserApi;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//Söka efter användare baserat på användarnamn eller bara kvalifikationer.
// (Dropdown för kvalifikationer och sökfunktion för användarnamn?)
public class SearchUserAndQualificationActivity extends AppCompatActivity {
    //search
    SearchView searchViewUsers;
    ListView listViewUsers;
    ArrayAdapter adapter;
    String selectedUser;
    ArrayList<User> arrayListUsers;
    Button swapSearchButton;
    TextView textViewSwappedSearchText;
    boolean isSwapButtonPressed = false; //false innebär sök efter användare och och true innebär sök på qualification
    ArrayList<Qualification> arrayListQualification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user_and_qualification);

        searchViewUsers = findViewById(R.id.searchViewUsers);
        listViewUsers = findViewById(R.id.listViewSearchUsers);
        swapSearchButton = findViewById(R.id.swapSearchButton);
        textViewSwappedSearchText = findViewById(R.id.textViewSwappedSearchText);

        //showQualificationsSearchDialog(); //behövs för att välja kvalifikationer
        swapSearch();
        initializeSearch();
    }

    private void initializeSearch(){
        if (!isSwapButtonPressed){
            searchUsers();
        } else {
            searchQualifications();
        }
    }

    private void swapSearch(){
        swapSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSwapButtonPressed) {
                    textViewSwappedSearchText.setText("Sök efter användare");
                    isSwapButtonPressed = false;
                } else {
                    textViewSwappedSearchText.setText("Sök baserat på kvalifikationer");
                    isSwapButtonPressed = true;
                }
                initializeSearch();
            }
        });
    }

    private void searchUsers(){
        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        userApi.findAllUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                arrayListUsers = new ArrayList<>();

                for (int i = 0; i < response.body().size(); i++){
                    User userTest = new User();
                    userTest.setId(response.body().get(i).getId());
                    userTest.setCity(response.body().get(i).getCity());
                    userTest.setDescription(response.body().get(i).getDescription());
                    userTest.setEmail(response.body().get(i).getEmail());
                    userTest.setFirstname(response.body().get(i).getFirstname());
                    userTest.setGender(response.body().get(i).getGender());
                    userTest.setLastname(response.body().get(i).getLastname());
                    userTest.setUsername(response.body().get(i).getUsername());

                    arrayListUsers.add(userTest);
                }

                initializeAdapter();

                System.out.println("ARRAYTEST" + arrayListUsers);
                System.out.println("ARRAYTEST" + arrayListUsers.get(1));

                System.out.println("hmm" + response.code());
                System.out.println("RESPONSE BODY FIRST TIME: " + response.body());

                System.out.println("hmmm" + response.body().getClass());

                System.out.println("FIND ALL USERS PASSED");
                System.out.println("*****");
                System.out.println("*****");
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(SearchUserAndQualificationActivity.this, "FAIL", Toast.LENGTH_SHORT).show();
                Logger.getLogger(NewUserFirstTimeLogin.class.getName()).log(Level.SEVERE, "Error occurred", t);
            }
        });



        searchViewUsers.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                listViewUsers.setVisibility(View.VISIBLE);
                return false;
            }
        });

        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedUser = listViewUsers.getItemAtPosition(i).toString();

                listViewUsers.setVisibility(View.INVISIBLE);

                searchViewUsers.setQueryHint(selectedUser);

                listViewUsers.setSelection(i);


                Intent intent = new Intent(getApplicationContext(), SecondProfileActivity.class);
                intent.putExtra("selectedUsername", selectedUser);
                startActivity(intent);
                finish();

                Toast.makeText(getApplicationContext(), selectedUser + " valt.", Toast.LENGTH_LONG).show();
                System.out.println("ANVÄNDARE VALD ÄR: " + selectedUser);
            }
        });
    }

    private void searchQualifications(){
        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        userApi.findAllQualification().enqueue(new Callback<List<Qualification>>() {
            @Override
            public void onResponse(Call<List<Qualification>> call, Response<List<Qualification>> response) {
                arrayListQualification = new ArrayList<>();

                System.out.println("felkod: " + response.code());
                System.out.println("hmm" + response.body());
                System.out.println(response.message());
                System.out.println(response.raw());


                for (int i = 0; i < response.body().size(); i++){
                    Qualification qualificationTest = new Qualification();
                    qualificationTest.setId(response.body().get(i).getId());
                    qualificationTest.setQualification(response.body().get(i).getQualification());

                    arrayListQualification.add(qualificationTest);
                }
                initializeAdapter();

                System.out.println("outside for-loop: " + arrayListQualification.get(0));
                System.out.println("outside for-loop: " + arrayListQualification.get(1));
                System.out.println("outside for-loop: " + arrayListQualification.get(2));
                System.out.println("outside for-loop: " + arrayListQualification.get(3));
                System.out.println("outside for-loop: " + arrayListQualification.get(4));
            }

            @Override
            public void onFailure(Call<List<Qualification>> call, Throwable t) {
                Logger.getLogger(NewUserFirstTimeLogin.class.getName()).log(Level.SEVERE, "Error occurred", t);
            }
        });
    }

    private void initializeAdapter() {

        if (!isSwapButtonPressed)
        {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayListUsers);
        } else {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayListQualification);
        }

        listViewUsers.setAdapter(adapter);
    }

    /*
    private void showQualificationsSearchDialog() {

        // assign variable
        textViewQualificationsSearch = findViewById(R.id.textViewSelectSearchUsersQualifications);

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
     */
}