package controller;

import util.HibernateUtil;
import view.*;

import java.util.ArrayDeque;
import java.util.Deque;

public class MainController {
    private final Deque<MenuState> states;
    private final ProductController productController;
    private final CustomerController customerController;
    private final OrderController orderController;

    public MainController() {
        this.states = new ArrayDeque<>();
        this.productController = new ProductController(states);
        this.customerController = new CustomerController(states);
        this.orderController = new OrderController(states);
    }

    public void start() {
        initializeDB();
        states.push(new MainMenuState(this));
        while (!states.isEmpty()) {
            states.getFirst().show();
        }
        HibernateUtil.shutdown();
    }

    public void toProductMenu() {
        states.push(new ProductMenuState(productController));
    }

    public void toCustomerMenu() {
        states.push(new CustomerMenuState(new CustomerController(states)));
    }

    public void toOrderMenu() {
        states.push(new OrderMenuState(orderController, productController, customerController));
    }

    public void exitProgram() {
        states.pop();
    }

    private void initializeDB() {
        productController.getProductService().addInitialProductsToDB();
        customerController.getCustomerService().initialCustomers();
    }
}
