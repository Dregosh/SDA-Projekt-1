package view;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class MenuState {
    protected final Scanner in = new Scanner(System.in);

    abstract public void show();

    public double requestNumberInput() {
        while (true) {
            try {
                double input = in.nextDouble();
                in.nextLine();
                return input;
            } catch (InputMismatchException e) {
                System.out.println("Input must be a number");
                in.nextLine();
                System.out.print("> ");
            }
        }
    }

    public void reportNotImplented() {
        System.out.println("Option not implemented yet.");
    }

    public void reportOperationSuccessful() {
        System.out.println("Operation successful");
    }

    public void reportOperationCancelled() {
        System.out.println("Operation cancelled");
    }
}
