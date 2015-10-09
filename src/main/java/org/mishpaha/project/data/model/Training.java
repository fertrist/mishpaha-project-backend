package org.mishpaha.project.data.model;

import java.util.Date;

/**
 * Created by fertrist on 24.09.15.
 */
public class Training {

    private int id;
    private String name;
    private Date tookPlace;

    public Training(int id, String name, Date tookPlace) {
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

    public Date getTookPlace() {
        return tookPlace;
    }

    public void setTookPlace(Date tookPlace) {
        this.tookPlace = tookPlace;
    }
}