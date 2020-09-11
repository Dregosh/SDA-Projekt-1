package dao;

import java.util.List;

public interface BaseDao<T> {
    void save(final T transientInstance);
    void update(final T detachedInstance);
    void delete(final T persistentInstance);
    void delete(final Long id, Class<T> tClass);
    T findById(final Long id, Class<T> tClass);
    List<T> findAll(Class<T> tClass);
}
