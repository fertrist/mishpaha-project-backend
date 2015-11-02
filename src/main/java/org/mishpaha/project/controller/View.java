package org.mishpaha.project.controller;

/**
 * Created by fertrist on 29.10.15.
 */
public class View {

    /**
     * Basic info home group and name
     */
    public interface Summary{}

    /**
     * For stuff like tribe, region, group list.
     */
    public interface ExtendedSummary extends Summary{}

    /**
     * For stuff like tribe, region, group info list.
     */
    public interface FullInfo extends ExtendedSummary {}
}
