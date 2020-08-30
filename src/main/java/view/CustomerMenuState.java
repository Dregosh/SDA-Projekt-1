package view;

import controller.CustomerController;

import java.util.Scanner;

public class CustomerMenuState extends MenuState {
    private CustomerController customerController;

    public CustomerMenuState(CustomerController customerController) {
        this.customerController = customerController;
    }

    public void show() {
        System.out.println("\nCUSTOMER MENU");
        System.out.println("(1) Add a new customer to the DataBase");
        System.out.println("(2) Update an existing customer");
        System.out.println("(3) Remove a customer from the DataBase");
        System.out.println("(4) To Customer Browser Menu...");
        System.out.println("(0) Return to the previous menu");
        System.out.print("> ");
        int input = in.nextInt();
        switch (input) {
            case 1:
                addNewCustomerOption();
                break;
            case 2:
                updateExistingCustomerOption();
                break;
            case 3:
                removeCustomerOption();
                break;
            case 4:
                toCustomerBrowserMenuOption();
                break;
            case 0:
            default:
                returnToPreviousMenuOption();
        }
    }

    private void addNewCustomerOption(){
        customerController.addNewCustomer();
    }

    private void updateExistingCustomerOption() {
        customerController.updateExistingCustomer();
    }

    private void removeCustomerOption() {
        customerController.removeCustomer();
    }

    private void toCustomerBrowserMenuOption() {
        customerController.toCustomerBrowserMenu();
    }

    protected void returnToPreviousMenuOption() {
        customerController.returnToPreviousMenu();
    }
}
