package org.mishpaha.project.data.model;

public class EventType {
    private int id;
    private String type;

    public EventType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public EventType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
