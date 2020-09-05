package view;

import controller.OrderController;
import controller.ProductController;
import model.Order;
import model.OrderItem;
import model.Product;

import java.util.List;
import java.util.Objects;

public class OrderItemMenuState extends OrderMenuState {
    private final Order orderForContext;

    public OrderItemMenuState(OrderController orderController,
                              ProductController productController,
                              Order orderForContext) {
        super(orderController, productController, null);
        this.orderForContext = orderForContext;
    }

    @Override
    public void show() {
        System.out.println(orderController.getModelDeltaMap());
        System.out.println("\nORDER ITEMS");
        showNumberedOrderItemsList();
        System.out.println("OPTIONS: ");
        System.out.println("(1) Add new item");
        System.out.println("(2) Edit existing item");
        System.out.println("(3) Remove existing item");
        System.out.println("(0) Finish");
        System.out.print("> ");
        int input = (int) requestNumberInput(BLANK_INPUT_NOT_ALLOWED);
        switch (input) {
            case 1:
                addNewOrderItemOption();
                break;
            case 2:
                editOrderItemOption();
                break;
            case 3:
                removeOrderItemOption();
                break;
            case 0:
                returnToPreviousMenuOption();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    private void addNewOrderItemOption() {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(orderForContext);
        productController.toProductSelectionMenu();
        Product product = productController.getModelProduct();
        if (Objects.isNull(product)) {
            reportOperationCancelled();
            return;
        }
        for (OrderItem item : orderForContext.getOrderItems()) {
            if (product.getId().equals(item.getProduct().getId())) {
                System.out.println("Product is already on this list. Try editing " +
                                   "instead.");
                reportOperationCancelled();
                return;
            }
        }
        if (product.getAmount() == 0) {
            System.out.println("Product is currently out of stock");
            reportOperationCancelled();
            return;
        }
        orderItem.setProduct(product);
        int salesAmount = defineOrderItemSalesAmount(product, 0, BLANK_INPUT_NOT_ALLOWED);
        orderController.getModelDeltaMap().put(product.getId(), -(salesAmount));
        orderItem.setSalesAmount(salesAmount);
        orderItem.setDiscountPercent(defineOrderItemSalesDiscount(
                product, DISCOUNT_THRESHOLD, BLANK_INPUT_NOT_ALLOWED));
        double salesPrice = product.getPrice() *
                            ((double) (100 - orderItem.getDiscountPercent()) / 100);
        System.out.printf("SALES PRICE: %.2f\n", salesPrice);
        orderItem.setSalesPrice(salesPrice);
        orderForContext.getOrderItems().add(orderItem);
    }

    private void editOrderItemOption() {
        System.out.print("Enter the number of item to select it: ");
        int input = (int) requestNumberInput(BLANK_INPUT_NOT_ALLOWED);
        if (input <= 0 || input > orderForContext.getOrderItems().size()) {
            System.out.println("Number not in range of the list");
            reportOperationCancelled();
            return;
        }
        int index = input - 1;
        OrderItem chosenItem = orderForContext.getOrderItems().get(index);
        System.out.println("Item selected for the edition: ");
        showFormattedOrderItem(chosenItem);
        Product product =
                productController.findProductById(chosenItem.getProduct().getId());
        int previousAmountOnOrder = chosenItem.getSalesAmount();
        int salesAmount = defineOrderItemSalesAmount(
                product, previousAmountOnOrder, BLANK_INPUT_NOT_ALLOWED);
        orderController.getModelDeltaMap().put(
                product.getId(), previousAmountOnOrder - salesAmount);
        chosenItem.setSalesAmount(salesAmount);
        chosenItem.setDiscountPercent(defineOrderItemSalesDiscount(
                product, DISCOUNT_THRESHOLD, BLANK_INPUT_NOT_ALLOWED));
        double salesPrice = product.getPrice() *
                            ((double) (100 - chosenItem.getDiscountPercent()) / 100);
        System.out.printf("SALES PRICE: %.2f\n", salesPrice);
        chosenItem.setSalesPrice(salesPrice);
        orderForContext.getOrderItems().remove(index);
        orderForContext.getOrderItems().add(chosenItem);
    }

    private void removeOrderItemOption() {
        System.out.print("Enter the number of item to select it: ");
        int input = (int) requestNumberInput(BLANK_INPUT_NOT_ALLOWED);
        if (input <= 0 || input > orderForContext.getOrderItems().size()) {
            System.out.println("Number not in range of the list");
            reportOperationCancelled();
            return;
        }
        int index = input - 1;
        Long productId = orderForContext.getOrderItems().get(index).getProduct().getId();
        Integer delta = orderForContext.getOrderItems().get(index).getSalesAmount();
        orderController.getModelDeltaMap().put(productId, delta);
        orderForContext.getOrderItems().remove(index);
    }

    private int defineOrderItemSalesAmount(
            Product product, int modifier, boolean allowBlank) {
        boolean legitInput;
        int salesAmount;
        do {
            System.out.print("SALES AMOUNT: enter sales amount (min = 1, " +
                             "max = " + (product.getAmount() + modifier) + "): ");
            salesAmount = (int) requestNumberInput(allowBlank);
            if (salesAmount == BLANK_INPUT_MARKER && allowBlank) {
                return BLANK_INPUT_MARKER;
            }
            legitInput =
                    (salesAmount > 0 && salesAmount <= product.getAmount() + modifier);
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

    private void showNumberedOrderItemsList() {
        List<OrderItem> items = orderForContext.getOrderItems();
        if (items.size() > 0) {
            for (int i = 0; i < items.size(); i++) {
                System.out.print((i + 1) + ". ");
                showFormattedOrderItem(items.get(i));
            }
        } else {
            System.out.println("\t<no items added yet>");
        }
    }
}
