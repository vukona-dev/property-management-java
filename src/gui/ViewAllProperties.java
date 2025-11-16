
/**
 *
 * @author Vukona-Maritz
 */

package gui;

import controller.PropertyPD;
import model.Property;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ViewAllProperties extends JFrame {
    private JTable table;
    private JButton btnClose;
    private PropertyPD propertyPD;

    public ViewAllProperties() {
        setTitle("VIEW PROPERTIES SOLD");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        propertyPD = new PropertyPD();

        // Table setup
        String[] columns = {
            "Ref No", "Type", "Area", "Price", "Deposit", "Loan",
            "Monthly", "Total Payment", "Interest", "Agency", "Agent", "Customer"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        loadData(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Bottom panel
        JPanel bottomPanel = new JPanel(new FlowLayout());
        btnClose = new JButton("Close");
        bottomPanel.add(btnClose);
        add(bottomPanel, BorderLayout.SOUTH);

        btnClose.addActionListener(e -> dispose());
    }

    private void loadData(DefaultTableModel model) {
        ArrayList<Property> list = propertyPD.getAllProperties();
        for (Property p : list) {
            Object[] row = {
                p.getPRefNo(),
                p.getPropType(),
                p.getArea(),
                String.format("R%,.2f", p.getSellingPrice()),
                String.format("R%,.2f", p.calcDeposit()),
                String.format("R%,.2f", p.getLoan()),
                String.format("R%,.2f", p.calcMonthlyInstalment()),
                String.format("R%,.2f", p.totPayment()),
                String.format("R%,.2f", p.totInterest()),
                p.getAgency(),
                p.getAgentName(),
                p.getCustomer()
            };
            model.addRow(row);
        }
    }
}
