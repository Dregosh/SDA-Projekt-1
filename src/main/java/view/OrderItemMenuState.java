package view;

import controller.CustomerController;
import controller.OrderController;
import controller.ProductController;

public class OrderItemMenuState extends OrderMenuState {
    public OrderItemMenuState(OrderController orderController, ProductController productController, CustomerController customerController) {
        super(orderController, productController, customerController);
    }

    @Override
    public void show() {
        System.out.println("\nORDER ITEM MENU");
        System.out.println("(1) Add new item");
        System.out.println("(2) Edit existing item");
        System.out.println("(3) Remove existing item");
        System.out.println("(0) Finish");
        System.out.print("> ");
        int input = (int) requestNumberInput(BLANK_INPUT_NOT_ALLOWED);
        switch (input) {
            case 1:
                reportNotImplented();
                break;
            case 2:
                reportNotImplented();
                break;
            case 3:
                reportNotImplented();
                break;
            case 0:
                returnToPreviousMenuOption();
            default:
                System.out.println("Invalid choice");
        }
    }
}
