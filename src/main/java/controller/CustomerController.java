package controller;

import service.CustomerService;
import view.MenuState;

import java.util.Deque;

public class CustomerController {
    private Deque<MenuState> states;
    private CustomerService customerService;

    public CustomerController(Deque<MenuState> states) {
        this.states = states;
        this.customerService = new CustomerService();
    }

    public void returnToPreviousMenu() {
        states.pop();
    }
}
