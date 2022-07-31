package com.example.rickandmortyapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.example.rickandmortyapi.adapters.CharacterAdapter;
import com.example.rickandmortyapi.databinding.ActivityMainBinding;
import com.example.rickandmortyapi.models.CharacterResponseObject;
import com.example.rickandmortyapi.models.RickAndMortyCharacter;
import com.example.rickandmortyapi.network.API;
import com.example.rickandmortyapi.network.RetrofitClient;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String TAG="RICK-AND-MORTY";
    private ActivityMainBinding binding;
    private ArrayList<RickAndMortyCharacter> itemsList = new ArrayList<>();
    private CharacterAdapter adapter;
    private API api;
    private List<RickAndMortyCharacter> charactersFromAPI = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        adapter = new CharacterAdapter(this, itemsList, this::OnItemClickListener);
        binding.rvItems.setAdapter(adapter);
        binding.rvItems.setLayoutManager(new LinearLayoutManager(this));

        // TODO: configure the API client
        this.api = RetrofitClient.getInstance().getApi();

        // configure search button handler
        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get search key from UI
                String charNameFromUI = binding.etCharacterName.getText().toString();
                if (charNameFromUI.isEmpty() == true) {
                    Snackbar.make(binding.getRoot(), "Please enter a search term", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                // TODO: Start the search
//                binding.tvNumResults.setText("Searching for: " + charNameFromUI);

                Call<CharacterResponseObject> request = api.getOneCharacterByName(charNameFromUI);
                request.enqueue(new Callback<CharacterResponseObject>() {
                    @Override
                    public void onResponse(Call<CharacterResponseObject> call, Response<CharacterResponseObject> response) {
                        if(response.isSuccessful() == false){
                            Log.d(TAG,"Error from API with the reponse code: " + response.code());
                            return;
                        }

                        CharacterResponseObject obj = response.body();
                        charactersFromAPI = obj.getResults();

                        itemsList.clear();
                        itemsList.addAll(charactersFromAPI);
                        adapter.notifyDataSetChanged();

                        binding.tvNumResults.setText("Number of results: " + charactersFromAPI.size());
                    }

                    @Override
                    public void onFailure(Call<CharacterResponseObject> call, Throwable t) {
                        Log.d(TAG,t.getMessage());
                    }
                });

            }
        });

        // add listener for switch
        binding.swAlive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    // switch is on
                    // - filter through the list of existing characters and retrieve only
                    // the characters with an "Alive" status
                    ArrayList<RickAndMortyCharacter> aliveCharacterList = new ArrayList<>();
                    for(int i =0 ;i<charactersFromAPI.size();i++){
                        if(charactersFromAPI.get(i).getStatus().contentEquals("Alive") == true){
                            aliveCharacterList.add(charactersFromAPI.get(i));
                        }
                    }

                    itemsList.clear();
                    itemsList.addAll(aliveCharacterList);
                    adapter.notifyDataSetChanged();

                    // - update the adapter with the list of alive characters

                    // - update results label to show the new number of items in the search results
                    binding.tvNumResults.setText("Number of results: " + aliveCharacterList.size());

                }
                else {
                    // switch is off
                    binding.tvNumResults.setText("Switch is OFF");
                    // - restore the original list of characters

                    itemsList.clear();
                    itemsList.addAll(charactersFromAPI);
                    adapter.notifyDataSetChanged();
                    binding.tvNumResults.setText("Number of results: " + charactersFromAPI.size());

                    // - update results label to show the new number of items in the search results
                }
            }
        });

    }

    // click listener for recyler view row adapter
    public void OnItemClickListener(RickAndMortyCharacter item) {
        // navigate to screen 2
        Intent intent = new Intent(this, SecondScreen.class);
        intent.putExtra("EXTRA_CHAR_INFO",item);
        startActivity(intent);

    }
}