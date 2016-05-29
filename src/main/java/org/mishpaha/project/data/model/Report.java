package org.mishpaha.project.data.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Report {

    private int groupId;
    private String groupLeader;
    private LocalDate startRange;
    private LocalDate endRange;
    private List<Week> weeks;

    public Report() {
        this.weeks = new ArrayList<>();
    }

    public void addWeekRecord(LocalDate weekStart, LocalDate weekEnd, Map<String, Boolean> listed,
                              List<String> newbies, List<String> guests, int meetingsNewbies, int visitsNewbies,
                              int meetingsListed,
                              int visitsListed, int calls) {
        weeks.add(new Week(weekStart, weekEnd, listed, newbies, guests, meetingsNewbies, visitsNewbies,
            meetingsListed, visitsListed, calls));
    }

    private class Week {
        private LocalDate weekStart;
        private LocalDate weekEnd;
        private Map<String, Boolean> listed;
        private List<String> newbies;
        private List<String> guests;
        private int meetingsNewbies;
        private int visitsNewbies;
        private int meetingsListed;
        private int visitsListed;
        private int calls;

        private Week(LocalDate weekStart, LocalDate weekEnd, Map<String, Boolean> listed,
                    List<String> newbies, List<String> guests, int meetingsNewbies, int visitsNewbies,
                    int meetingsListed,
                    int visitsListed, int calls) {
            this.weekStart = weekStart;
            this.weekEnd = weekEnd;
            this.listed = listed;
            this.newbies = newbies;
            this.guests = guests;
            this.meetingsNewbies = meetingsNewbies;
            this.visitsNewbies = visitsNewbies;
            this.meetingsListed = meetingsListed;
            this.visitsListed = visitsListed;
            this.calls = calls;
        }

        private Week(LocalDate weekStart, LocalDate weekEnd, Map<String, Boolean> listed,
                     List<String> newbies, List<String> guests) {
            this.weekStart = weekStart;
            this.weekEnd = weekEnd;
            this.listed = listed;
            this.newbies = newbies;
            this.guests = guests;
        }

        public LocalDate getWeekStart() {
            return weekStart;
        }

        public void setWeekStart(LocalDate weekStart) {
            this.weekStart = weekStart;
        }

        public LocalDate getWeekEnd() {
            return weekEnd;
        }

        public void setWeekEnd(LocalDate weekEnd) {
            this.weekEnd = weekEnd;
        }

        public Map<String, Boolean> getListed() {
            return listed;
        }

        public void setListed(Map<String, Boolean> listed) {
            this.listed = listed;
        }

        public List<String> getNewbies() {
            return newbies;
        }

        public void setNewbies(List<String> newbies) {
            this.newbies = newbies;
        }

        public List<String> getGuests() {
            return guests;
        }

        public void setGuests(List<String> guests) {
            this.guests = guests;
        }

        public int getMeetingsNewbies() {
            return meetingsNewbies;
        }

        public void setMeetingsNewbies(int meetingsNewbies) {
            this.meetingsNewbies = meetingsNewbies;
        }

        public int getVisitsNewbies() {
            return visitsNewbies;
        }

        public void setVisitsNewbies(int visitsNewbies) {
            this.visitsNewbies = visitsNewbies;
        }

        public int getMeetingsListed() {
            return meetingsListed;
        }

        public void setMeetingsListed(int meetingsListed) {
            this.meetingsListed = meetingsListed;
        }

        public int getVisitsListed() {
            return visitsListed;
        }

        public void setVisitsListed(int visitsListed) {
            this.visitsListed = visitsListed;
        }

        public int getCalls() {
            return calls;
        }

        public void setCalls(int calls) {
            this.calls = calls;
        }
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupLeader() {
        return groupLeader;
    }

    public void setGroupLeader(String groupLeader) {
        this.groupLeader = groupLeader;
    }

    public LocalDate getStartRange() {
        return startRange;
    }

    public void setStartRange(LocalDate startRange) {
        this.startRange = startRange;
    }

    public LocalDate getEndRange() {
        return endRange;
    }

    public void setEndRange(LocalDate endRange) {
        this.endRange = endRange;
    }

    public List<Week> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<Week> weeks) {
        this.weeks = weeks;
    }
}
