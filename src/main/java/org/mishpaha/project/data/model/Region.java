package org.mishpaha.project.data.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.mishpaha.project.data.dao.Unit;
import org.mishpaha.project.util.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fertrist on 24.09.15.
 */
public class Region implements Unit{

    @JsonView(View.Summary.class)
    private int id;
    private int leaderId;
    @JsonView(View.Summary.class)
    private int tribeId;
    private String leader = null;
    @JsonView(View.Summary.class)
    private List<Group> groups = null;

    public Region(int id, int leaderId, int tribeId) {
        this(leaderId, tribeId);
        this.id = id;
    }

    public Region(int leaderId, int tribeId) {
        this.leaderId = leaderId;
        this.tribeId = tribeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public List<Group> getGroups() {
        if (groups == null) {
            groups = new ArrayList<>();
        }
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public int getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(int leaderId) {
        this.leaderId = leaderId;
    }

    public int getTribeId() {
        return tribeId;
    }

    public void setTribeId(int tribeId) {
        this.tribeId = tribeId;
    }

    @Override
    public Unit.Units getUnit() {
        return Units.REGION;
    }
}
