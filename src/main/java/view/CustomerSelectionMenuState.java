package view;

import controller.CustomerController;
import model.Customer;

import java.util.Objects;

public class CustomerSelectionMenuState extends CustomerMenuState {

    private boolean allowRemoved;

    public CustomerSelectionMenuState(CustomerController customerController, boolean allowRemoved) {
        super(customerController);
        this.allowRemoved = allowRemoved;
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
                defineCustomerForContext(allowRemoved);
                break;
            case 2:
                toCustomerBrowserMenuOption(allowRemoved);
                break;
            case 0:
                returnToPreviousMenuOption();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }
}
