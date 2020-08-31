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
        int input = (int) requestNumberInput(BLANK_INPUT_NOT_ALLOWED);
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
                returnToPreviousMenuOption();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    private void findProductByIdOption() {
        reportNotImplented();
    }

    private void findProductByNameOption() {
        reportNotImplented();
    }

    private void showAllProductsOption() {
        reportNotImplented();
    }
}