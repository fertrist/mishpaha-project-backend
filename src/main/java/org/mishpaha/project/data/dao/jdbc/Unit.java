package org.mishpaha.project.data.dao.jdbc;

public interface Unit {
    Units getUnit();
    void setId(int id);
    int getId();

    enum Units {
        GROUP, REGION, TRIBE
    }
}
