package view;

import controller.OrderController;
import controller.ProductController;

public class OrderMenuState extends MenuState {
    private final OrderController orderController;
    private final ProductController productController;

    public OrderMenuState(OrderController orderController,
                          ProductController productController) {
        this.orderController = orderController;
        this.productController = productController;
    }

    @Override
    public void show() {
        System.out.println("\nORDER MENU");
        System.out.println("(1) Order Browser Menu...");
        System.out.println("(2) Create new order");
        //other order-related options to be added here
        System.out.println("(0) Return to previous menu");
        System.out.print("> ");
        int input = (int) requestNumberInput(BLANK_INPUT_NOT_ALLOWED);
        switch (input) {
            case 1:
                reportNotImplented();
                break;
            case 2:
                createNewOrderOption();
                break;
            case 0:
                returnToPreviousMenuOption();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    private void createNewOrderOption() {
        reportNotImplented();
    }

    private void returnToPreviousMenuOption() {
        orderController.returnToPreviousMenu();
    }
}
