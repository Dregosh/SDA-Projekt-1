package controller;

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

    public void returnToPreviousMenu() {
        states.pop();
    }
}
