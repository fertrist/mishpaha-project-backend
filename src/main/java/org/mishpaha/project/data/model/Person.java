package org.mishpaha.project.data.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class Person {

    private int id;
    private String firstName;
    private String midName;
    private String lastName;
    //true means men
    private Boolean sex;
    private Date birthDay;
    private int districtId;
    private String address;
    private Boolean isJew;
    private Boolean givesTithe;
    private List<String> emails;
    private List<String> phones;


    public Person(int id, String firstName, String midName, String lastName, boolean sex, Date birthDay, int districtId,
                  String address, boolean isJew, boolean givesTithe) {
        this.id = id;
        this.firstName = firstName;
        this.midName = midName;
        this.lastName = lastName;
        this.sex = sex;
        setBirthDay(birthDay);
        this.districtId = districtId;
        this.address = address;
        this.isJew = isJew;
        this.givesTithe = givesTithe;
    }

    public Person() {

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

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
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

    public Boolean isJew() {
        return isJew;
    }

    public void setIsJew(Boolean isJew) {
        this.isJew = isJew;
    }

    public Boolean givesTithe() {
        return givesTithe;
    }

    public void givesTithe(Boolean givesTithe) {
        this.givesTithe = givesTithe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (getId() != person.getId()) return false;
        if (getSex() != person.getSex()) return false;
        if (getDistrictId() != person.getDistrictId()) return false;
        if (isJew() != person.isJew()) return false;
        if (givesTithe() != person.givesTithe()) return false;
        if (!getFirstName().equals(person.getFirstName())) return false;
        if (getMidName() != null ? !getMidName().equals(person.getMidName()) : person.getMidName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(person.getLastName()) : person.getLastName() != null)
            return false;
        if (getBirthDay() != null ? !getBirthdayAsString().equals(person.getBirthdayAsString())
            : person.getBirthdayAsString() != null)
            return false;
        return !(getAddress() != null ? !getAddress().equals(person.getAddress()) : person.getAddress() != null);

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + (getMidName() != null ? getMidName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getSex() ? 1 : 0);
        result = 31 * result + (getBirthDay() != null ? getBirthDay().hashCode() : 0);
        result = 31 * result + getDistrictId();
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (isJew() ? 1 : 0);
        result = 31 * result + (givesTithe() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", midName='" + midName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", sex=" + (sex ? "M" : "W") +
            ", birthDay=" + getBirthdayAsString() +
            ", districtId=" + districtId +
            ", address='" + address + '\'' +
            ", isJew=" + isJew +
            ", givesTithe=" + givesTithe +
            '}';
    }

    public String getBirthdayAsString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(getBirthDay());
    }

}
