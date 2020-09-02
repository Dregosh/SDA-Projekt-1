package view;

import controller.ProductController;
import model.Product;

import java.util.Objects;

public class ProductSelectionMenuState extends ProductMenuState {

    public ProductSelectionMenuState(ProductController productController) {
        super(productController);
    }

    @Override
    public void show() {
        System.out.println("\nPRODUCT SELECTION MENU");
        System.out.println("(1) Select product by ID");
        System.out.println("(2) Product Browser Menu...");
        System.out.println("(0) Cancel operation");
        System.out.print("> ");
        int input = (int) requestNumberInput(BLANK_INPUT_NOT_ALLOWED);
        switch (input) {
            case 1:
                defineProductForContext();
                break;
            case 2:
                toProductBrowserMenuOption();
                break;
            case 0:
                returnToPreviousMenuOption();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    protected void defineProductForContext() {
        long input;
        do {
            System.out.print("Enter product ID or 0 to cancel: ");
            input = (long) requestNumberInput(BLANK_INPUT_NOT_ALLOWED);
            if (input != 0) {
                Product product = productController.findProductById(input);
                showFormattedProduct(product);
                if (Objects.nonNull(product) && userConfirms()) {
                    productController.setModelProduct(product);
                    productController.returnToPreviousMenu();
                    return;
                }
            }
        } while (input != 0);
    }
}
