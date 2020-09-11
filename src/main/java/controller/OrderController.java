package controller;

import model.Customer;
import model.Order;
import service.OrderService;
import view.MenuState;
import view.OrderItemMenuState;

import java.util.Deque;
import java.util.List;
import java.util.Map;

public class OrderController {
    private final Deque<MenuState> states;
    private final OrderService orderService;
    private Order modelOrder;
    private Map<Long, Integer> modelDeltaMap;

    public OrderController(Deque<MenuState> states) {
        this.states = states;
        this.orderService = new OrderService();
    }

    public void addOrUpdateOrderInDB(Order order) {
        orderService.saveOrUpdateOrder(order, modelDeltaMap);
    }

    public Order findOrderByIdWithOrderItems(long id) {
        return orderService.findOrderByIdWithOrderItems(id);
    }

    public List<Order> findAllOrdersWithOrderItems() {
        return orderService.findAllOrdersWithOrderItems();
    }

    public List<Order> findByCustomerWithOrderItems(Customer customer) {
        return orderService.findByCustomerWithOrderItems(customer);
    }

    public void toOrderItemMenu(Order order) {
        modelOrder = order;
        int loopMarker = states.size();
        states.push(new OrderItemMenuState(this, new ProductController(states), order));
        while (loopMarker < states.size()) {
            states.getFirst().show();
        }
    }

    public void returnToPreviousMenu() {
        states.pop();
    }

    public Order getModelOrder() {
        return modelOrder;
    }

    public void setModelOrder(Order modelOrder) {
        this.modelOrder = modelOrder;
    }

    public Map<Long, Integer> getModelDeltaMap() {
        return modelDeltaMap;
    }

    public void setModelDeltaMap(Map<Long, Integer> modelDeltaMap) {
        this.modelDeltaMap = modelDeltaMap;
    }
}
