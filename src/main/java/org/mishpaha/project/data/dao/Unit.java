package org.mishpaha.project.data.dao;

public interface Unit {
    Units getUnit();
    int getId();

    enum Units {
        GROUP, REGION, TRIBE
    }
}
