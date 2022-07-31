package com.example.rickandmortyapi.models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RickAndMortyCharacter implements Serializable {
    private int id;
    private String name;
    private String status;
    private String species;
    private Location origin;
    @SerializedName("location")
    private Location lastKnownLocation;

    @SerializedName("image")
    private String imageURL;
    @SerializedName("episode")
    private List<String> episodeUrlsList;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getSpecies() {
        return species;
    }

    public Location getOrigin() {
        return origin;
    }

    public Location getLastKnownLocation() {
        return lastKnownLocation;
    }

    public String getImageURL() {
        return imageURL;
    }

    public List<String> getEpisodeUrlsList() {
        return episodeUrlsList;
    }

    @Override
    public String toString() {
        return "RickAndMortyCharacter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", species='" + species + '\'' +
                ", origin=" + origin +
                ", lastKnownLocation=" + lastKnownLocation +
                ", imageURL='" + imageURL + '\'' +
                ", episodeUrlsList=" + episodeUrlsList +
                '}';
    }

    public List<Integer> getEpisodeIds(){
        String subString = "https://rickandmortyapi.com/api/episode/";

        List<Integer> idsList = new ArrayList<>();

        for(int i = 0;i<episodeUrlsList.size();i++){
            String currUrl = episodeUrlsList.get(i);

            String[] segments = currUrl.split(subString);
            String episodeNumAsString = segments[1];

            int episodeNumAsInt = Integer.parseInt(episodeNumAsString);

            idsList.add(episodeNumAsInt);
        }
        return idsList;
    }
}
