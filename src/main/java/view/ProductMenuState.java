package view;

import controller.ProductController;
import model.Product;
import model.ProductType;

import java.util.Objects;

public class ProductMenuState extends MenuState {
    protected final ProductController productController;
    public static final int NULL_FIELDS_THRESHOLD_FOR_PRODUCT = 2;

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
        Product product = new Product();
        setProductFields(product, BLANK_INPUT_NOT_ALLOWED, ZERO_NOT_ALLOWED);
        if (validateObjectFieldsNonNull(product, NULL_FIELDS_THRESHOLD_FOR_PRODUCT)) {
            System.out.println("Invalid product");
            reportOperationFailed();
            return;
        }
        if(existsInDb(product)) {
            reportOperationFailed();
            return;
        }
        showFormattedProduct(product);
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
        setProductFields(product, BLANK_INPUT_ALLOWED, ZERO_ALLOWED);
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
        } else {
            reportOperationCancelled();
        }
    }

    protected void returnToPreviousMenuOption() {
        productController.returnToPreviousMenu();
    }

    public void setProductFields(Product product, boolean allowBlank,
                                     boolean allowZero) {
        int input;
        do {
            System.out.println("\nChoose attribute: ");
            System.out.print("(1) Name");
            menuDisplayAttribute(product.getName());
            System.out.print("(2) Type");
            menuDisplayAttribute(product.getType());
            System.out.print("(3) Price per piece");
            if (Objects.nonNull(product.getPrice())) {
                System.out.printf(" <%.2f>\n", product.getPrice());
            } else {
                System.out.println();
            }
            System.out.print("(4) Amount in stock");
            menuDisplayAttribute(product.getAmount());
            System.out.println("(0) Finish");
            System.out.print("> ");
            input = (int) requestNumberInput(BLANK_INPUT_NOT_ALLOWED);
            switch (input) {
                case 1:
                    String newName = defineProductName(allowBlank);
                    if (!newName.isBlank()) {
                        product.setName(newName);
                    }
                    break;
                case 2:
                    if (allowZero) {
                        System.out.print("(0) <ORIGINAL TYPE> ");
                    }
                    showEnumTypes(ProductType.class);
                    int newTypeNumber = defineLegitEnumTypeNumber(
                            ProductType.class, allowZero);
                    if (newTypeNumber != 0) {
                        product.setType(ProductType.values()[newTypeNumber - 1]);
                    }
                    break;
                case 3:
                    double newPrice = defineProductPrice(allowBlank);
                    if (newPrice != BLANK_INPUT_MARKER) {
                        product.setPrice(newPrice);
                    }
                    break;
                case 4:
                    int newAmount = defineAmountInStock(allowBlank);
                    if (newAmount != BLANK_INPUT_MARKER) {
                        product.setAmount(newAmount);
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (input != 0);
    }

    public String defineProductName(boolean allowBlank) {
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

    public double defineProductPrice(boolean allowBlank) {
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

    public int defineAmountInStock(boolean allowBlank) {
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

    private boolean existsInDb(Product product) {
        Product searchResult = productController.findProductByNameAndType(product);
        if (Objects.nonNull(searchResult)) {
            System.out.println("Product with such name and Type combination already " +
                               "exists in the DataBase on ID = " + searchResult.getId() +
                               ". Try updating that one instead.");
        }
        return Objects.nonNull(productController.findProductByNameAndType(product));
    }
}
