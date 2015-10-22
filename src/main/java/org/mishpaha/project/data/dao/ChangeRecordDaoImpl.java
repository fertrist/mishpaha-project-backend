package org.mishpaha.project.data.dao;

import org.mishpaha.project.data.model.ChangeRecord;

import javax.sql.DataSource;
import java.util.List;

import static org.mishpaha.project.util.TestUtil.getQuotedString;

/**
 * Created by fertrist on 24.09.15.
 */
public class ChangeRecordDaoImpl extends DaoImplementation<ChangeRecord> {

    public ChangeRecordDaoImpl(DataSource dataSource, String table) {
        super(dataSource, table);
    }

    @Override
    public int save(ChangeRecord entity) {
        return operations.update(String.format(INSERT, table, "personId, groupId, wasAdded, comment",
            new StringBuilder("" + entity.getPersonId()).append(",").append(entity.getGroupId()).append(",")
            .append(getQuotedString(String.valueOf(entity.isWasAdded()))).append(",").append(entity.getComment())
        ));
    }

    @Override
    public int update(ChangeRecord entity) {
        return 0;
    }

    @Override
    public ChangeRecord get(int id) {
        return null;
    }

    @Override
    public List<ChangeRecord> list() {
        return null;
    }
}
