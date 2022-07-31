package com.example.rickandmortyapi.models;

import java.io.Serializable;

public class Location implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                '}';
    }
}
