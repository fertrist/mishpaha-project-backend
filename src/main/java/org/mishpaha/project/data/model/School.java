package org.mishpaha.project.data.model;

import java.util.Date;

/**
 * Created by fertrist on 24.09.15.
 */
public class School {

    private int id;
    Level schoolLevel;
    private Date start;
    private Date graduation;
    private String teacher;

    public enum Level {FIRST, SECOND, THIRD};

    public School(int id, Level schoolLevel, Date start, Date graduation, String teacher) {
        this.id = id;
        this.schoolLevel = schoolLevel;
        this.start = start;
        this.graduation = graduation;
        this.teacher = teacher;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Level getSchoolLevel() {
        return schoolLevel;
    }

    public void setSchoolLevel(Level schoolLevel) {
        this.schoolLevel = schoolLevel;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getGraduation() {
        return graduation;
    }

    public void setGraduation(Date graduation) {
        this.graduation = graduation;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
