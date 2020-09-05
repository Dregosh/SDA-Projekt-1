package controller;

import util.HibernateUtil;
import view.CustomerMenuState;
import view.MainMenuState;
import view.MenuState;
import view.OrderMenuState;
import view.ProductMenuState;

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
        states.push(new MainMenuState(this));
        while (!states.isEmpty()) {
            states.getFirst().show();
        }
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
        HibernateUtil.closeSession();
    }
}
