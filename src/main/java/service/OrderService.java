package service;

import model.Order;
import model.OrderItem;
import model.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    Transaction transaction = null;

    public void addOrder(Order order) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(order);
            order.getOrderItems().forEach(
                    orderItem -> {
                        Product product = session.find(
                                Product.class, orderItem.getProduct().getId());
                        product.setAmount(
                                product.getAmount() - orderItem.getSalesAmount());
                    }
            );
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addOrderItem(OrderItem orderItem) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(orderItem);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Order> findAllOrders() {
        List<Order> orders = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Order> query = builder.createQuery(Order.class);
            Root<Order> root = query.from(Order.class);
            query.select(root);
            orders = session.createQuery(query).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }

    public Order findOrderById(long id) {
        Order order = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            order = session.find(Order.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }
}
