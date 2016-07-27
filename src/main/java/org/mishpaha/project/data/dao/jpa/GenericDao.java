package org.mishpaha.project.data.dao.jpa;

import java.util.List;

public interface GenericDao<T> {

    T save(T entity);

    T update(T entity);

    int delete(int id);

    T get(int id);

    List<T> list();

}
