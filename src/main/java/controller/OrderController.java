package controller;

import model.Order;
import service.OrderService;
import view.MenuState;

import java.util.Deque;

public class OrderController {
    private final Deque<MenuState> states;
    private final OrderService orderService;

    public OrderController(Deque<MenuState> states) {
        this.states = states;
        this.orderService = new OrderService();
    }

    public void addNewOrderToDB(Order order) {
        orderService.addOrder(order);
    }

    public void returnToPreviousMenu() {
        states.pop();
    }
}
