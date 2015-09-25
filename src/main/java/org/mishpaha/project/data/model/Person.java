package org.mishpaha.project.data.model;

import java.util.Date;

/**
 * Created by fertrist on 24.09.15.
 */
public class Person {

    private int id;
    private String firstName;
    private String midName;
    private String lastName;
    //true means men
    private boolean sex;
    private Date birthDay;
    private int districtId;
    private String address;
    private String email;
    private boolean isJew;
    private boolean givesTithe;
    private Date wasAdded;

    public Person(String firstName, String midName, String lastName, boolean sex, Date birthDay, int districtId,
                  String address, String email, boolean isJew, boolean givesTithe, Date wasAdded) {
        this.firstName = firstName;
        this.midName = midName;
        this.lastName = lastName;
        this.sex = sex;
        this.birthDay = birthDay;
        this.districtId = districtId;
        this.address = address;
        this.email = email;
        this.isJew = isJew;
        this.givesTithe = givesTithe;
        this.wasAdded = wasAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMidName() {
        return midName;
    }

    public void setMidName(String midName) {
        this.midName = midName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isJew() {
        return isJew;
    }

    public void setIsJew(boolean isJew) {
        this.isJew = isJew;
    }

    public boolean isGivesTithe() {
        return givesTithe;
    }

    public void setGivesTithe(boolean givesTithe) {
        this.givesTithe = givesTithe;
    }

    public Date getWasAdded() {
        return wasAdded;
    }

    public void setWasAdded(Date wasAdded) {
        this.wasAdded = wasAdded;
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", midName='" + midName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", sex=" + (sex ? "M" : "W") +
            ", birthDay=" + birthDay +
            ", districtId=" + districtId +
            ", address='" + address + '\'' +
            ", email='" + email + '\'' +
            ", isJew=" + isJew +
            ", givesTithe=" + givesTithe +
            ", wasAdded=" + wasAdded +
            '}';
    }
}
