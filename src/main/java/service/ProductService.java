package service;

import model.Product;
import model.ProductType;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductService {
    private Transaction transaction = null;

    public void addProduct(Product product) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(product);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeProduct(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Product product = findProductById(id);
            if (Objects.nonNull(product)) {
                session.delete(product);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(Product product) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(product);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Product findProductById(Long id) {
        Product product = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            product = session.get(Product.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    public List<Product> findProductByNameFragment(String nameFragment) {
        List<Product> products = new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Product> query = builder.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);
            query.where(builder.like(root.get("name"), "%" + nameFragment + "%"));
            products = session.createQuery(query).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public void addInitialProductsToDB() {
        List<Product> products = List.of(
                new Product("Core i7-6700k", ProductType.CPU, 1240.0, 3),
                new Product("Core i7-9700k", ProductType.CPU, 1460.0, 1),
                new Product("BenQ 24\" ", ProductType.DISPLAY, 560.0, 2),
                new Product("Samsung-SSD 512GB", ProductType.DISC_DRIVE, 250.0, 3),
                new Product("OCZ-SSD 128GB", ProductType.DISC_DRIVE, 150.00, 2)
        );
        for (Product p : products) {
            addProduct(p);
        }
    }
}