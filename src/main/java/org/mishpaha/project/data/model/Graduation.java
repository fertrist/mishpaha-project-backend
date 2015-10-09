package org.mishpaha.project.data.model;

/**
 * Created by fertrist on 24.09.15.
 */
public class Graduation {

    private int schoolId;
    private int personId;

    public Graduation(int schoolId, int personId) {
        this.schoolId = schoolId;
        this.personId = personId;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
