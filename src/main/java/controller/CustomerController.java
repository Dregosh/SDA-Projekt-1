package controller;

import model.Customer;
import service.CustomerService;
import view.CustomerBrowserMenuState;
import view.MenuState;

import java.util.Deque;
import java.util.List;

public class CustomerController {
    private Deque<MenuState> states;
    private CustomerService customerService;

    public CustomerController(Deque<MenuState> states) {
        this.states = states;
        this.customerService = new CustomerService();
    }

    public void addNewCustomer(Customer customer){
        customerService.add(customer);
    }

    public void updateExistingCustomer(Customer customer) {
        customerService.update(customer);
    }

    public void removeCustomer(Customer customer) {
        customerService.delete(customer);
    }

    public void toCustomerBrowserMenu() {
        states.push(new CustomerBrowserMenuState(this));
    }

    public Customer findCustomerById(long id) {
        return customerService.findById(id);
    }

    public List<Customer> findCustomerByFullName(String lastName, String firstName) {
        return customerService.findByFullName(lastName, firstName);
    }

    public List<Customer> findAllCustomers() {
        return customerService.findAllCustomers();
    }

    public void returnToPreviousMenu() {
        states.pop();
    }
}
