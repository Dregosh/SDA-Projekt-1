package controller;

import service.ProductService;
import view.MenuState;

import java.util.Deque;

public class ProductController {
    private Deque<MenuState> states;
    private ProductService productService;

    public ProductController(Deque<MenuState> states) {
        this.states = states;
        this.productService = new ProductService();
    }


    public void returnToPreviousMenu() {
        states.pop();
    }
}
