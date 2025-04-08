package src;

import src.GUI;

public class Main {
    private static Thread gui_ = new Thread(new GUI("Inventory Management System"));
    public static void main(String[] args) {
        gui_.start();
    }
}
