package view;

import java.util.Scanner;

public abstract class MenuState {
    protected Scanner in = new Scanner(System.in);

    abstract public void show();
}
