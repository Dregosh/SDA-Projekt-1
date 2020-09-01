package view;

import model.Product;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public abstract class MenuState {
    protected final Scanner in = new Scanner(System.in);
    protected static final boolean BLANK_INPUT_ALLOWED = true;
    protected static final boolean BLANK_INPUT_NOT_ALLOWED = false;
    protected static final int BLANK_INPUT_MARKER = -1;
    protected static final String PRODUCT_INFO_FMT =
            "(#%02d) %-20s| %-12s| %-9.2f| %-2d pc(s)\n";

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

    protected void showFormattedProduct(Product product) {
        if (Objects.nonNull(product)) {
            System.out.printf(PRODUCT_INFO_FMT, product.getId(), product.getName(),
                              product.getType(), product.getPrice(), product.getAmount());
        } else {
            reportFoundNothing();
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

    protected boolean userConfirms() {
        System.out.print("Press 'Y' to confirm: ");
        return "Y".equals(in.nextLine().toUpperCase());
    }
}
