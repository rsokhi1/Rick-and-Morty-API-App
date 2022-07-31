package com.example.rickandmortyapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.rickandmortyapi.adapters.EpisodeAdapter;
import com.example.rickandmortyapi.databinding.ActivitySecondScreenBinding;
import com.example.rickandmortyapi.models.Episode;
import com.example.rickandmortyapi.models.RickAndMortyCharacter;
import com.example.rickandmortyapi.network.API;
import com.example.rickandmortyapi.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondScreen extends AppCompatActivity {

    private final String TAG="RICK-AND-MORTY";

    private ActivitySecondScreenBinding binding;

    private ArrayList<Episode> itemsList = new ArrayList<>();
    private EpisodeAdapter adapter;
    private API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.api = RetrofitClient.getInstance().getApi();

        // setup the adapter
        this.adapter = new EpisodeAdapter(this, itemsList);

        // set up recycler view
        binding.rvEpisodes.setLayoutManager(new LinearLayoutManager(this));
        binding.rvEpisodes.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        binding.rvEpisodes.setAdapter(adapter);

        // get intent
        Intent intent = getIntent();
        if (intent != null) {
            // TODO: Get data from previous screen
            RickAndMortyCharacter charFromS1 = (RickAndMortyCharacter)intent.getSerializableExtra("EXTRA_CHAR_INFO");

            binding.tvName.setText(charFromS1.getName());
            binding.tvSpecies.setText(charFromS1.getSpecies());

            Glide.with(this).load(charFromS1.getImageURL()).into(binding.imageView);



            Call<List<Episode>> request = api.getMultipleEpisodeByIds(charFromS1.getEpisodeIds());
            request.enqueue(new Callback<List<Episode>>() {
                @Override
                public void onResponse(Call<List<Episode>> call, Response<List<Episode>> response) {
                    if(response.isSuccessful() == false){
                        Log.d("ABC","Error from API with the reponse code: " + response.code());
                        return;
                    }

                    List<Episode> episodesFromAPI = response.body();

                    itemsList.clear();
                    itemsList.addAll(episodesFromAPI);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<Episode>> call, Throwable t) {
                    Log.d("ABC",t.getMessage());
                }
            });


        }
    }
}