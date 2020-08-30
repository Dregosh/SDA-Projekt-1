package controller;

import service.CustomerService;
import view.CustomerBrowserMenuState;
import view.MenuState;

import java.util.Deque;
import java.util.Scanner;

public class CustomerController {
    private Deque<MenuState> states;
    private CustomerService customerService;

    public CustomerController(Deque<MenuState> states) {
        this.states = states;
        this.customerService = new CustomerService();
    }

    public void addNewCustomer(){

    }

    public void updateExistingCustomer() {

    }

    public void removeCustomer() {

    }

    public void toCustomerBrowserMenu() {
        states.push(new CustomerBrowserMenuState(this));
    }

    public void returnToPreviousMenu() {
        states.pop();
    }
}
