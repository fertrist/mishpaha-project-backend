package org.mishpaha.project.data.model;

/**
 * Created by fertrist on 24.09.15.
 */
public class Volunteer {

    private int personId;
    private int ministryId;

    public Volunteer(int personId, int ministryId) {
        this.personId = personId;
        this.ministryId = ministryId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getMinistryId() {
        return ministryId;
    }

    public void setMinistryId(int ministryId) {
        this.ministryId = ministryId;
    }
}
