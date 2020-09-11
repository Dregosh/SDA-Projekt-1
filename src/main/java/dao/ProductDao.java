package dao;

import model.Product;
import model.ProductType;

import java.util.List;

public interface ProductDao extends BaseDao<Product> {
    List<Product> findByNameFragment(String nameFragment);
    List<Product> findByType(ProductType type);
    Product findByNameAndType(Product product);
    List<Product> findAllSortedByTypeAndName();
}
