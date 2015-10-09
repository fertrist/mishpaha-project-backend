package org.mishpaha.project.data.model;

/**
 * Created by fertrist on 24.09.15.
 */
public class Region {

    private int leaderId;
    private int tribeId;

    public Region(int leaderId, int tribeId) {
        this.leaderId = leaderId;
        this.tribeId = tribeId;
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
}
