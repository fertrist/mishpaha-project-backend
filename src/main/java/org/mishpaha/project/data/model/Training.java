package org.mishpaha.project.data.model;

import java.time.LocalDate;

public class Training {

    private int id;
    private String name;
    private LocalDate tookPlace;

    public Training(int id, String name, LocalDate tookPlace) {
        this.id = id;
        this.name = name;
        this.tookPlace = tookPlace;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getTookPlace() {
        return tookPlace;
    }

    public void setTookPlace(LocalDate tookPlace) {
        this.tookPlace = tookPlace;
    }
}
