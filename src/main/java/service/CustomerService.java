package service;

import model.Customer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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

            Query<Customer> query = session.createQuery(criteriaQuery);
            customer = query.getSingleResult();

        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public void update() {

    }

    public void delete() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();

            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initialCustomers() {
        CustomerService customerService = new CustomerService();

        Customer customer = new Customer();
        customer.setLastName("Kowalski");
        customer.setFirstName("Jan");
        customer.setAddressStreet("Ujazdowskie");
        customer.setAddressPostalCode("00-500");
        customer.setAddressCity("Warszawa");

        customerService.add(customer);

        Customer customer1 = new Customer();
        customer.setLastName("Burak");
        customer.setFirstName("Stefan");
        customer.setAddressStreet("Puławska");
        customer.setAddressPostalCode("00-100");
        customer.setAddressCity("Kraków");

        customerService.add(customer1);

    }

}
