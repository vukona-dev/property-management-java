/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Vukona-Maritz
 */
package gui;

import controller.PropertyPD;
import model.Property;
import exceptions.NotFoundException;

import javax.swing.*;
import java.awt.*;

public class SearchProperty extends JFrame {
    private JTextField txtRefNo;
    private JTextArea txtResults;
    private JButton btnSearch, btnClear, btnClose;
    private PropertyPD propertyPD;

    public SearchProperty() {
        setTitle("SEARCH PROPERTY SOLD BY REF");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        propertyPD = new PropertyPD();

        // Top panel for input
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Enter Ref No:"));
        txtRefNo = new JTextField(15);
        topPanel.add(txtRefNo);
        btnSearch = new JButton("Search");
        topPanel.add(btnSearch);
        add(topPanel, BorderLayout.NORTH);

        // Center panel for results
        txtResults = new JTextArea();
        txtResults.setEditable(false);
        txtResults.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(txtResults), BorderLayout.CENTER);

        // Bottom panel for buttons
        JPanel bottomPanel = new JPanel(new FlowLayout());
        btnClear = new JButton("Clear");
        btnClose = new JButton("Close");
        bottomPanel.add(btnClear);
        bottomPanel.add(btnClose);
        add(bottomPanel, BorderLayout.SOUTH);

        // Button actions
        btnSearch.addActionListener(e -> {
            String refNo = txtRefNo.getText().trim();
            try {
                Property p = propertyPD.searchProperty(refNo);
                txtResults.setText(formatProperty(p));
            } catch (NotFoundException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Not Found", JOptionPane.ERROR_MESSAGE);
                txtResults.setText("");
            }
        });

        btnClear.addActionListener(e -> {
            txtRefNo.setText("");
            txtResults.setText("");
        });

        btnClose.addActionListener(e -> dispose());
    }

    private String formatProperty(Property p) {
        return String.format(
            "Customer: %s\nRef No: %s\nType: %s\nArea: %s\nPrice: R%,.2f\nDeposit: R%,.2f\nLoan: R%,.2f\nMonthly: R%,.2f\nTotal Payment: R%,.2f\nInterest: R%,.2f\nAgency: %s\nAgent: %s",
            p.getCustomer(), p.getPRefNo(), p.getPropType(), p.getArea(), p.getSellingPrice(),
            p.calcDeposit(), p.getLoan(), p.calcMonthlyInstalment(), p.totPayment(), p.totInterest(),
            p.getAgency(), p.getAgentName()
        );
    }
}
