package org.mishpaha.project.data.model;

/**
 * Created by fertrist on 24.09.15.
 */
public class DoneTraining {

    private int personId;
    private int trainingid;

    public DoneTraining(int personId, int trainingid) {
        this.personId = personId;
        this.trainingid = trainingid;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getTrainingid() {
        return trainingid;
    }

    public void setTrainingid(int trainingid) {
        this.trainingid = trainingid;
    }
}
