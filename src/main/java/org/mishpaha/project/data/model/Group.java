package org.mishpaha.project.data.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.mishpaha.project.util.View;
import org.mishpaha.project.data.dao.jdbc.Unit;

import java.util.ArrayList;
import java.util.List;

public class Group implements Unit{

    @JsonView(View.Summary.class)
    private int id;
    @JsonView(View.Summary.class)
    private int leaderId;
    @JsonView(View.Summary.class)
    private int regionId;
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
        if (persons == null) {
            persons = new ArrayList<>();
        }
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public Group() {

    }

    public Group(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (id != group.id) return false;
        if (leaderId != group.leaderId) return false;
        if (regionId != group.regionId) return false;
        if (leader != null ? !leader.equals(group.leader) : group.leader != null) return false;
        return persons != null ? persons.equals(group.persons) : group.persons == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + leaderId;
        result = 31 * result + regionId;
        result = 31 * result + (leader != null ? leader.hashCode() : 0);
        result = 31 * result + (persons != null ? persons.hashCode() : 0);
        return result;
    }

    @Override
    public Units getUnit() {
        return Units.GROUP;
    }
}
