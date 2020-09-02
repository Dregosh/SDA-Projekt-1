package view;

import controller.CustomerController;
import model.Customer;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class CustomerMenuState extends MenuState {
    protected CustomerController customerController;

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
        setCustomerFields(customer);
        if (hasMoreThanOneNull(customer)) {
            System.out.println("Invalid customer");
            reportOperationFailed();
            return;
        }
        showCustomerBeforeAdd(customer);
        System.out.print("Save customer in the DataBase? ");
        if (userConfirms()) {
            customerController.addNewCustomer(customer);
            reportOperationSuccessful();
        } else {
            reportOperationCancelled();
        }
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
        Customer customer = defineCustomerForContext();
        if (Objects.isNull(customer)) {
            reportOperationCancelled();
            return;
        }
        System.out.print("Customer selected for deletion: ");
        showFormattedCustomer(customer);
        if (userConfirms()) {
            customerController.removeCustomer(customer);
            reportOperationSuccessful();
        } else {
            reportOperationCancelled();
        }
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
            String attributeName;
            switch (input) {
                case 1:
                    attributeName = "last name";
                    String newLastName = defineAttribute(BLANK_INPUT_NOT_ALLOWED, attributeName);
                    if (Objects.nonNull(newLastName)) {
                        modifiedCustomer.setLastName(newLastName);
                    } else if (Objects.isNull(customer.getLastName())) {
                        invalidValueWarning(attributeName);
                    }
                    break;
                case 2:
                    attributeName = "first name";
                    String newFirstName = defineAttribute(BLANK_INPUT_NOT_ALLOWED, attributeName);
                    if (Objects.nonNull(newFirstName)) {
                        modifiedCustomer.setFirstName(newFirstName);
                    } else if (Objects.isNull(customer.getFirstName())) {
                        invalidValueWarning(attributeName);
                    }
                    break;
                case 3:
                    attributeName = "street address";
                    String newAddressStreet = defineAttribute(BLANK_INPUT_NOT_ALLOWED, attributeName);
                    if (Objects.nonNull(newAddressStreet)) {
                        modifiedCustomer.setAddressStreet(newAddressStreet);
                    } else if (Objects.isNull(customer.getAddressStreet())) {
                        invalidValueWarning(attributeName);
                    }
                    break;
                case 4:
                    attributeName = "postal code";
                    String newAddressPostalCode = defineAttribute(BLANK_INPUT_NOT_ALLOWED, attributeName);
                    if (Objects.nonNull(newAddressPostalCode)) {
                        modifiedCustomer.setAddressPostalCode(newAddressPostalCode);
                    } else if (Objects.isNull(customer.getAddressPostalCode())) {
                        invalidValueWarning(attributeName);
                    }
                    break;
                case 5:
                    attributeName = "city";
                    String newAddressCity = defineAttribute(BLANK_INPUT_NOT_ALLOWED, attributeName);
                    if (Objects.nonNull(newAddressCity)) {
                        modifiedCustomer.setAddressCity(newAddressCity);
                    } else if (Objects.isNull(customer.getAddressCity())) {
                        invalidValueWarning(attributeName);
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
                invalidInput(attributeName);
            }
        }
    }

    private void invalidInput(String attributeName) {
        System.out.println(attributeName + " cannot be empty");
    }

    private void invalidValueWarning(String attributeName) {
        System.out.println("WARNING: " + attributeName + " cannot be left empty");
    }

    private boolean hasMoreThanOneNull(Customer customer) {
        List<String> nullFields = new LinkedList<>();
        for (Field f : customer.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (Objects.isNull(f.get(customer))) {
                    nullFields.add(f.getName());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return nullFields.size() > 1;
    }

    private void showCustomerBeforeAdd(Customer customer) {
        String customerBeforeAddFmt = "%-16s | %-16s | %-20s | %-7s | %s\n";
        System.out.printf(customerBeforeAddFmt, customer.getLastName(), customer.getFirstName(),
                customer.getAddressStreet(), customer.getAddressPostalCode(), customer.getAddressCity());
    }

    protected void returnToPreviousMenuOption() {
        customerController.returnToPreviousMenu();
    }
}