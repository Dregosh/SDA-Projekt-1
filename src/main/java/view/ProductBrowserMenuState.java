package view;

import controller.ProductController;

public class ProductBrowserMenuState extends ProductMenuState {

    public ProductBrowserMenuState(ProductController productController) {
        super(productController);
    }

    @Override
    public void show() {
        System.out.println("\nPRODUCT BROWSER MENU");
        System.out.println("(1) Find product by ID");
        System.out.println("(2) Find product by name");
        System.out.println("(3) Show all products");
        System.out.println("(0) Return to previous menu");
        System.out.print("> ");
        int input = in.nextInt();
        switch (input) {
            case 1:
                findProductByIdOption();
                break;
            case 2:
                findProductByNameOption();
                break;
            case 3:
                showAllProductsOption();
                break;
            case 0:
            default:
                returnToPreviousMenuOption();
        }
    }

    private void findProductByIdOption() {
        reportNotImplentedInfo();
    }

    private void findProductByNameOption() {
        reportNotImplentedInfo();
    }

    private void showAllProductsOption() {
        reportNotImplentedInfo();
    }
}