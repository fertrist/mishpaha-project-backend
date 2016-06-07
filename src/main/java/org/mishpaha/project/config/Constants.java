package org.mishpaha.project.config;

public class Constants {

    public static final String PEOPLE_BASE = "/people";
    public static final String EVENTS_BASE = "/events";
    public static final String REPORTS_BASE = "/reports";

    public static final String GROUP_ID = "/group/{id}";
    public static final String REGION_ID = "/region/{id}";
    public static final String TRIBE_ID = "/tribe/{id}";
    public static final String SUMMARY = "/summary";
    public static final String INFO = "/info";
    public static final String STAFF = "/staff";
    public static final String STAFF_INFO = STAFF + INFO;
    public static final String STAFF_SUMMARY = STAFF + SUMMARY;
    public static final String GROUP_SUMMARY = GROUP_ID + SUMMARY;
    public static final String GROUP_INFO = GROUP_ID + INFO;
    public static final String REGION_SUMMARY = REGION_ID + SUMMARY;
    public static final String REGION_INFO = REGION_ID + INFO;
    public static final String TRIBE_SUMMARY = TRIBE_ID + SUMMARY;
    public static final String TRIBE_INFO = TRIBE_ID + INFO;
    public static final String PERSON = "/person";
    public static final String PERSON_ID = "/person/{id}";

    public static final String EVENT = "/event";
    public static final String EVENT_ID = "/event/{id}";

    public static final String SECURITY_BASE = "/security";
    public static final String USERS = "/users";
    public static final String AUTHENTICATED = "/authenticated";
    public static final String USER = USERS + "/user";

    public static final int EVENTS_DEFAULT_MONTH_PAST = 1;
    public static final int EVENTS_DEFAULT_WEEKS_FUTURE = 2;
    public static final int REPORT_DEFAULT_MONTH_PAST = 3;

    public static final String PROFILE_TEST = "test";
    public static final String PROFILE_DEV = "dev";
    public static final String PROFILE_PROD = "prod";
}
