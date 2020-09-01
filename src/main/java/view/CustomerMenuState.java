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
                break;
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
        Customer customer = defineCustomerForContext();
        if (Objects.isNull(customer)) {
            reportOperationCancelled();
            return;
        }
        System.out.print("Customer selected for the update: ");
        showFormattedCustomer(customer);
        customer = setCustomerFields(customer);
        showFormattedCustomer(customer);
        System.out.print("Proceed with update? ");
        if (userConfirms()) {
            customerController.updateExistingCustomer(customer);
            reportOperationSuccessful();
        } else {
            reportOperationCancelled();
        }
    }

    private void removeCustomerOption() {
        setCustomerFields(new Customer());
        customerController.removeCustomer();
    }

    private void toCustomerBrowserMenuOption() {
        customerController.toCustomerBrowserMenu();
    }

    private Customer defineCustomerForContext() {
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

    private Customer setCustomerFields(Customer customer) {
        int input;
        Customer modifiedCustomer = customer;
        do {
            System.out.println("Choose attribute: ");
            System.out.println("(1) Last name");
            System.out.println("(2) First name");
            System.out.println("(3) Street address");
            System.out.println("(4) Postal code");
            System.out.println("(5) City");
            System.out.println("(0) Finish");
            System.out.print("> ");
            input = (int) requestNumberInput(BLANK_INPUT_NOT_ALLOWED);
            switch (input) {
                case 1:
                    String newLastName = defineAttribute(BLANK_INPUT_NOT_ALLOWED, "last name");
                    if (Objects.nonNull(newLastName)) {
                        modifiedCustomer.setLastName(newLastName);
                    }
                    break;
                case 2:
                    String newFirstName = defineAttribute(BLANK_INPUT_NOT_ALLOWED, "first name");
                    if (Objects.nonNull(newFirstName)) {
                        modifiedCustomer.setFirstName(newFirstName);
                    }
                    break;
                case 3:
                    String newAddressStreet = defineAttribute(BLANK_INPUT_NOT_ALLOWED, "street address");
                    if (Objects.nonNull(newAddressStreet)) {
                        modifiedCustomer.setAddressStreet(newAddressStreet);
                    }
                    break;
                case 4:
                    String newAddressPostalCode = defineAttribute(BLANK_INPUT_NOT_ALLOWED, "postal code");
                    if (Objects.nonNull(newAddressPostalCode)) {
                        modifiedCustomer.setAddressPostalCode(newAddressPostalCode);
                    }
                    break;
                case 5:
                    String newAddressCity = defineAttribute(BLANK_INPUT_NOT_ALLOWED, "city");
                    if (Objects.nonNull(newAddressCity)) {
                        modifiedCustomer.setAddressCity(newAddressCity);
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (input != 0);
        return modifiedCustomer;
    }

    private String defineAttribute(boolean allowBlank, String attributeName) {
        String input = "";
        while (true) {
            System.out.println("Input " + attributeName + " or 0 to return");
            System.out.print("> ");
            input = in.nextLine();
            if (input.equals("0")) {
                return null;
            } else if (!input.isBlank()) {
                return input;
            } else if (input.isBlank() && allowBlank) {
                return input;
            } else if (input.isBlank()) {
                System.out.println(attributeName + " cannot be empty");
            }
        }
    }

    protected void returnToPreviousMenuOption() {
        customerController.returnToPreviousMenu();
    }
}
