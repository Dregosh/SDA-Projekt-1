package controller;

import model.Customer;
import service.CustomerService;
import view.CustomerBrowserMenuState;
import view.CustomerOrderMenuState;
import view.CustomerSelectionMenuState;
import view.MenuState;

import java.util.Deque;
import java.util.List;

public class CustomerController {
    private Deque<MenuState> states;
    private CustomerService customerService;
    private Customer modelCustomer;

    public CustomerController(Deque<MenuState> states) {
        this.states = states;
        this.customerService = new CustomerService();
    }

    public Customer getModelCustomer() {
        return modelCustomer;
    }

    public void setModelCustomer(Customer modelCustomer) {
        this.modelCustomer = modelCustomer;
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

    public void toCustomerSelectionMenu() {
        modelCustomer = null;
        int loopMarker = states.size();
        states.push(new CustomerSelectionMenuState(this));
        while (loopMarker < states.size()) {
            states.getFirst().show();
        }
    }

    public void toCustomerOrderMenu() {
        modelCustomer = null;
        int loopMarker = states.size();
        states.push(new CustomerOrderMenuState(this));
        while (loopMarker < states.size()) {
            states.getFirst().show();
        }
    }

    public Customer findCustomerById(long id) {
        return customerService.findById(id);
    }

    public Customer findCustomerByAllButId(Customer customer) {
        return customerService.findByAllButId(customer);
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
