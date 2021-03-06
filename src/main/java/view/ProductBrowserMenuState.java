package view;

import controller.ProductController;
import model.Product;
import model.ProductType;

import java.util.List;

public class ProductBrowserMenuState extends ProductMenuState {

    public ProductBrowserMenuState(ProductController productController) {
        super(productController);
    }

    @Override
    public void show() {
        System.out.println("\nPRODUCT BROWSER MENU");
        System.out.println("(1) Find products by name");
        System.out.println("(2) Find products by type");
        System.out.println("(3) Show all products");
        System.out.println("(0) Return to previous menu");
        System.out.print("> ");
        int input = (int) requestNumberInput(BLANK_INPUT_NOT_ALLOWED);
        switch (input) {
            case 1:
                findProductByNameOption();
                break;
            case 2:
                findProductByTypeOption();
                break;
            case 3:
                showAllProductsOption();
                break;
            case 0:
                returnToPreviousMenuOption();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    private void findProductByNameOption() {
        System.out.print("Enter product name fragment: ");
        String nameFragment = in.nextLine();
        List<Product> products = productController.findProductsByName(nameFragment);
        showFormattedProducts(products);
    }

    private void findProductByTypeOption() {
        System.out.print("(0) ANY ");
        showEnumTypes(ProductType.class);
        int typeNumber = defineLegitEnumTypeNumber(ProductType.class, ZERO_ALLOWED);
        if (typeNumber != 0) {
            ProductType type = ProductType.values()[typeNumber - 1];
            List<Product> products = productController.findProductsByType(type);
            showFormattedProducts(products);
        } else {
            showAllProductsOption();
        }
    }

    private void showAllProductsOption() {
        List<Product> products = productController.showAllProducts();
        showFormattedProducts(products);
    }
}