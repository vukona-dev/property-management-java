/**
 *
 * @author Vukona-Maritz
 */

package gui;

import controller.PropertyPD;
import exceptions.NotFoundException;
import exceptions.DataStorageException;

import javax.swing.*;
import java.awt.*;

public class DeleteProperty extends JFrame {
    private JTextField txtRefNo;
    private JButton btnDelete, btnClear, btnClose;
    private PropertyPD propertyPD;

    public DeleteProperty() {
        setTitle("CANCEL PROPERTY FROM SOLD LIST");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        propertyPD = new PropertyPD();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0: Ref No
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Enter Ref No:"), gbc);
        gbc.gridx = 1;
        txtRefNo = new JTextField();
        add(txtRefNo, gbc);

        // Row 1: Buttons
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");
        btnClose = new JButton("Close");
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnClose);
        add(buttonPanel, gbc);

        // Button actions
        btnDelete.addActionListener(e -> {
            String refNo = txtRefNo.getText().trim();
            if (refNo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a reference number.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete property " + refNo + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    propertyPD.deleteProperty(refNo);
                    JOptionPane.showMessageDialog(this, "Property " + refNo + " deleted successfully!");
                    clearFields();
                } catch (NotFoundException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Not Found", JOptionPane.ERROR_MESSAGE);
                } catch (DataStorageException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Storage Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnClear.addActionListener(e -> clearFields());
        btnClose.addActionListener(e -> dispose());
    }

    private void clearFields() {
        txtRefNo.setText("");
    }
}

