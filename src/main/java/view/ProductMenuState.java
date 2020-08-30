package view;

import controller.ProductController;

public class ProductMenuState extends MenuState {
    private ProductController productController;

    public ProductMenuState(ProductController productController) {
        this.productController = productController;
    }

    public void show() {
        System.out.println("1. Show all products");
        System.out.println("0. Return to previous menu");
        int input = in.nextInt();
        switch (input) {
            case 1:
                break;
            case 0:
            default:
                productController.returnToPreviousMenu();
        }
    }
}
