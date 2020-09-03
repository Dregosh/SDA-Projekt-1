package view;

import controller.OrderController;
import controller.ProductController;
import model.Customer;
import model.Order;
import model.OrderStatus;

import java.time.LocalDate;

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
        Order order = new Order();
        order.setOrderDate(defineOrderDate());
        order.setCustomer(defineCustomer());
        order.setStatus(defineStatus());
        order.setPaymentDate(definePaymentDate());
        orderController.addNewOrderToDB(order);
        while (promptUserForOrderItemInsert()) {
            addNewOrderItem(order);
        }
    }

    private LocalDate defineOrderDate() {
        System.out.print("Enter order date in format yyyy-mm-dd " +
                         "or leave field blank to use current date: ");
        String input = in.nextLine();
        LocalDate orderDate = LocalDate.now();
        if (!input.isBlank()) {
            orderDate = LocalDate.parse(input);
        }
        return orderDate;
    }

    private Customer defineCustomer() {
        //customer selection logic to be implemented here..
        Customer customer = new Customer();
        customer.setLastName("Nowak");
        customer.setFirstName("Jan");
        customer.setAddressStreet("Żabki 2/4");
        customer.setAddressPostalCode("05-507");
        customer.setAddressCity("Kraków");
        return customer;
    }

    private OrderStatus defineStatus() {
        return null;
    }

    private boolean promptUserForOrderItemInsert() {
        return false;
    }

    private Double definePaymentDate() {
        return null;
    }

    private void addNewOrderItem(Order order) {
        
    }

    private void returnToPreviousMenuOption() {
        orderController.returnToPreviousMenu();
    }
}
