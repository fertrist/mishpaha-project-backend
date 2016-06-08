package org.mishpaha.project.data.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.mishpaha.project.data.dao.Unit;
import org.mishpaha.project.util.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class Tribe implements Unit {
    @JsonView(View.Summary.class)
    private int id;
    private String name;
    @JsonView(View.Summary.class)
    private List<Region> regions = null;

    public Tribe(){}

    public Tribe(String name) {
        this.name = name;
    }

    public Tribe(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public List<Region> getRegions() {
        if (regions == null) {
            regions = new ArrayList<>();
        }
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }

    @Override
    public Units getUnit() {
        return Units.TRIBE;
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
