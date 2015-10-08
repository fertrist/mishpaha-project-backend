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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Phone phone1 = (Phone) o;

        if (personId != phone1.personId) return false;
        return !(phone != null ? !phone.equals(phone1.phone) : phone1.phone != null);

    }

    @Override
    public int hashCode() {
        int result = personId;
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Phone{" +
            "personId=" + personId +
            ", phone='" + phone + '\'' +
            '}';
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
