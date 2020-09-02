package view;

import controller.CustomerController;
import model.Customer;

import java.util.List;

public class CustomerBrowserMenuState extends CustomerMenuState {


    public CustomerBrowserMenuState(CustomerController customerController) {
        super(customerController);
    }

    @Override
    public void show() {
        System.out.println("\nCUSTOMER BROWSER MENU");
        System.out.println("(1) Find a customer by the ID");
        System.out.println("(2) Find customers by the last name and/or first name");
        System.out.println("(3) Show all customers");
        System.out.println("(0) Return to previous menu");
        System.out.print("> ");
        int input = (int) requestNumberInput(BLANK_INPUT_NOT_ALLOWED);
        switch (input) {
            case 1:
                findCustomerByIdOption();
                break;
            case 2:
                findCustomerByFullNameOption();
                break;
            case 3:
                showAllCustomersOption();
                break;
            case 0:
                returnToPreviousMenuOption();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    private void findCustomerByIdOption() {

    }

    private void findCustomerByFullNameOption() {
        System.out.println("Enter last name or leave blank");
        System.out.print("> ");
        String lastName = in.nextLine();
        System.out.println("Enter first name or leave blank");
        System.out.print("> ");
        String firstName = in.nextLine();
        List<Customer> customers = customerController.findCustomerByFullName(lastName, firstName);
        showFormattedCustomers(customers);
    }

    private void showAllCustomersOption() {
        List<Customer> customers = customerController.findAllCustomers();
        showFormattedCustomers(customers);
    }
}
