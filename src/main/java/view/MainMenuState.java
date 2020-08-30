package view;

import controller.MainController;

public class MainMenuState extends MenuState {
    private MainController mainController;

    public MainMenuState(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void show() {
        System.out.println("\nMAIN MENU");
        System.out.println("(1) Product Menu...");
        System.out.println("(2) Customer Menu...");
        System.out.println("(3) Order Menu...");
        System.out.println("(0) Exit program");
        System.out.print("> ");
        int input = in.nextInt();
        switch (input) {
            case 1:
                toProductMenuOption();
                break;
            case 2:
                toCustomerMenuOption();
                break;
            case 3:
                toOrderMenuOption();
                break;
            case 0:
            default:
                exitProgramOption();
                break;
        }
    }

    private void toProductMenuOption() {
        mainController.toProductMenu();
    }

    private void toCustomerMenuOption() {
        //mainController.toCustomerMenu();
        reportNotImplentedInfo();
    }

    private void toOrderMenuOption() {
        //mainController.toOrderMenu();
        reportNotImplentedInfo();
    }

    private void exitProgramOption() {
        mainController.exitProgram();
    }
}