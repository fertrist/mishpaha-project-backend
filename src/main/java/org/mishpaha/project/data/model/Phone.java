package org.mishpaha.project.data.model;

/**
 * Model class which represents phone No.
 */
public class Phone {

    private int personId;
    private String phone;

    public Phone(int personId, String phone) {
        this.personId = personId;
        this.phone = phone;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
