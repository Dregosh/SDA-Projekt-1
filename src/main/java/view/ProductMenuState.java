package view;

import controller.ProductController;
import model.Product;
import model.ProductType;

import java.util.Objects;

public class ProductMenuState extends MenuState {
    private final ProductController productController;
    public static final boolean BLANK_INPUT_ALLOWED = true;
    public static final boolean BLANK_INPUT_NOT_ALLOWED = false;
    public static final boolean ZERO_ALLOWED = true;
    public static final boolean ZERO_NOT_ALLOWED = false;

    public ProductMenuState(ProductController productController) {
        this.productController = productController;
    }

    public void show() {
        System.out.println("\nPRODUCT MENU");
        System.out.println("(1) Add new product to DataBase");
        System.out.println("(2) Update existing product");
        System.out.println("(3) Remove product from DataBase");
        System.out.println("(4) To Product Browser Menu...");
        System.out.println("(0) Return to previous menu");
        System.out.print("> ");
        int input = (int) requestNumberInput();
        switch (input) {
            case 1:
                addNewProductOption();
                break;
            case 2:
                updateExistingProductOption();
                break;
            case 3:
                removeProductOption();
                break;
            case 4:
                toProductBrowserMenuOption();
                break;
            case 0:
                returnToPreviousMenuOption();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    private void addNewProductOption() {
        String name = defineProductName(BLANK_INPUT_NOT_ALLOWED);
        showProductTypes();
        int typeNumber = defineLegitProductTypeNumber(ZERO_NOT_ALLOWED);
        ProductType type = ProductType.values()[typeNumber - 1];
        double price = defineProductPrice();
        int amount = defineAmountInStock();
        Product product = new Product(name, type, price, amount);
        productController.addNewProductToDB(product);
        System.out.println("New product added to the DataBase");
    }

    private void updateExistingProductOption() {
        defineProductForContext();
    }

    private void removeProductOption() {
        reportNotImplented();
    }

    private void toProductBrowserMenuOption() {
        productController.toProductBrowserMenu();
    }

    protected void returnToPreviousMenuOption() {
        productController.returnToPreviousMenu();
    }

    private void defineProductForContext() {
        System.out.print("Enter product ID or 0 to cancel: ");
        int input;
        do {
            input = (int) requestNumberInput();
            Product product = productController.findProductById(input);
            if (Objects.isNull(product)) {
                System.out.println("Product not found");
            } else {
                System.out.println("Found: " + product);
            }
        } while (input != 0);
    }

    private String defineProductName(boolean allowBlank) {
        String productName = "";
        while (productName.isBlank()) {
            System.out.print("Enter product name: ");
            productName = in.nextLine();
            if (productName.isBlank() && allowBlank) {
                return productName;
            } else if (productName.isBlank()) {
                System.out.println("Name cannot be empty");
            }
        }
        return productName;
    }

    private int defineLegitProductTypeNumber(boolean allowZero) {
        int prodTypeNo;
        boolean legitInput;
        do {
            System.out.print("Enter product type number: ");
            prodTypeNo = (int) requestNumberInput();
            legitInput = verifyInputProductType(allowZero, prodTypeNo);
            if (!legitInput) {
                System.out.println("Invalid number. Try again.");
            }
        } while (!legitInput);
        return prodTypeNo;
    }

    private double defineProductPrice() {
        boolean legitInput;
        double prodPrice;
        do {
            System.out.print("Enter product price: ");
            prodPrice = requestNumberInput();
            legitInput = (prodPrice > 0);
            if (!legitInput) {
                System.out.println("Price must be greater than 0");
            }
        } while (!legitInput);
        return prodPrice;
    }

    private int defineAmountInStock() {
        boolean legitInput;
        int amount;
        do {
            System.out.print("Enter amount of products in stock: ");
            amount = (int) requestNumberInput();
            legitInput = (amount > 0);
            if (!legitInput) {
                System.out.println("Amount must be greater than 0");
            }
        } while (!legitInput);
        return amount;
    }

    protected void showProductTypes() {
        for (int i = 0; i < ProductType.values().length; i++) {
            System.out.print("(" + (i + 1) + ") " + ProductType.values()[i] + " ");
        }
        System.out.println();
    }

    protected boolean verifyInputProductType(boolean allowZero, int number) {
        int rangeMin = allowZero ? 0 : 1;
        int rangeMax = ProductType.values().length;
        return (number >= rangeMin && number <= rangeMax);
    }
}
