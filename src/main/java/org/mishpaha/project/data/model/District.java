package org.mishpaha.project.data.model;

public class District {
    private int id;
    private String district;

    public District() {}

    public District(int id, String district) {
        this.id = id;
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
}
