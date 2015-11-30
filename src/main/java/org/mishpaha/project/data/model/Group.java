package org.mishpaha.project.data.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.mishpaha.project.controller.View;

import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class Group {

    @JsonView(View.Summary.class)
    private int id;
    private int leaderId;
    @JsonView(View.Summary.class)
    private int regionId;
    @JsonView(View.Summary.class)
    private String leader = null;
    @JsonView(View.Summary.class)
    private List<Person> persons = null;

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public Group() {

    }

    public Group(int id, int leaderId, int regionId) {
        this(leaderId, regionId);
        this.id = id;
    }

    public Group(int leaderId, int regionId) {
        this.leaderId = leaderId;
        this.regionId = regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getRegionId() {
        return regionId;
    }

    public int getLeaderId() {
        return leaderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLeaderId(int leaderId) {
        this.leaderId = leaderId;
    }
}
