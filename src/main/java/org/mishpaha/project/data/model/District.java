package org.mishpaha.project.data.model;

/**
 * Model class which represents city District.
 */
public class District {
    private int id;
    private String district;

    public District() {}

    public District(int id, String district) {
        this.id = id;
        this.district = district;
    }

    public District(String district) {
        this.district = district;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public String toString() {
        return String.format("%d : %s", getId(), getDistrict());
    }
}
