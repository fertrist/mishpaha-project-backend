package org.mishpaha.project.data.model;

import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class Tribe {
    private int id;
    private String name;
    private List<Region> regions = null;

    public Tribe(String name) {
        this.name = name;
    }

    public Tribe(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
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
