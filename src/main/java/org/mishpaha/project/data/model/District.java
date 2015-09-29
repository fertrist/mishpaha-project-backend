package org.mishpaha.project.data.model;

/**
 * Model class which represents city district.
 */
public class District {
    private int id = -1;
    private String name;

    public District() {}

    public District(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public District(String name) {
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

    @Override
    public String toString() {
        return String.format("%d : %s", getId(), getName());
    }
}
