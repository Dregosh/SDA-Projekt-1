package view;

import controller.CustomerController;
import model.Customer;

import java.util.Objects;

public class CustomerSelectionMenuState extends CustomerMenuState {

    public CustomerSelectionMenuState(CustomerController customerController) {
        super(customerController);
    }

    @Override
    public void show() {
        System.out.println("\nCUSTOMER SELECTION MENU");
        System.out.println("(1) Select customer by ID");
        System.out.println("(2) Customer Browser Menu...");
        System.out.println("(0) Cancel operation");
        System.out.print("> ");
        int input = (int) requestNumberInput(BLANK_INPUT_NOT_ALLOWED);
        switch (input) {
            case 1:
                defineCustomerForContext();
                break;
            case 2:
                toCustomerBrowserMenuOption();
                break;
            case 0:
                returnToPreviousMenuOption();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    protected void defineCustomerForContext() {
        int input;
        do {
            System.out.println("Enter ID of the customer or 0 to cancel");
            System.out.print("> ");
            input = (int) requestNumberInput(BLANK_INPUT_NOT_ALLOWED);
            if (input != 0) {
                Customer customer = customerController.findCustomerById(input);
                if (Objects.isNull(customer)) {
                    System.out.println("Customer not found.");
                } else {
                    System.out.print("Selected: ");
                    showFormattedCustomer(customer);
                    if (userConfirms()) {
                        customerController.setModelCustomer(customer);
                        customerController.returnToPreviousMenu();
                        return;
                    }
                }
            }
        } while (input != 0);
    }

}
