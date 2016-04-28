package org.project.people.util;


import org.project.people.data.entity.Category;
import org.project.people.data.entity.Person;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by fertrist on 09.10.15.
 */
public abstract class EntityUtil {

    private static Map<String, String> TABLE_NAMES;

    static {
          TABLE_NAMES = new LinkedHashMap<>();
//        TABLE_NAMES.put(GroupMember.class.getSimpleName(), "groupMembers");
//        TABLE_NAMES.put(Group.class.getSimpleName(), "groups");
//        TABLE_NAMES.put(ChangeRecord.class.getSimpleName(), "changeRecords");
//        TABLE_NAMES.put(DoneTraining.class.getSimpleName(), "doneTrainings");
//        TABLE_NAMES.put(Training.class.getSimpleName(), "trainings");
//        TABLE_NAMES.put(Graduation.class.getSimpleName(), "graduations");
//        TABLE_NAMES.put(School.class.getSimpleName(), "schools");
//        TABLE_NAMES.put(Volunteer.class.getSimpleName(), "volunteers");
//        TABLE_NAMES.put(Ministry.class.getSimpleName(), "ministries");
//        TABLE_NAMES.put(Region.class.getSimpleName(), "regions");
//        TABLE_NAMES.put(Tribe.class.getSimpleName(), "tribes");
//        TABLE_NAMES.put(Phone.class.getSimpleName(), "phones");
//        TABLE_NAMES.put(Email.class.getSimpleName(), "emails");
        TABLE_NAMES.put(Person.class.getSimpleName(), "persons");
        TABLE_NAMES.put(Category.class.getSimpleName(), "categories");
//        TABLE_NAMES.put(Address.class.getSimpleName(), "addresses");
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
