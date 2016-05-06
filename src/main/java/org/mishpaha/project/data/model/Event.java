package org.mishpaha.project.data.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;


public class Event {

    private int id;
    private int personId;
    private int groupId;
    private int typeId;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate happened;

    public Event() {
    }

    public Event(int id, int personId, int groupId, int typeId, LocalDate happened) {
        this(personId, groupId, typeId, happened);
        this.id = id;
    }

    public Event(int personId, int groupId, int typeId, LocalDate happened) {
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

    public LocalDate getHappened() {
        return happened;
    }

    public void setHappened(LocalDate happened) {
        this.happened = happened;
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + id +
            ", personId=" + personId +
            ", groupId=" + groupId +
            ", typeId=" + typeId +
            ", happened=" + happened +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (id != event.id) return false;
        if (personId != event.personId) return false;
        if (groupId != event.groupId) return false;
        if (typeId != event.typeId) return false;
        return happened != null ? happened.equals(event.happened) : event.happened == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + personId;
        result = 31 * result + groupId;
        result = 31 * result + typeId;
        result = 31 * result + (happened != null ? happened.hashCode() : 0);
        return result;
    }
}
