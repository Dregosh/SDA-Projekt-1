package controller;

import model.Order;
import model.OrderItem;
import service.OrderService;
import view.MenuState;

import java.util.Deque;
import java.util.List;

public class OrderController {
    private final Deque<MenuState> states;
    private final OrderService orderService;
    private List<OrderItem> modelOrderItems;

    public OrderController(Deque<MenuState> states) {
        this.states = states;
        this.orderService = new OrderService();
    }

    public List<OrderItem> getModelOrderItems() {
        return modelOrderItems;
    }

    public void setModelOrderItems(List<OrderItem> modelOrderItems) {
        this.modelOrderItems = modelOrderItems;
    }

    public void addOrUpdateOrderInDB(Order order) {
        orderService.addOrUpdateOrder(order);
    }

    public void addNewOrderItemToDB(OrderItem orderItem) {
        orderService.addOrderItem(orderItem);
    }

    public Order findOrderById(long id) {
        return orderService.findOrderById(id);
    }

    public List<Order> findAllOrders() {
        return orderService.findAllOrders();
    }

    public void returnToPreviousMenu() {
        states.pop();
    }
}
