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
import exceptions.DataStorageException;

import javax.swing.*;
import java.awt.*;

public class UpdateProperty extends JFrame {
    private JTextField txtRefNo, txtArea, txtAgent, txtPrice, txtCustomer;
    private JComboBox<String> cmbType, cmbAgency;
    private JRadioButton rdoYes, rdoNo;
    private ButtonGroup depositGroup;
    private JButton btnSearch, btnUpdate, btnClear, btnClose;
    private PropertyPD propertyPD;

    public UpdateProperty() {
        setTitle("UPDATE PROPERTY");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        propertyPD = new PropertyPD();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0: Ref No
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Prop Ref No:"), gbc);
        gbc.gridx = 1;
        txtRefNo = new JTextField();
        add(txtRefNo, gbc);

        btnSearch = new JButton("Search");
        gbc.gridx = 2;
        add(btnSearch, gbc);

        // Row 1: Property Type
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Property Type:"), gbc);
        gbc.gridx = 1;
        cmbType = new JComboBox<>(new String[]{"House", "TownHouse", "Apartment"});
        add(cmbType, gbc);

        // Row 2: Agency
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Agency:"), gbc);
        gbc.gridx = 1;
        cmbAgency = new JComboBox<>(new String[]{"LeapFrog", "Realnet", "RE/MAX"});
        add(cmbAgency, gbc);

        // Row 3: Area
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Area:"), gbc);
        gbc.gridx = 1;
        txtArea = new JTextField();
        add(txtArea, gbc);

        // Row 4: Selling Price
        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Selling Price:"), gbc);
        gbc.gridx = 1;
        txtPrice = new JTextField();
        add(txtPrice, gbc);

        // Row 5: Deposit Required
        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("Deposit Required:"), gbc);
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
        add(new JLabel("Agent Name:"), gbc);
        gbc.gridx = 1;
        txtAgent = new JTextField();
        add(txtAgent, gbc);

        // Row 7: Customer
        gbc.gridx = 0; gbc.gridy = 7;
        add(new JLabel("Customer:"), gbc);
        gbc.gridx = 1;
        txtCustomer = new JTextField();
        add(txtCustomer, gbc);

        // Row 8: Buttons
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 3;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnUpdate = new JButton("Update");
        btnClear = new JButton("Clear");
        btnClose = new JButton("Close");
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnClose);
        add(buttonPanel, gbc);

        // Button actions
        btnSearch.addActionListener(e -> {
            try {
                Property p = propertyPD.searchProperty(txtRefNo.getText().trim());
                loadProperty(p);
            } catch (NotFoundException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Not Found", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                String refNo = txtRefNo.getText().trim();
                String type = cmbType.getSelectedItem().toString();
                String agency = cmbAgency.getSelectedItem().toString();
                String area = txtArea.getText().trim();
                double price = Double.parseDouble(txtPrice.getText().trim());
                boolean depositReq = rdoYes.isSelected();
                String agent = txtAgent.getText().trim();
                String customer = txtCustomer.getText().trim();

                Property updated = new Property(refNo, type, area, agent, agency, price, depositReq, customer);
                propertyPD.updateProperty(updated);

                JOptionPane.showMessageDialog(this, "Property updated successfully!");
                clearFields();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid price. Enter a numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NotFoundException | DataStorageException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnClear.addActionListener(e -> clearFields());
        btnClose.addActionListener(e -> dispose());
    }

    private void loadProperty(Property p) {
        txtRefNo.setText(p.getPRefNo());
        cmbType.setSelectedItem(p.getPropType());
        cmbAgency.setSelectedItem(p.getAgency());
        txtArea.setText(p.getArea());
        txtPrice.setText(String.valueOf(p.getSellingPrice()));
        if (p.getIsDepositReq()) {
            rdoYes.setSelected(true);
        } else {
            rdoNo.setSelected(true);
        }
        txtAgent.setText(p.getAgentName());
        txtCustomer.setText(p.getCustomer());
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
