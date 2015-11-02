package org.mishpaha.project.data.model;

/**
 * Created by fertrist on 24.09.15.
 */
public class GroupMember {

    private int personId;
    private int groupId;

    public GroupMember(int personId, int groupId) {
        this.personId = personId;
        this.groupId = groupId;
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

}
