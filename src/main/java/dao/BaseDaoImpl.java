package dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BaseDaoImpl<T> implements BaseDao<T> {

    @Override
    public void save(T transientInstance) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(transientInstance);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(T detachedInstance) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(detachedInstance);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(T persistentInstance) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(persistentInstance);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id, Class<T> tClass) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            T persistentInstance = session.find(tClass, id);
            if (Objects.nonNull(persistentInstance)) {
                session.delete(persistentInstance);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public T findById(Long id, Class<T> tClass) {
        T persistentInstance = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            persistentInstance = session.find(tClass, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return persistentInstance;
    }

    @Override
    public List<T> findAll(Class<T> tClass) {
        List<T> results = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(tClass);
            query.from(tClass);
            results = session.createQuery(query).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
}
