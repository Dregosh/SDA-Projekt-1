package controller;

import view.MenuState;

import java.util.Deque;

public class ProductController {
    private Deque<MenuState> states;

    public ProductController(Deque<MenuState> states) {
        this.states = states;
    }


    public void returnToPreviousMenu() {
        states.pop();
    }
}
