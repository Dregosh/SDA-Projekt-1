package view;

import controller.ProductController;

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

    private void toProductBrowserMenuOption() {
        productController.toProductBrowserMenu();
    }

    private void removeProductOption() {
        reportNotImplentedInfo();
    }

    private void updateExistingProductOption() {
        reportNotImplentedInfo();
    }

    private void addNewProductOption() {
        reportNotImplentedInfo();
    }

    protected void returnToPreviousMenuOption() {
        productController.returnToPreviousMenu();
    }
}