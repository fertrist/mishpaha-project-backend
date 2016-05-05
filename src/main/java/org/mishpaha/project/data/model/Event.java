package org.mishpaha.project.data.model;

import java.util.Date;

import static org.mishpaha.project.util.TestUtil.getDateAsString;

public class Event {

    private int id;
    private int personId;
    private int groupId;
    private int typeId;
    private Date happened;

    public Event() {
    }

    public Event(int id, int personId, int groupId, int typeId, Date happened) {
        this(personId, groupId, typeId, happened);
        this.id = id;
    }

    public Event(int personId, int groupId, int typeId, Date happened) {
        this.personId = personId;
        this.groupId = groupId;
        this.typeId = typeId;
        this.happened = happened;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public Date getHappened() {
        return happened;
    }

    public void setHappened(Date happened) {
        this.happened = happened;
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + id +
            ", personId=" + personId +
            ", groupId=" + groupId +
            ", typeId=" + typeId +
            ", happened=" + getDateAsString(happened) +
            '}';
    }
}
