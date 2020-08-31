package view;

import controller.CustomerController;
import model.Customer;

import java.util.Objects;

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
        int input = (int) requestNumberInput(BLANK_INPUT_NOT_ALLOWED);
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
                returnToPreviousMenuOption();
            default:
                System.out.println("Invalid choice");
        }
    }

    private void addNewCustomerOption() {
        Customer customer = new Customer();
        in.nextLine();
        System.out.print("Enter the last name: ");
        customer.setLastName(in.nextLine());
        System.out.println();
        System.out.print("Enter the first name: ");
        customer.setFirstName(in.nextLine());
        System.out.println();
        System.out.print("Enter the name of the street, the building number and the apartment number: ");
        customer.setAddressStreet(in.nextLine());
        System.out.println();
        System.out.print("Enter the postal code: ");
        customer.setAddressPostalCode(in.nextLine());
        System.out.println();
        System.out.print("Enter the name of the city : ");
        customer.setAddressCity(in.nextLine());
        customerController.addNewCustomer(customer);
    }

    private void updateExistingCustomerOption() {
        defineCustomerForContext();
        customerController.updateExistingCustomer();
    }

    private void removeCustomerOption() {
        customerController.removeCustomer();
    }

    private void toCustomerBrowserMenuOption() {
        customerController.toCustomerBrowserMenu();
    }

    private Customer defineCustomerForContext() {
        int input;
        do {
            System.out.print("Enter ID of the customer or 0 to cancel: ");
            input = (int) requestNumberInput(BLANK_INPUT_NOT_ALLOWED);
            if (input != 0) {
                Customer customer = customerController.findCustomerById(input);
                if (Objects.isNull(customer)) {
                    System.out.println("Customer not found.");
                } else {
                    System.out.print("Found: ");
                    showFormattedCustomer(customer);
                    if (userConfirms()) {
                        return customer;
                    }
                }
            }
        } while (input != 0);
        return null;
    }

    protected void returnToPreviousMenuOption() {
        customerController.returnToPreviousMenu();
    }
}
