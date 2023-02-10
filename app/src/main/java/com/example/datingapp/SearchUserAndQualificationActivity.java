package com.example.datingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    // qualifications
    //TextView textViewQualificationsSearch; //behövs för att välja kvalifikationer
    //boolean[] selectedQualificationsSearch; //behövs för att välja kvalifikationer
    ArrayList<Integer> qualificationsSearchList = new ArrayList<>();
    String[] qualificationsSearchArray = {"Datorkunskaper", "Kommunikation", "Problemlösning", "Tidshantering", "Överförbara kompetenser"};
    ArrayList<String> selectedQualificationsSearchToSend;

    //searchUser
    SearchView searchViewUsers;
    ListView listViewUsers;
    ArrayList<User> arrayListUsers;
    ArrayAdapter adapter;
    String selectedUser;
    ArrayList<User> arrayListUsersTest;
    Button swapSearchButton;
    TextView textViewSwappedSearchText;
    boolean isSwapButtonPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_empty);

        searchViewUsers = findViewById(R.id.searchViewUsers);
        listViewUsers = findViewById(R.id.listViewSearchUsers);
        swapSearchButton = findViewById(R.id.swapSearchButton);
        textViewSwappedSearchText = findViewById(R.id.textViewSwappedSearchText);

        //showQualificationsSearchDialog(); //behövs för att välja kvalifikationer
        searchUsers();
        swapSearch();

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
            }
        });
    }

    private void searchUsers(){
        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        userApi.findAllUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                arrayListUsersTest = new ArrayList<>();

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

                    arrayListUsersTest.add(userTest);
                }

                System.out.println("ARRAYTEST" + arrayListUsersTest);
                System.out.println("ARRAYTEST" + arrayListUsersTest.get(1));

                System.out.println("hmm" + response.code());
                System.out.println("RESPONSE BODY FIRST TIME: " + response.body());

                System.out.println("hmmm" + response.body().getClass());

                initializeAdapter();
                System.out.println("FIND ALL USERS PASSED");
                System.out.println("*****");
                System.out.println(arrayListUsers);
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

                //closeKeyboard();

                Toast.makeText(getApplicationContext(), selectedUser + " valt.", Toast.LENGTH_LONG).show();
                System.out.println("ANVÄNDARE VALD ÄR: " + selectedUser);
            }
        });
    }

    private void initializeAdapter() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayListUsersTest);
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