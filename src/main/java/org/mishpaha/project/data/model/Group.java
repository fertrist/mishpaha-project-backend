package org.mishpaha.project.data.model;

/**
 * Created by fertrist on 24.09.15.
 */
public class Group {

    private int leaderId;
    private int regionId;

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

    public void setLeaderId(int leaderId) {
        this.leaderId = leaderId;
    }
}
