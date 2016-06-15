package org.mishpaha.project.data.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.mishpaha.project.util.View;

import java.time.LocalDate;
import java.util.List;

public class Person {

    @JsonView(View.Summary.class)
    private int id;
    @JsonView(View.Summary.class)
    private String firstName;
    @JsonView(View.Summary.class)
    private String midName;
    @JsonView(View.Summary.class)
    private String lastName;
    private int groupId;
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
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate birthDay;
    @JsonView(View.Info.class)
    private List<String> emails;
    @JsonView(View.Info.class)
    private List<String> phones;
    @JsonView(View.Info.class)
    private String address;

    public Person(int id, String firstName, String midName, String lastName, boolean sex, LocalDate birthDay,
                  boolean isJew, boolean givesTithe, int categoryId, String address, String comment) {
        this(firstName, midName, lastName, sex, birthDay, isJew, givesTithe, categoryId, address, comment);
        this.id = id;
    }

    public Person(String firstName, String midName, String lastName, boolean sex, LocalDate birthDay,
                  boolean isJew, boolean givesTithe, int categoryId, String address, String comment) {
        this.firstName = firstName;
        this.midName = midName;
        this.lastName = lastName;
        this.sex = sex;
        this.birthDay = birthDay;
        this.isJew = isJew;
        this.givesTithe = givesTithe;
        this.categoryId = categoryId;
        this.comment = comment;
        this.address = address;
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

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (id != person.id) return false;
        if (categoryId != person.categoryId) return false;
        if (firstName != null ? !firstName.equals(person.firstName) : person.firstName != null) return false;
        if (midName != null ? !midName.equals(person.midName) : person.midName != null) return false;
        if (lastName != null ? !lastName.equals(person.lastName) : person.lastName != null) return false;
        if (isJew != null ? !isJew.equals(person.isJew) : person.isJew != null) return false;
        if (givesTithe != null ? !givesTithe.equals(person.givesTithe) : person.givesTithe != null) return false;
        if (comment != null ? !comment.equals(person.comment) : person.comment != null) return false;
        if (sex != null ? !sex.equals(person.sex) : person.sex != null) return false;
        if (birthDay != null ? !birthDay.equals(person.birthDay) : person.birthDay != null) return false;
        if (emails != null ? !emails.equals(person.emails) : person.emails != null) return false;
        if (phones != null ? !phones.equals(person.phones) : person.phones != null) return false;
        return address != null ? address.equals(person.address) : person.address == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (midName != null ? midName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + categoryId;
        result = 31 * result + (isJew != null ? isJew.hashCode() : 0);
        result = 31 * result + (givesTithe != null ? givesTithe.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (birthDay != null ? birthDay.hashCode() : 0);
        result = 31 * result + (emails != null ? emails.hashCode() : 0);
        result = 31 * result + (phones != null ? phones.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
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
            ", birthDay=" + birthDay +
            ", isJew=" + isJew +
            ", givesTithe=" + givesTithe +
            '}';
    }

}
