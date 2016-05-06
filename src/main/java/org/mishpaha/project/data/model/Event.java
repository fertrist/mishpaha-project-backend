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
}
