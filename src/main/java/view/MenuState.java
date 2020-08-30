package view;

import java.util.Scanner;

public abstract class MenuState {
    protected Scanner in = new Scanner(System.in);

    abstract public void show();

    public void reportNotImplentedInfo() {
        System.out.println("Option not implemented yet.");
    }
}