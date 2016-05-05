package org.mishpaha.project.util;

import org.mishpaha.project.data.model.Category;
import org.mishpaha.project.data.model.ChangeRecord;
import org.mishpaha.project.data.model.DoneTraining;
import org.mishpaha.project.data.model.Email;
import org.mishpaha.project.data.model.Event;
import org.mishpaha.project.data.model.EventType;
import org.mishpaha.project.data.model.Graduation;
import org.mishpaha.project.data.model.Group;
import org.mishpaha.project.data.model.GroupMember;
import org.mishpaha.project.data.model.Ministry;
import org.mishpaha.project.data.model.Person;
import org.mishpaha.project.data.model.Phone;
import org.mishpaha.project.data.model.Region;
import org.mishpaha.project.data.model.School;
import org.mishpaha.project.data.model.Training;
import org.mishpaha.project.data.model.Tribe;
import org.mishpaha.project.data.model.Volunteer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ModelUtil {

    private static Map<String, String> TABLE_NAMES;

    static {
        TABLE_NAMES = new LinkedHashMap<>();
        TABLE_NAMES.put(GroupMember.class.getSimpleName(), "groupMembers");
        TABLE_NAMES.put(Group.class.getSimpleName(), "groups");
        TABLE_NAMES.put(Category.class.getSimpleName(), "categories");
        TABLE_NAMES.put(ChangeRecord.class.getSimpleName(), "changeRecords");
        TABLE_NAMES.put(DoneTraining.class.getSimpleName(), "doneTrainings");
        TABLE_NAMES.put(Training.class.getSimpleName(), "trainings");
        TABLE_NAMES.put(Graduation.class.getSimpleName(), "graduations");
        TABLE_NAMES.put(School.class.getSimpleName(), "schools");
        TABLE_NAMES.put(Volunteer.class.getSimpleName(), "volunteers");
        TABLE_NAMES.put(Ministry.class.getSimpleName(), "ministries");
        TABLE_NAMES.put(Region.class.getSimpleName(), "regions");
        TABLE_NAMES.put(Tribe.class.getSimpleName(), "tribes");
        TABLE_NAMES.put(Phone.class.getSimpleName(), "phones");
        TABLE_NAMES.put(Email.class.getSimpleName(), "emails");
        TABLE_NAMES.put(Person.class.getSimpleName(), "persons");
        TABLE_NAMES.put(Event.class.getSimpleName(), "events");
        TABLE_NAMES.put(EventType.class.getSimpleName(), "eventTypes");
    }

    public static String getTable(Class clazz) {
        return TABLE_NAMES.get(clazz.getSimpleName());
    }

    public static String[] getTableNames() {
        List<String> tableNames = new ArrayList<>();
        tableNames.addAll(TABLE_NAMES.keySet().stream().map(TABLE_NAMES::get).collect(Collectors.toList()));
        String[] names = new String[TABLE_NAMES.size()];
        return tableNames.toArray(names);
    }
}
