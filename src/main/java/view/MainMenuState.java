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
        int input = (int) requestNumberInput(BLANK_INPUT_NOT_ALLOWED);
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
                exitProgramOption();
                break;
            default:
                System.out.println("Invalid choice");
        }
    }

    private void toProductMenuOption() {
        mainController.toProductMenu();
    }

    private void toCustomerMenuOption() {
        mainController.toCustomerMenu();
    }

    private void toOrderMenuOption() {
        //mainController.toOrderMenu();
        reportNotImplented();
    }

    private void exitProgramOption() {
        mainController.exitProgram();
    }
}