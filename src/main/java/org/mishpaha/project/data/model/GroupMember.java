package org.mishpaha.project.data.model;

/**
 * Created by fertrist on 24.09.15.
 */
public class GroupMember {

    private int personId;
    private int groupId;
    private int categoryId;

    public GroupMember(int personId, int groupId, int categoryId) {
        this.personId = personId;
        this.groupId = groupId;
        this.categoryId = categoryId;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
