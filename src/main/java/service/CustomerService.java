package service;

import model.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class CustomerService {

    public void add(Customer customer) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();
            session.save(customer);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void find() {

    }

    public void update() {

    }

    public void delete() {

    }

}
