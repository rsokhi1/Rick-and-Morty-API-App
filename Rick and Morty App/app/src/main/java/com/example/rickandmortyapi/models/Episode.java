package com.example.rickandmortyapi.models;

import com.google.gson.annotations.SerializedName;

public class Episode {

    @SerializedName("name")
    private String title;

    @SerializedName("episode")
    private String code;

    @SerializedName("air_date")
    private String airDate;

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public String getAirDate() {
        return airDate;
    }

    @Override
    public String toString() {
        return "Episode{" +
                "title='" + title + '\'' +
                ", code='" + code + '\'' +
                ", airDate='" + airDate + '\'' +
                '}';
    }
}
