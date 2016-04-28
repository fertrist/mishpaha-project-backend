package org.project.people.data.entity;

import com.fasterxml.jackson.annotation.JsonView;
import org.project.people.controller.View;

import java.util.Date;
import java.util.List;

import static org.project.people.util.TestUtil.getDateAsString;


/**
 * Entity to define a person.
 */
public class Person {

    @JsonView(View.Summary.class)
    private int id;
    @JsonView(View.Summary.class)
    private String firstName;
    @JsonView(View.Summary.class)
    private String midName;
    @JsonView(View.Summary.class)
    private String lastName;
    @JsonView(View.Summary.class)
    private int categoryId;
    //true means men
    @JsonView(View.Summary.class)
    private Boolean isJew;
    @JsonView(View.Summary.class)
    private Boolean givesTithe;
    @JsonView(View.Summary.class)
    private String comment;
    @JsonView(View.Info.class)
    private Boolean sex;
    @JsonView(View.Info.class)
    private Date birthDay;
    @JsonView(View.Info.class)
    private List<String> emails;
    @JsonView(View.Info.class)
    private List<String> phones;

    public Person(int id, String firstName, String midName, String lastName, boolean sex, Date birthDay,
                  boolean isJew, boolean givesTithe, int categoryId, String comment) {
        this(firstName, midName, lastName, sex, birthDay, isJew, givesTithe, categoryId, comment);
        this.id = id;
    }

    public Person(String firstName, String midName, String lastName, boolean sex, Date birthDay,
                  boolean isJew, boolean givesTithe, int categoryId, String comment) {
        this.firstName = firstName;
        this.midName = midName;
        this.lastName = lastName;
        this.sex = sex;
        setBirthDay(birthDay);
        this.isJew = isJew;
        this.givesTithe = givesTithe;
        this.categoryId = categoryId;
        this.comment = comment;
    }

    public Person() {}

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (getId() != person.getId()) return false;
        if (getSex() != person.getSex()) return false;
        if (isJew() != person.isJew()) return false;
        if (givesTithe() != person.givesTithe()) return false;
        if (!getFirstName().equals(person.getFirstName())) return false;
        if (getMidName() != null ? !getMidName().equals(person.getMidName()) : person.getMidName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(person.getLastName()) : person.getLastName() != null)
            return false;
        if (getBirthDay() != null ? !getDateAsString(getBirthDay()).equals(getDateAsString(person.getBirthDay()))
            : person.getBirthDay() != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getFirstName().hashCode();
        result = 31 * result + (getMidName() != null ? getMidName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getSex() ? 1 : 0);
        result = 31 * result + (getBirthDay() != null ? getBirthDay().hashCode() : 0);
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
            ", birthDay=" + getDateAsString(getBirthDay()) +
            ", isJew=" + isJew +
            ", givesTithe=" + givesTithe +
            '}';
    }

}
