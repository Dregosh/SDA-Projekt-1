package view;

import controller.MainController;

import java.util.Scanner;

public class MainMenuState extends MenuState {
    private MainController mainController;

    public MainMenuState(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void show() {
        System.out.println("1. To Product Menu...");
        System.out.println("0. Exit program");
        int input = in.nextInt();
        switch (input) {
            case 1:
                mainController.toProductMenu();
                break;
            case 0:
            default:
                mainController.exitProgram();
                break;
        }
    }


}
