package view;

import controller.ProductController;
import model.Product;
import model.ProductType;

import java.util.Objects;

public class ProductMenuState extends MenuState {
    protected final ProductController productController;
    protected static final boolean ZERO_ALLOWED = true;
    protected static final boolean ZERO_NOT_ALLOWED = false;

    public ProductMenuState(ProductController productController) {
        this.productController = productController;
    }

    public void show() {
        System.out.println("\nPRODUCT MENU");
        System.out.println("(1) Product Browser Menu...");
        System.out.println("(2) Add new product to DataBase");
        System.out.println("(3) Update existing product in DataBase");
        System.out.println("(4) Remove product from DataBase");
        System.out.println("(0) Return to previous menu");
        System.out.print("> ");
        int input = (int) requestNumberInput(BLANK_INPUT_NOT_ALLOWED);
        switch (input) {
            case 1:
                toProductBrowserMenuOption();
                break;
            case 2:
                addNewProductOption();
                break;
            case 3:
                updateExistingProductOption();
                break;
            case 4:
                removeProductOption();
                break;
            case 0:
                returnToPreviousMenuOption();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    protected void toProductBrowserMenuOption() {
        productController.toProductBrowserMenu();
    }

    private void addNewProductOption() {
        String name = defineProductName(BLANK_INPUT_NOT_ALLOWED);
        showProductTypes();
        int typeNumber = defineLegitProductTypeNumber(ZERO_NOT_ALLOWED);
        ProductType type = ProductType.values()[typeNumber - 1];
        double price = defineProductPrice(BLANK_INPUT_NOT_ALLOWED);
        int amount = defineAmountInStock(BLANK_INPUT_NOT_ALLOWED);
        Product product = new Product(name, type, price, amount);
        System.out.print("Save product in the DataBase? ");
        if (userConfirms()) {
            productController.addNewProductToDB(product);
            reportOperationSuccessful();
        } else {
            reportOperationCancelled();
        }
    }

    private void updateExistingProductOption() {
        productController.toProductSelectionMenu();
        Product product = productController.getModelProduct();
        if (Objects.isNull(product)) {
            reportOperationCancelled();
            return;
        }
        System.out.print("Product selected for update: ");
        showFormattedProduct(product);
        System.out.println("Enter new data. Leave field blank (or use 0 in case of " +
                           "Type) to leave values unchanged.");
        String newName = defineProductName(BLANK_INPUT_ALLOWED);
        if (!newName.isBlank()) {
            product.setName(newName);
        }
        System.out.print("(0) <ORIGINAL TYPE> ");
        showProductTypes();
        int newTypeNumber = defineLegitProductTypeNumber(ZERO_ALLOWED);
        if (newTypeNumber != 0) {
            product.setType(ProductType.values()[newTypeNumber - 1]);
        }
        double newPrice = defineProductPrice(BLANK_INPUT_ALLOWED);
        if (newPrice != BLANK_INPUT_MARKER) {
            product.setPrice(newPrice);
        }
        int newAmount = defineAmountInStock(BLANK_INPUT_ALLOWED);
        if (newAmount != BLANK_INPUT_MARKER) {
            product.setAmount(newAmount);
        }
        showFormattedProduct(product);
        System.out.print("Proceed with update? ");
        if (userConfirms()) {
            productController.updateProductInDB(product);
            reportOperationSuccessful();
        } else {
            reportOperationCancelled();
        }
    }

    private void removeProductOption() {
        productController.toProductSelectionMenu();
        Product product = productController.getModelProduct();
        if (Objects.isNull(product)) {
            reportOperationCancelled();
            return;
        }
        System.out.print("Product selected to delete: ");
        showFormattedProduct(product);
        System.out.print("Proceed with delete? ");
        if (userConfirms()) {
            productController.removeProductFromDB(product);
            reportOperationSuccessful();
        } else {
            reportOperationCancelled();
        }
    }

    protected void returnToPreviousMenuOption() {
        productController.returnToPreviousMenu();
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

    protected int defineLegitProductTypeNumber(boolean allowZero) {
        int prodTypeNo;
        boolean legitInput;
        do {
            System.out.print("Enter product type number: ");
            prodTypeNo = (int) requestNumberInput(BLANK_INPUT_NOT_ALLOWED);
            legitInput = verifyInputProductType(allowZero, prodTypeNo);
            if (!legitInput) {
                System.out.println("Invalid number. Try again.");
            }
        } while (!legitInput);
        return prodTypeNo;
    }

    private double defineProductPrice(boolean allowBlank) {
        boolean legitInput;
        double prodPrice;
        do {
            System.out.print("Enter product price: ");
            prodPrice = requestNumberInput(allowBlank);
            if (prodPrice == -1 && allowBlank) {
                return BLANK_INPUT_MARKER;
            }
            legitInput = (prodPrice > 0);
            if (!legitInput) {
                System.out.println("Price must be greater than 0");
            }
        } while (!legitInput);
        return prodPrice;
    }

    private int defineAmountInStock(boolean allowBlank) {
        boolean legitInput;
        int amount;
        do {
            System.out.print("Enter amount of products in stock: ");
            amount = (int) requestNumberInput(allowBlank);
            if (amount == -1 && allowBlank) {
                return BLANK_INPUT_MARKER;
            }
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
