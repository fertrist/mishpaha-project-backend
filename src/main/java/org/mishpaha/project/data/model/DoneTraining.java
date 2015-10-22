package org.mishpaha.project.data.model;

/**
 * Created by fertrist on 24.09.15.
 */
public class DoneTraining {

    private int personId;
    private int trainingId;

    public DoneTraining(int personId, int trainingId) {
        this.personId = personId;
        this.trainingId = trainingId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(int trainingId) {
        this.trainingId = trainingId;
    }
}
