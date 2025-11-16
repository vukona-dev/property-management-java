/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author Vukona-Maritz
 */

import javax.swing.*;
import java.awt.*;
import controller.PropertyPD;

public class MainMenu extends JFrame {
    private PropertyPD propertyPD;

    public MainMenu() {
        setTitle("HOME MENU - BY MS MNGOMA");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        propertyPD = new PropertyPD();

        // --- Menu Bar ---
        JMenuBar menuBar = new JMenuBar();

        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem searchItem = new JMenuItem("Search Property Sold by Ref");
        JMenuItem updateItem = new JMenuItem("Update Agent Name");
        JMenuItem countItem = new JMenuItem("Count No of Townhouses Sold");
        JMenuItem deleteItem = new JMenuItem("Cancel Property from Sold List");
        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenu.add(searchItem);
        fileMenu.add(updateItem);
        fileMenu.add(countItem);
        fileMenu.add(deleteItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Properties Menu
        JMenu propertiesMenu = new JMenu("Properties");
        JMenuItem addItem = new JMenuItem("Add Selling Property");
        JMenuItem viewItem = new JMenuItem("View Properties Sold");

        propertiesMenu.add(addItem);
        propertiesMenu.add(viewItem);

        // Add menus to bar
        menuBar.add(fileMenu);
        menuBar.add(propertiesMenu);
        setJMenuBar(menuBar);

        // --- Actions ---
        addItem.addActionListener(e -> new AddSellingProperty().setVisible(true));
        searchItem.addActionListener(e -> new SearchProperty().setVisible(true));
        updateItem.addActionListener(e -> new UpdateProperty().setVisible(true));
        deleteItem.addActionListener(e -> new DeleteProperty().setVisible(true));
        viewItem.addActionListener(e -> new ViewAllProperties().setVisible(true));

        countItem.addActionListener(e -> {
            int count = propertyPD.countTownHouse();
            JOptionPane.showMessageDialog(this,
                    "Number of TownHouse properties sold: " + count,
                    "TownHouse Count",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        exitItem.addActionListener(e -> System.exit(0));

        // --- Main Panel (optional welcome message) ---
        JPanel panel = new JPanel(new BorderLayout());
        JLabel lblWelcome = new JLabel("Welcome to Property Management System", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(lblWelcome, BorderLayout.CENTER);
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainMenu().setVisible(true);
        });
    }
}