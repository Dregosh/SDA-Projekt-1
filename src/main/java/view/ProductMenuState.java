package view;

import controller.ProductController;
import model.Product;
import model.ProductType;

import java.util.Objects;

public class ProductMenuState extends MenuState {
    private ProductController productController;

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
        int input = in.nextInt();
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
            default:
                returnToPreviousMenuOption();
        }
    }

    private void addNewProductOption() {
        System.out.print("Enter product name: ");
        String productName = in.nextLine();
        for (int i = 0; i < ProductType.values().length; i++) {
            System.out.print("(" + (i + 1) + ") " + ProductType.values()[i] + " ");
        }
        System.out.println();
        System.out.print("Enter product type number: ");
        int prodTypeNo = in.nextInt();
        ProductType prodType = ProductType.values()[prodTypeNo - 1];
        System.out.print("Enter product price: ");
        double prodPrice = in.nextDouble();
        System.out.print("Enter amount of products in stock: ");
        int prodAmount = in.nextInt();
        Product product = new Product(productName, prodType, prodPrice, prodAmount);
        productController.addNewProductToDB(product);
    }

    private void updateExistingProductOption() {
        productSelectionProcess();
    }

    private void productSelectionProcess() {
        System.out.print("Enter product ID or 0 to cancel: ");
        long input = in.nextLong();
        Product product = productController.findProductById(input);
        if (Objects.isNull(product)) {
            System.out.println("Product not found");
        } else {
            System.out.println("Found: " + product);
        }
    }

    private void removeProductOption() {
        reportNotImplentedInfo();
    }

    private void toProductBrowserMenuOption() {
        productController.toProductBrowserMenu();
    }

    protected void returnToPreviousMenuOption() {
        productController.returnToPreviousMenu();
    }
}