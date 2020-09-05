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
import java.util.Map;

public class OrderService {
    private Session session = HibernateUtil.getSession();
    private Transaction transaction = null;

    public void addOrUpdateOrder(Order order, Map<Long, Integer> deltaMap) {
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(order);
            deltaMap.forEach((key, value) -> {
                Product product = session.find(Product.class, key);
                product.setAmount(product.getAmount() + value);
            });
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addOrderItem(OrderItem orderItem) {
        try {
            transaction = session.beginTransaction();
            session.save(orderItem);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Order> findAllOrders() {
        List<Order> orders = new ArrayList<>();
        try {
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
        try {
            order = session.find(Order.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }
}
