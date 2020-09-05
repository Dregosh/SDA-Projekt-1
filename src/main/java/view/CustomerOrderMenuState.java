package view;

import controller.CustomerController;
import model.Customer;

public class CustomerOrderMenuState extends CustomerMenuState {
    public CustomerOrderMenuState(CustomerController customerController) {
        super(customerController);
    }

    @Override
    public void show() {
        System.out.println("\nCUSTOMER SELECTION MENU");
        System.out.println("(1) Add and select a customer");
        System.out.println("(2) Select customer by ID");
        System.out.println("(3) Customer Browser Menu...");
        System.out.println("(0) Cancel operation");
        System.out.print("> ");
        int input = (int) requestNumberInput(BLANK_INPUT_NOT_ALLOWED);
        switch (input) {
            case 1:
                Customer customer = addNewCustomerOption();
                customerController.setModelCustomer(customer);
                customerController.returnToPreviousMenu();
                break;
            case 2:
                defineCustomerForContext();
                break;
            case 3:
                toCustomerBrowserMenuOption();
                break;
            case 0:
                returnToPreviousMenuOption();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }
}
