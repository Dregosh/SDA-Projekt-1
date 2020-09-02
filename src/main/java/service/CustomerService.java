package service;

import model.Customer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomerService {

    public void add(Customer customer) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(customer);
            transaction.commit();
        } catch (HibernateException e) {
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Customer findById(Long id) {
        Customer customer = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
            Root<Customer> root = criteriaQuery.from(Customer.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"),id));
            try {
                Query<Customer> query = session.createQuery(criteriaQuery);
                customer = query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public List<Customer> findByFullName(String lastName, String firstName) {
        List<Customer> customers = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
            Root<Customer> root = criteriaQuery.from(Customer.class);
            Predicate[] predicates = new Predicate[2];
            predicates[0] = criteriaBuilder.like(root.get("lastName"), "%" + lastName + "%");
            predicates[1] = criteriaBuilder.like(root.get("firstName"), "%" + firstName + "%");
            criteriaQuery.select(root).where(predicates);
            Query<Customer> query = session.createQuery(criteriaQuery);
            customers = query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public List<Customer> findAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
            Root<Customer> root = criteriaQuery.from(Customer.class);
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get("lastName")));
            Query<Customer> query = session.createQuery(criteriaQuery);
            customers = query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public void update(Customer customer) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(customer);
            transaction.commit();
        } catch (HibernateException e) {
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Customer customer = findById(id);
            if(Objects.nonNull(customer)) {
                session.delete(customer);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void initialCustomers() {
        Customer customer = new Customer();
        customer.setLastName("Kowalski");
        customer.setFirstName("Jan");
        customer.setAddressStreet("Ujazdowskie");
        customer.setAddressPostalCode("00-500");
        customer.setAddressCity("Warszawa");
        add(customer);

        Customer customer1 = new Customer();
        customer1.setLastName("Burak");
        customer1.setFirstName("Stefan");
        customer1.setAddressStreet("Puławska");
        customer1.setAddressPostalCode("00-100");
        customer1.setAddressCity("Kraków");
        add(customer1);
    }

}
