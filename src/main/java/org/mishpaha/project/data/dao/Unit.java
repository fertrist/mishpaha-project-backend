package org.mishpaha.project.data.dao;

public interface Unit {
    Units getUnit();
    void setId(int id);
    int getId();

    enum Units {
        GROUP, REGION, TRIBE
    }
}
