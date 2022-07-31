package com.example.rickandmortyapi.network;


import com.example.rickandmortyapi.models.CharacterResponseObject;
import com.example.rickandmortyapi.models.Episode;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    // TODO: base url of the api
    public final String BASE_URL = "https://rickandmortyapi.com/api/";

    // TODO: endpoints we want to connect to

//    https://rickandmortyapi.com/api/character/?name=rick
    @GET("character")
    public Call<CharacterResponseObject> getOneCharacterByName(@Query("name") String searchTerm);

    @GET("episode/{episodesIds}")
    public Call<List<Episode>> getMultipleEpisodeByIds(@Path("episodesIds") List<Integer> listOfEpisodes);
}
