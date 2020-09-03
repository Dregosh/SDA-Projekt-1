package view;

import controller.OrderController;
import controller.ProductController;
import model.Customer;
import model.Order;
import model.OrderStatus;

import java.time.LocalDate;
import java.util.Objects;

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
        System.out.print("SET ORDER DATE: ");
        order.setOrderDate(defineDate());
        order.setCustomer(defineCustomer());
        order.setStatus(defineStatus());
        System.out.print("SET PAYMENT DATE: ");
        order.setPaymentDate(defineDate());
        System.out.print("Save order in the DataBase? ");
        if (userConfirms()) {
            orderController.addNewOrderToDB(order);
        } else {
            reportOperationCancelled();
        }
        if (Objects.nonNull(order.getId())) {
            System.out.println("sekcja pozycji orderów");
            while (promptUserForOrderItemInsert()) {
                addNewOrderItem(order);
            }
        }
    }

    private LocalDate defineDate() {
        System.out.print("enter the date in format 'yyyy-mm-dd' " +
                         "or leave field blank to use current date: ");
        return requestDateInput(BLANK_INPUT_ALLOWED);
    }

    private Customer defineCustomer() {
        //customer selection logic to be implemented here..
        System.out.println("SET CUSTOMER: <using hardcoded Customer persisted in DB" +
                           " under the ID #01>");
        Customer customer = new Customer();
        customer.setId(1L);
        return customer;
    }

    private OrderStatus defineStatus() {
        System.out.print("SET ORDER STATUS: ");
        showEnumTypes(OrderStatus.class);
        int typeNumber = defineLegitEnumTypeNumber(OrderStatus.class, ZERO_NOT_ALLOWED);
        return OrderStatus.values()[typeNumber - 1];
    }

    private void addNewOrderItem(Order order) {

    }

    private boolean promptUserForOrderItemInsert() {
        return false;
    }

    private void returnToPreviousMenuOption() {
        orderController.returnToPreviousMenu();
    }
}
