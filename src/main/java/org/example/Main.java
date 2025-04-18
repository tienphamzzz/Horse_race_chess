package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame("Horse race chess");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        // Hiển thị menu chính
        MainMenuPanel mainMenu = new MainMenuPanel(window);
        window.add(mainMenu);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}