package view;

import model.Order;
import model.OrderItem;
import model.Customer;
import model.Product;
import model.ProductType;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public abstract class MenuState {
    protected final Scanner in = new Scanner(System.in);
    protected static final boolean BLANK_INPUT_ALLOWED = true;
    protected static final boolean BLANK_INPUT_NOT_ALLOWED = false;
    protected static final boolean ZERO_ALLOWED = true;
    protected static final boolean ZERO_NOT_ALLOWED = false;
    protected static final boolean REMOVED_ALLOWED = true;
    protected static final boolean REMOVED_NOT_ALLOWED = false;
    protected static final int BLANK_INPUT_MARKER = -1;
    protected static final String PRODUCT_INFO_FMT =
            "(#%02d) %-20s| %-12s| %-9.2f| %-2d pc(s)\n";
    protected static final String CUSTOMER_LABEL_FMT =
            "ID %-2s | Last name %-6s | First name %-5s | Street %-13s | Postal code | City\n";
    protected static final String CUSTOMER_INFO_FMT =
            "(#%02d) | %-16s | %-16s | %-20s | %-11s | %s\n";
    protected static final String ORDER_INFO_FMT =
            "Order #%d (%s %s), placed on: %s, status: %s, paid on: %s\n";
    protected static final String ITEMS_INFO_FMT =
            "\tItem: %s %s, sales price: %.2f, given discount: %d%%, sales amount: %d\n";

    abstract public void show();

    protected double requestNumberInput(boolean allowBlank) {
        while (true) {
            try {
                String rawInput = in.nextLine();
                if (rawInput.isBlank() && allowBlank) {
                    return BLANK_INPUT_MARKER;
                }
                return Double.parseDouble(rawInput);
            } catch (NumberFormatException e) {
                System.out.println("Input must be a number");
                System.out.print("> ");
            }
        }
    }

    public LocalDate requestDateInput(boolean allowBlank) {
        while (true) {
            try {
                String rawInput = in.nextLine();
                if (rawInput.isBlank() && allowBlank) {
                    return LocalDate.now();
                }
                return LocalDate.parse(rawInput);
            } catch (DateTimeParseException e) {
                System.out.println("Date must be in format: 'yyyy-mm-dd'");
                System.out.print("> ");
            }
        }
    }

    protected <E extends Enum<E>> int defineLegitEnumTypeNumber(
            Class<E> enumClass, boolean allowZero) {
        int typeNumber;
        boolean legitInput;
        do {
            System.out.print("Enter the Type number: ");
            typeNumber = (int) requestNumberInput(BLANK_INPUT_NOT_ALLOWED);
            legitInput = verifyInputInRangeOfEnumTypes(
                    enumClass, allowZero, typeNumber);
            if (!legitInput) {
                System.out.println("Invalid number. Try again.");
            }
        } while (!legitInput);
        return typeNumber;
    }

    protected <E extends Enum<E>> void showEnumTypes(Class<E> enumClass) {
        for (int i = 0; i < enumClass.getEnumConstants().length; i++) {
            System.out.print("(" + (i + 1) + ") " +
                             enumClass.getEnumConstants()[i] + " ");
        }
        System.out.println();
    }

    public <E extends  Enum<E>> boolean verifyInputInRangeOfEnumTypes(
            Class<E> enumClass, boolean allowZero, int number) {
        int rangeMin = allowZero ? 0 : 1;
        int rangeMax = enumClass.getEnumConstants().length;
        return (number >= rangeMin && number <= rangeMax);
    }

    public <T> boolean validateObjectFieldsNonNull(T object, int nullsThreshold) {
        List<String> nullFields = new LinkedList<>();
        for (Field f : object.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (Objects.isNull(f.get(object))) {
                    nullFields.add(f.getName());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return nullFields.size() > nullsThreshold;
    }

    protected void showFormattedProduct(Product product) {
        if (Objects.nonNull(product)) {
            Long id = Objects.nonNull(product.getId()) ? product.getId() : 0L;
            System.out.printf(PRODUCT_INFO_FMT, id, product.getName(), product.getType(),
                              product.getPrice(), product.getAmount());
        } else {
            reportFoundNothing();
        }
    }

    protected void showFormattedCustomer(Customer customer) {
        String formattedCustomer = String.format(CUSTOMER_INFO_FMT, customer.getId(), customer.getLastName(), customer.getFirstName(),
                customer.getAddressStreet(), customer.getAddressPostalCode(), customer.getAddressCity());
        System.out.print(formattedCustomer);
    }

    public void showFormatterOrder(Order order) {
        if (Objects.nonNull(order)) {
            Long id = Objects.nonNull(order.getId()) ? order.getId() : 0L;
            String paymentDate = Objects.nonNull(order.getPaymentDate()) ?
                                 order.getPaymentDate().toString() : "<not paid yet>";
            System.out.printf(ORDER_INFO_FMT, id, order.getCustomer().getLastName(),
                              order.getCustomer().getFirstName(), order.getOrderDate(),
                              order.getStatus(), paymentDate);
            if (order.getOrderItems() != null && order.getOrderItems().size() > 0) {
                for (OrderItem item : order.getOrderItems()) {
                    showFormattedOrderItem(item);
                }
            } else {
                System.out.println("\t<this order contains zero items>");
            }
        } else {
            System.out.println("<nothing to display>");
        }
    }

    public void showFormattedOrderItem(OrderItem item) {
        if (Objects.nonNull(item)) {
            System.out.printf(ITEMS_INFO_FMT, item.getProduct().getName(),
                              item.getProduct().getType(),
                              item.getSalesPrice(),
                              item.getDiscountPercent(),
                              item.getSalesAmount());
        } else {
            System.out.println("<nothing to display>");
        }
    }

    protected void showFormattedProducts(List<Product> products) {
        if (products.size() > 0) {
            for (Product p : products) {
                showFormattedProduct(p);
            }
        } else {
            reportFoundNothing();
        }
    }

    protected void showFormattedCustomers(List<Customer> customers) {
        if (customers.size() > 0) {
            for (Customer c : customers) {
                showFormattedCustomer(c);
            }
        } else {
            System.out.println("<found nothing>");
        }
    }

    public void showFormattedOrders(List<Order> orders) {
        if (orders.size() > 0) {
            for (Order o : orders) {
                showFormatterOrder(o);
                System.out.println();
            }
        } else {
            System.out.println("<nothing to display>");
        }
    }

    protected void menuDisplayAttribute(Object o) {
        if (Objects.nonNull(o) && !("").equals(o)) {
            System.out.print(" <" + o + ">");
        }
        System.out.print("\n");
    }

    protected void reportNotImplented() {
        System.out.println("Option not implemented yet.");
    }

    protected void reportOperationSuccessful() {
        System.out.println("Operation successful");
    }

    protected void reportOperationCancelled() {
        System.out.println("Operation cancelled");
    }

    protected void reportFoundNothing() {
        System.out.println("<found nothing>");
    }

    protected void reportOperationFailed() {
        System.out.println("Operation failed");
    }

    protected boolean userConfirms() {
        System.out.print("Press 'Y' to confirm: ");
        return "Y".equals(in.nextLine().toUpperCase());
    }

    private void separatorLine(String string) {
        System.out.println("=".repeat(Math.max(0, string.length() - 1)));
    }
}
