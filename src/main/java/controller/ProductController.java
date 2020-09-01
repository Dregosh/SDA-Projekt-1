package controller;

import model.Product;
import model.ProductType;
import service.ProductService;
import view.MenuState;
import view.ProductBrowserMenuState;

import java.util.Deque;
import java.util.List;

public class ProductController {
    private Deque<MenuState> states;
    private ProductService productService;

    public ProductController(Deque<MenuState> states) {
        this.states = states;
        this.productService = new ProductService();
    }

    public void addNewProductToDB(Product product) {
        productService.addProduct(product);
    }

    public void updateProductInDB(Product product) {
        productService.updateProduct(product);
    }

    public void removeProductFromDB(Product product) {
        productService.removeProduct(product);
    }

    public void toProductBrowserMenu() {
        states.push(new ProductBrowserMenuState(this));
    }

    public Product findProductById(long id) {
        return productService.findProductById(id);
    }

    public List<Product> findProductsByName(String nameFragment) {
        return productService.findProductsByNameFragment(nameFragment);
    }

    public List<Product> findProductsByType(ProductType type) {
        return productService.findProductsByType(type);
    }

    public List<Product> showAllProducts() {
        return productService.findAllProducts();
    }

    public void returnToPreviousMenu() {
        states.pop();
    }
}