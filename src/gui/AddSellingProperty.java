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

import controller.PropertyPD;
import model.Property;
import exceptions.DuplicateException;
import exceptions.DataStorageException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AddSellingProperty extends JFrame {
    private JTextField txtRefNo, txtArea, txtAgent, txtPrice, txtCustomer;
    private JComboBox<String> cmbType, cmbAgency;
    private JRadioButton rdoYes, rdoNo;
    private ButtonGroup depositGroup;
    private JButton btnAdd, btnClear, btnClose;
    private PropertyPD propertyPD;

    public AddSellingProperty() {
        setTitle("ADD SELLING PROPERTY FORM");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        propertyPD = new PropertyPD();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels and Inputs
        String[] labels = {
            "Prop Ref No:", "Property Type:", "Agency:", "Area:",
            "Selling Price:", "Is Deposit Required?", "Agent Name:", "Customer:"
        };

        // Row 0: Ref No
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel(labels[0]), gbc);
        gbc.gridx = 1;
        txtRefNo = new JTextField();
        add(txtRefNo, gbc);

        // Row 1: Property Type
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel(labels[1]), gbc);
        gbc.gridx = 1;
        cmbType = new JComboBox<>(new String[]{"House", "TownHouse", "Apartment"});
        add(cmbType, gbc);

        // Row 2: Agency
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel(labels[2]), gbc);
        gbc.gridx = 1;
        cmbAgency = new JComboBox<>(new String[]{"LeapFrog", "Realnet", "RE/MAX"});
        add(cmbAgency, gbc);

        // Row 3: Area
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel(labels[3]), gbc);
        gbc.gridx = 1;
        txtArea = new JTextField();
        add(txtArea, gbc);

        // Row 4: Selling Price
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel(labels[4]), gbc);
        gbc.gridx = 1;
        txtPrice = new JTextField();
        add(txtPrice, gbc);

        // Row 5: Deposit Required
        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel(labels[5]), gbc);
        gbc.gridx = 1;
        JPanel depositPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rdoYes = new JRadioButton("Yes");
        rdoNo = new JRadioButton("No");
        depositGroup = new ButtonGroup();
        depositGroup.add(rdoYes);
        depositGroup.add(rdoNo);
        depositPanel.add(rdoYes);
        depositPanel.add(rdoNo);
        add(depositPanel, gbc);

        // Row 6: Agent Name
        gbc.gridx = 0; gbc.gridy = 6;
        add(new JLabel(labels[6]), gbc);
        gbc.gridx = 1;
        txtAgent = new JTextField();
        add(txtAgent, gbc);

        // Row 7: Customer
        gbc.gridx = 0; gbc.gridy = 7;
        add(new JLabel(labels[7]), gbc);
        gbc.gridx = 1;
        txtCustomer = new JTextField();
        add(txtCustomer, gbc);

        // Row 8: Buttons
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnAdd = new JButton("Add Property");
        btnClear = new JButton("Clear");
        btnClose = new JButton("Close");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnClose);
        add(buttonPanel, gbc);

        // Button Actions
        btnAdd.addActionListener(e -> {
            try {
                String refNo = txtRefNo.getText().trim();
                String type = cmbType.getSelectedItem().toString();
                String agency = cmbAgency.getSelectedItem().toString();
                String area = txtArea.getText().trim();
                double price = Double.parseDouble(txtPrice.getText().trim());
                boolean depositReq = rdoYes.isSelected();
                String agent = txtAgent.getText().trim();
                String customer = txtCustomer.getText().trim();

                Property p = new Property(refNo, type, area, agent, agency, price, depositReq, customer);
                propertyPD.addProperty(p);

                JOptionPane.showMessageDialog(this, refNo + ": Property added successfully!");
                clearFields();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid price. Enter a numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (DuplicateException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Duplicate Error", JOptionPane.ERROR_MESSAGE);
            } catch (DataStorageException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Storage Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnClear.addActionListener(e -> clearFields());
        btnClose.addActionListener(e -> dispose());
    }

    private void clearFields() {
        txtRefNo.setText("");
        cmbType.setSelectedIndex(0);
        cmbAgency.setSelectedIndex(0);
        txtArea.setText("");
        txtPrice.setText("");
        depositGroup.clearSelection();
        txtAgent.setText("");
        txtCustomer.setText("");
    }
}
