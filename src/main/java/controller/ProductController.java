package controller;

import model.Product;
import model.ProductType;
import service.ProductService;
import view.MenuState;
import view.ProductBrowserMenuState;
import view.ProductSelectionMenuState;

import java.util.Deque;
import java.util.List;

public class ProductController {
    private final Deque<MenuState> states;
    private final ProductService productService;
    private Product modelProduct;

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

    public void toProductSelectionMenu() {
        modelProduct = null;
        int loopMarker = states.size();
        states.push(new ProductSelectionMenuState(this));
        while (loopMarker < states.size()) {
            states.getFirst().show();
        }
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

    public Product getModelProduct() {
        return modelProduct;
    }

    public void setModelProduct(Product modelProduct) {
        this.modelProduct = modelProduct;
    }
}
