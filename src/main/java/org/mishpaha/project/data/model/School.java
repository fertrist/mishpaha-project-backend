package org.mishpaha.project.data.model;

import java.time.LocalDate;

public class School {

    private int id;
    Level schoolLevel;
    private LocalDate start;
    private LocalDate graduation;
    private String teacher;

    public enum Level {FIRST, SECOND, THIRD}

    public School(int id, Level schoolLevel, LocalDate start, LocalDate graduation, String teacher) {
        this.id = id;
        this.schoolLevel = schoolLevel;
        this.start = start;
        this.graduation = graduation;
        this.teacher = teacher;
    }

    public School(int id, String schoolLevel, LocalDate start, LocalDate graduation, String teacher) {
        this.id = id;
        this.schoolLevel = Level.valueOf(schoolLevel);
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

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getGraduation() {
        return graduation;
    }

    public void setGraduation(LocalDate graduation) {
        this.graduation = graduation;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
