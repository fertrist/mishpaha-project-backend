package org.project.people.controller;

/**
 * Created by fertrist on 29.10.15.
 */
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
