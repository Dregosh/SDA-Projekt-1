package controller;

import view.MainMenuState;
import view.MenuState;
import view.ProductMenuState;

import java.util.ArrayDeque;
import java.util.Deque;

public class MainController {
    private final Deque<MenuState> states;
    private final ProductController productController;

    public MainController() {
        this.states = new ArrayDeque<>();
        this.productController = new ProductController(states);
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

    }

    public void toOrderMenu() {

    }

    public void exitProgram() {
        states.pop();
    }
}
