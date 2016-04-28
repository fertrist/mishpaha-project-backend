package org.project.people.data.dao;

import java.util.List;

/**
 * Created by fertrist on 25.09.15.
 */
public interface GenericDao<T> {

    int save(T entity);

    int update(T entity);

    int delete(int id);

    T get(int id);

    List<T> list();

}
