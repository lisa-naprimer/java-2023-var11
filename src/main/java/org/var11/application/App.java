package org.var11.application;
import javax.swing.*;
public class App {
    public static void main(String[] args) {
        Database database = new Database();
        AppController controller = new AppController(database);
        AppUI appUI = new AppUI(controller);

        SwingUtilities.invokeLater(appUI::createUI);
    }
}