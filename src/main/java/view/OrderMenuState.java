package view;

import controller.CustomerController;
import controller.OrderController;
import controller.ProductController;
import model.Customer;
import model.Order;
import model.OrderItem;
import model.OrderStatus;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;

public class OrderMenuState extends MenuState {
    protected final OrderController orderController;
    protected final ProductController productController;
    protected final CustomerController customerController;
    public static final int DISCOUNT_THRESHOLD = 15;
    public static final int NULL_FIELDS_THRESHOLD_FOR_ORDER = 1;

    public OrderMenuState(OrderController orderController,
                          ProductController productController,
                          CustomerController customerController) {
        this.orderController = orderController;
        this.productController = productController;
        this.customerController = customerController;
    }

    @Override
    public void show() {
        System.out.println("\nORDER MENU");
        System.out.println("(1) Show all orders in the DataBase");
        System.out.println("(2) Find orders of a chosen Customer");
        System.out.println("(3) Create new order");
        System.out.println("(4) Edit existing order");
        //other order-related options to be added here
        System.out.println("(0) Return to previous menu");
        System.out.print("> ");
        int input = (int) requestNumberInput(BLANK_INPUT_NOT_ALLOWED);
        switch (input) {
            case 1:
                showAllOrdersInDBOption();
                break;
            case 2:
                showOrdersByCustomerOption();
                break;
            case 3:
                createOrEditOrderOption(new Order());
                break;
            case 4:
                editExistingOrderOption();
                break;
            case 0:
                returnToPreviousMenuOption();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    private void createOrEditOrderOption(Order order) {
        orderController.setModelDeltaMap(new HashMap<>());
        for (OrderItem item : order.getOrderItems()) {
            orderController.getModelDeltaMap().put(item.getProduct().getId(), 0);
        }
        setOrderFields(order);
        if (validateObjectFieldsNonNull(order, NULL_FIELDS_THRESHOLD_FOR_ORDER) ||
            order.getOrderItems().size() == 0) {
            System.out.println("Invalid order");
            orderController.setModelDeltaMap(null);
            reportOperationFailed();
            return;
        }
        showFormatterOrder(order);
        System.out.print("Save changes in the DataBase? ");
        if (userConfirms()) {
            orderController.addOrUpdateOrderInDB(order);    //tu delta
            reportOperationSuccessful();
        } else {
            reportOperationCancelled();
        }
    }

    private void editExistingOrderOption() {
        Order order = defineOrderForContext();
        if (Objects.nonNull(order)) {
            createOrEditOrderOption(order);
        }
    }

    private Order defineOrderForContext() {
        System.out.print("Enter order ID: ");
        long input = (long) requestNumberInput(BLANK_INPUT_NOT_ALLOWED);
        Order order = orderController.findOrderByIdWithOrderItems(input);
        if (Objects.isNull(order)) {
            reportOperationCancelled();
        }
        return order;
    }

    public void setOrderFields(Order order) {
        int input;
        do {
            System.out.println("\nChoose attribute:");
            System.out.print("(1) Order date: ");
            menuDisplayAttribute(order.getOrderDate());
            System.out.print("(2) Customer: ");
            if (Objects.nonNull(order.getCustomer())) {
                showFormattedCustomer(order.getCustomer());
            } else {
                System.out.println();
            }
            System.out.print("(3) Status: ");
            menuDisplayAttribute(order.getStatus());
            System.out.print("(4) Payment date: ");
            menuDisplayAttribute(order.getPaymentDate());
            System.out.println("(5) Items: ");
            if (order.getOrderItems().size() > 0) {
                for (OrderItem item : order.getOrderItems()) {
                    showFormattedOrderItem(item);
                }
            } else {
                System.out.println("\t<order contains zero items>");
            }
            System.out.println("(0) Finish");
            System.out.print("> ");
            input = (int) requestNumberInput(BLANK_INPUT_NOT_ALLOWED);
            switch (input) {
                case 1:
                    System.out.print("SET ORDER DATE: ");
                    order.setOrderDate(defineDate());
                    break;
                case 2:
                    order.setCustomer(defineCustomer());
                    break;
                case 3:
                    order.setStatus(defineStatus());
                    break;
                case 4:
                    System.out.print("SET PAYMENT DATE: ");
                    order.setPaymentDate(defineDate());
                    break;
                case 5:
                    orderController.toOrderItemMenu(order);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (input != 0);
    }

    public LocalDate defineDate() {
        System.out.print("enter the date in format 'yyyy-mm-dd': ");
        return requestDateInput(BLANK_INPUT_ALLOWED);
    }

    private Customer defineCustomer() {
        customerController.toCustomerOrderMenu();
        Customer customer = customerController.getModelCustomer();
        if (Objects.isNull(customer)) {
            System.out.println("Invalid customer");
            return null;
        }
        return customer;
    }

    private OrderStatus defineStatus() {
        System.out.print("SET ORDER STATUS: ");
        showEnumTypes(OrderStatus.class);
        int typeNumber = defineLegitEnumTypeNumber(OrderStatus.class, ZERO_NOT_ALLOWED);
        return OrderStatus.values()[typeNumber - 1];
    }

    private void showAllOrdersInDBOption() {
        showFormattedOrders(orderController.findAllOrdersWithOrderItems());
    }

    private void showOrdersByCustomerOption() {
        customerController.toCustomerSelectionMenu(REMOVED_ALLOWED);
        Customer customer = customerController.getModelCustomer();
        showFormattedOrders(orderController.findByCustomerWithOrderItems(customer));
    }

    protected void returnToPreviousMenuOption() {
        orderController.returnToPreviousMenu();
    }
}
