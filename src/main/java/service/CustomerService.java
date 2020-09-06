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
    private Session session = HibernateUtil.getSession();
    private Transaction transaction = null;

    public void add(Customer customer) {
        transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(customer);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Customer findById(Long id) {
        Customer customer = null;
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
            Root<Customer> root = criteriaQuery.from(Customer.class);
            Predicate[] predicates = new Predicate[2];
            predicates[0] = criteriaBuilder.isFalse(root.get("isRemoved"));
            predicates[1] = criteriaBuilder.equal(root.get("id"), id);
            criteriaQuery.select(root).where(predicates);
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
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
            Root<Customer> root = criteriaQuery.from(Customer.class);
            Predicate[] predicates = new Predicate[3];
            predicates[0] = criteriaBuilder.isFalse(root.get("isRemoved"));
            predicates[1] = criteriaBuilder.like(root.get("lastName"), "%" + lastName + "%");
            predicates[2] = criteriaBuilder.like(root.get("firstName"), "%" + firstName + "%");
            criteriaQuery.select(root).where(predicates);
            Query<Customer> query = session.createQuery(criteriaQuery);
            customers = query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public Customer findByAllButId(Customer customer) {
        Customer result = null;
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
            Root<Customer> root = criteriaQuery.from(Customer.class);
            Predicate[] predicates = new Predicate[6];
            predicates[0] = criteriaBuilder.isFalse(root.get("isRemoved"));
            predicates[1] = criteriaBuilder.equal(root.get("lastName"), customer.getLastName());
            predicates[2] = criteriaBuilder.equal(root.get("firstName"), customer.getFirstName());
            predicates[3] = criteriaBuilder.equal(root.get("addressStreet"), customer.getAddressStreet());
            predicates[4] = criteriaBuilder.equal(root.get("addressPostalCode"), customer.getAddressPostalCode());
            predicates[5] = criteriaBuilder.equal(root.get("addressCity"), customer.getAddressCity());
            criteriaQuery.select(root).where(predicates);
            try {
                Query<Customer> query = session.createQuery(criteriaQuery);
                result = query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Customer> findAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
            Root<Customer> root = criteriaQuery.from(Customer.class);
            criteriaQuery.select(root).
                    where(criteriaBuilder.isFalse(root.get("isRemoved"))).
                    orderBy(criteriaBuilder.asc(root.get("lastName")));
            Query<Customer> query = session.createQuery(criteriaQuery);
            customers = query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return customers;
    }

    //==============================
    //=========To refactor==========
    //==============================

    public Customer findByIdRemovedIncl(Long id) {
        Customer customer = null;
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
            Root<Customer> root = criteriaQuery.from(Customer.class);
            Predicate predicates = criteriaBuilder.equal(root.get("id"), id);
            criteriaQuery.select(root).where(predicates);
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

    public List<Customer> findByFullNameRemovedIncl(String lastName, String firstName) {
        List<Customer> customers = new ArrayList<>();
        try {
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

    public Customer findByAllButIdRemovedIncl(Customer customer) {
        Customer result = null;
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
            Root<Customer> root = criteriaQuery.from(Customer.class);
            Predicate[] predicates = new Predicate[5];
            predicates[0] = criteriaBuilder.equal(root.get("lastName"), customer.getLastName());
            predicates[1] = criteriaBuilder.equal(root.get("firstName"), customer.getFirstName());
            predicates[2] = criteriaBuilder.equal(root.get("addressStreet"), customer.getAddressStreet());
            predicates[3] = criteriaBuilder.equal(root.get("addressPostalCode"), customer.getAddressPostalCode());
            predicates[4] = criteriaBuilder.equal(root.get("addressCity"), customer.getAddressCity());
            criteriaQuery.select(root).where(predicates);
            try {
                Query<Customer> query = session.createQuery(criteriaQuery);
                result = query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Customer> findAllCustomersRemovedIncl() {
        List<Customer> customers = new ArrayList<>();
        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Customer> criteriaQuery = criteriaBuilder.createQuery(Customer.class);
            Root<Customer> root = criteriaQuery.from(Customer.class);
            criteriaQuery.select(root).
                    orderBy(criteriaBuilder.asc(root.get("lastName")));
            Query<Customer> query = session.createQuery(criteriaQuery);
            customers = query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return customers;
    }

    //==============================
    //==============================
    //==============================

    public void update(Customer customer) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(customer);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Customer customer = findById(id);
            if (Objects.nonNull(customer)) {
                session.delete(customer);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void delete(Customer customer) {
        Transaction transaction = null;
        customer = anonymizeFields(customer);
        try {
            transaction = session.beginTransaction();
            session.update(customer);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void checkForDummyCustomer() {
        Customer dummyCustomer = new Customer("<deleted>", "<deleted>", "<deleted>", "<deleted>", "<deleted>");
        if(Objects.isNull(findByAllButId(dummyCustomer))) {
            addDummyCustomer(dummyCustomer);
        }

    }

    private void addDummyCustomer(Customer customer) {
        add(customer);
    }

    private Customer anonymizeFields(Customer customer) {
        String anonymized = "*****";
        customer.setLastName(anonymized);
        customer.setFirstName(anonymized);
        customer.setAddressStreet(anonymized);
        customer.setAddressPostalCode(anonymized);
        customer.setAddressCity(anonymized);
        customer.setRemoved(true);
        return customer;
    }

    public void initialCustomers() {
        List<Customer> customers = List.of(
                new Customer("Kowalski", "Jan", "Al. Ujazdowskie", "00-500", "Warszawa"),
                new Customer("Burak", "Stefan", "Puławska", "00-100", "Kraków"),
                new Customer("Zembrzuski", "Adrian", "Głęboka", "22-445", "Katowice"),
                new Customer("Kijanowska", "Agata", "Żabia", "05-174", "Międzyzdroje"),
                new Customer("Bilski", "Jakub", "Mazowiecka", "35-544", "Grabowo")
        );
        for (Customer c : customers) {
            add(c);
        }
    }
}
