package view;

import controller.CustomerController;
import controller.OrderController;
import controller.ProductController;
import model.*;

import java.time.LocalDate;
import java.util.Objects;

public class OrderMenuState extends MenuState {
    private final OrderController orderController;
    private final ProductController productController;
    private final CustomerController customerController;
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
        //System.out.println("(4) Edit existing order");
        //other order-related options to be added here
        System.out.println("(0) Return to previous menu");
        System.out.print("> ");
        int input = (int) requestNumberInput(BLANK_INPUT_NOT_ALLOWED);
        switch (input) {
            case 1:
                showAllOrdersInDBOption();
                break;
            case 2:
                reportNotImplented();
                break;
            case 3:
                createOrEditOrderOption(new Order());
                break;
//            case 4:
//                editExistingOrderOption();
//                break;
            case 0:
                returnToPreviousMenuOption();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    private void createOrEditOrderOption(Order order) {
        setOrderFields(order);
        if (validateObjectFieldsNonNull(order, NULL_FIELDS_THRESHOLD_FOR_ORDER) ||
            order.getOrderItems().size() == 0) {
            System.out.println("Invalid order");
            reportOperationFailed();
            return;
        }
        showFormatterOrder(order);
        System.out.print("Save changes in the DataBase? ");
        if (userConfirms()) {
            orderController.addOrUpdateOrderInDB(order);
            reportOperationSuccessful();
        } else {
            reportOperationCancelled();
        }
    }

    private void editExistingOrderOption() {
        Order order = defineOrderForContext();
        if (Objects.nonNull(order)) {
            setOrderFields(order);
            createOrEditOrderOption(order);
        }
    }

    private Order defineOrderForContext() {
        System.out.print("Enter order ID: ");
        long input = (long) requestNumberInput(BLANK_INPUT_NOT_ALLOWED);
        Order order = orderController.findOrderById(input);
        if (Objects.isNull(order)) {
            System.out.println("Order not found");
            reportOperationCancelled();
        }
        return order;
    }

    private void setOrderFields(Order order) {
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
                    //Selection of other customer to be implemented here..
                    order.setCustomer(defineCustomer());
                    break;
                case 3:
                    System.out.println("SET ORDER STATUS: ");
                    order.setStatus(defineStatus());
                    break;
                case 4:
                    System.out.print("SET PAYMENT DATE: ");
                    order.setPaymentDate(defineDate());
                    break;
                case 5:
                    OrderItem orderItem = createNewOrderItem(order);
                    if (Objects.nonNull(orderItem)) {
                        order.getOrderItems().add(orderItem);
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (input != 0);
    }

    private LocalDate defineDate() {
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
        int typeNumber = defineLegitEnumTypeNumber(OrderStatus.class, ZERO_ALLOWED);
        return OrderStatus.values()[typeNumber - 1];
    }

    private OrderItem createNewOrderItem(Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        productController.toProductSelectionMenu();
        Product product = productController.getModelProduct();
        if (Objects.isNull(product) || product.getAmount() == 0) {
            if (Objects.nonNull(product) && product.getAmount() == 0) {
                System.out.println("Product is currently out of stock");
            }
            reportOperationCancelled();
            return null;
        }
        orderItem.setProduct(product);
        int salesAmount = defineOrderItemSalesAmount(product, BLANK_INPUT_NOT_ALLOWED);
        product.setAmount(product.getAmount() - salesAmount);
        orderItem.setSalesAmount(salesAmount);
        orderItem.setDiscountPercent(defineOrderItemSalesDiscount(
                product, DISCOUNT_THRESHOLD, BLANK_INPUT_NOT_ALLOWED));
        double salesPrice = product.getPrice() *
                            ((double) (100 - orderItem.getDiscountPercent()) / 100);
        System.out.printf("SALES PRICE: %.2f\n", salesPrice);
        orderItem.setSalesPrice(salesPrice);
        showFormattedOrderItem(orderItem);
        return orderItem;
    }

    private int defineOrderItemSalesAmount(Product product, boolean allowBlank) {
        boolean legitInput;
        int salesAmount;
        do {
            System.out.print("SALES AMOUNT: enter sales amount (min = 1, " +
                             "max = " + product.getAmount() + "): ");
            salesAmount = (int) requestNumberInput(allowBlank);
            if (salesAmount == BLANK_INPUT_MARKER && allowBlank) {
                return BLANK_INPUT_MARKER;
            }
            legitInput = (salesAmount > 0 && salesAmount <= product.getAmount());
            if (!legitInput) {
                System.out.println("Amount not within given range");
            }
        } while (!legitInput);
        return salesAmount;
    }

    private Integer defineOrderItemSalesDiscount(
            Product product, int discountThreshold, boolean allowBlank) {
        boolean legitInput;
        int salesDiscount;
        System.out.println("DISCOUNT %: enter discount in range [0, " +
                           discountThreshold + "]%  (0 will leave original product " +
                           "price = " + product.getPrice() + "): ");
        do {
            System.out.print("> ");
            salesDiscount = (int) requestNumberInput(allowBlank);
            if (salesDiscount == BLANK_INPUT_MARKER && allowBlank) {
                return BLANK_INPUT_MARKER;
            }
            legitInput = (salesDiscount >= 0 && salesDiscount <= discountThreshold);
            if (!legitInput) {
                System.out.println("Discount not within given range");
            }
        } while (!legitInput);
        return salesDiscount;
    }

    private void showAllOrdersInDBOption() {
        showFormattedOrders(orderController.findAllOrders());
    }

    protected void returnToPreviousMenuOption() {
        orderController.returnToPreviousMenu();
    }
}
