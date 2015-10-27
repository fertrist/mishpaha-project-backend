package org.mishpaha.project.data.model;

/**
 * Created by fertrist on 26.10.15.
 */
public class Address {

    private int personId;
    private String country;
    private String region;
    private String city;
    private String district;
    private String street;
    private String building;
    private int flat;

    public Address(int personId, String country, String region, String city, String district,
                   String street, String building, int flat) {
        this.personId = personId;
        this.country = country;
        this.region = region;
        this.city = city;
        this.district = district;
        this.street = street;
        this.building = building;
        this.flat = flat;
    }

    public Address() {}

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public int getFlat() {
        return flat;
    }

    public void setFlat(int flat) {
        this.flat = flat;
    }
}
