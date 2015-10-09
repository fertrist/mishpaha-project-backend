package org.mishpaha.project.data.model;

/**
 * Created by fertrist on 09.10.15.
 */
public class Email {

    private int personId;
    private String email;

    public Email(int personId, String email) {
        this.personId = personId;
        this.email = email;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Email email1 = (Email) o;

        if (personId != email1.personId) return false;
        return !(email != null ? !email.equals(email1.email) : email1.email != null);

    }

    @Override
    public int hashCode() {
        int result = personId;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Email{" +
            "personId=" + personId +
            ", email='" + email + '\'' +
            '}';
    }
}
