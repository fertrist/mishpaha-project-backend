package org.mishpaha.project.data.model;

import java.util.Date;

/**
 * Created by fertrist on 24.09.15.
 */
public class ChangeRecord {

    private Date tookPlace;
    private int personId;
    private int groupId;
    private boolean wasAdded;
    private String comment;

    public ChangeRecord(Date tookPlace, int personId, int groupId, boolean wasAdded, String comment) {
        this(personId, groupId, wasAdded);
        this.comment = comment;
        this.tookPlace = tookPlace;
    }

    public ChangeRecord(int personId, int groupId, boolean wasAdded) {
        this.personId = personId;
        this.groupId = groupId;
        this.wasAdded = wasAdded;
    }

    public Date getTookPlace() {
        return tookPlace;
    }

    public void setTookPlace(Date tookPlace) {
        this.tookPlace = tookPlace;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public boolean isWasAdded() {
        return wasAdded;
    }

    public void setWasAdded(boolean wasAdded) {
        this.wasAdded = wasAdded;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
