package org.mishpaha.project.data.model;

/**
 * Created by fertrist on 24.09.15.
 */
public class Ministry {

    private int id;
    private String name;

    public Ministry(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Ministry(String name) {
        this.name = name;
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
}
