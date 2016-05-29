package org.mishpaha.project.util;

public class View {

    /**
     * Basic info like name, mid name, last name, category, nationality, charity.
     */
    public interface Summary{}

    /**
     * For stuff like tribe, region, group full info list.
     */
    public interface Info extends Summary {}
}
