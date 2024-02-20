package milk_processing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class sales_page extends JFrame {
    JFrame frame;
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblTopBar;
    private JLabel lblFormTitle;
    private JLabel lblDistributerNames;
    private JLabel lblDistributerId;
    private JLabel lblProduct;
    private JLabel lblAmount;
    private JLabel lblBoxes;
    private JTextField txtDistributerNames;
    private JTextField txtDistributerId;
    private JComboBox<String> comboBoxProduct;
    private JTextField txtAmount;
    private JTextField txtBoxes;
    private JButton btnBack;
    private JButton btnDelete;
    private JButton btnUpdate;
    private JButton btnAdd;

    private JTable table1;

    Connection con;
    PreparedStatement pst;

    private void showDataTable1() {
        int cc;
        try {
            pst = con.prepareStatement("SELECT * FROM sales");
            ResultSet Rs = pst.executeQuery();
            java.sql.ResultSetMetaData RSMD = Rs.getMetaData();
            cc = RSMD.getColumnCount();
            DefaultTableModel dataInTable = (DefaultTableModel) table1.getModel();
            dataInTable.setRowCount(0);
            while (Rs.next()) {
                Vector v2 = new Vector();

                v2.add(Rs.getString("id"));
                v2.add(Rs.getString("distributer_names"));
                v2.add(Rs.getString("distributer_id"));
                v2.add(Rs.getString("date_"));
                v2.add(Rs.getString("product_"));
                v2.add(Rs.getString("amount"));
                v2.add(Rs.getString("boxes"));
                dataInTable.addRow(v2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/milk_database", "root", "");
            System.out.print("Connected successfully");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public sales_page() {
        connection();
        frame = this;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 700);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Top Bar Section
        JPanel topBarPanel = new JPanel();
        topBarPanel.setBackground(Color.CYAN); // Changed to CYAN color
        topBarPanel.setBounds(0, 0, 900, 50);
        contentPane.add(topBarPanel);
        topBarPanel.setLayout(null);

        lblTopBar = new JLabel("Here you can manage Sales");
        lblTopBar.setForeground(Color.WHITE);
        lblTopBar.setFont(new Font("Verdana", Font.BOLD, 16));
        lblTopBar.setBounds(10, 10, 400, 30);
        topBarPanel.add(lblTopBar);

        // Form Section
        JPanel formPanel = new JPanel();
        formPanel.setBounds(0, 50, 450, 300);
        contentPane.add(formPanel);
        formPanel.setLayout(null);

        lblFormTitle = new JLabel("Sales Information");
        lblFormTitle.setFont(new Font("Verdana", Font.BOLD, 14));
        lblFormTitle.setBounds(10, 10, 200, 20);
        formPanel.add(lblFormTitle);

        lblDistributerNames = new JLabel("Distributer Names:");
        lblDistributerNames.setBounds(10, 40, 150, 20);
        formPanel.add(lblDistributerNames);

        txtDistributerNames = new JTextField();
        txtDistributerNames.setBounds(170, 40, 200, 20);
        formPanel.add(txtDistributerNames);
        txtDistributerNames.setColumns(10);

        lblDistributerId = new JLabel("Distributer ID:");
        lblDistributerId.setBounds(10, 70, 150, 20);
        formPanel.add(lblDistributerId);

        txtDistributerId = new JTextField();
        txtDistributerId.setBounds(170, 70, 200, 20);
        formPanel.add(txtDistributerId);
        txtDistributerId.setColumns(10);

        lblProduct = new JLabel("Product:");
        lblProduct.setBounds(10, 100, 150, 20);
        formPanel.add(lblProduct);

        comboBoxProduct = new JComboBox<>();
        comboBoxProduct.setBounds(170, 100, 200, 20);
        comboBoxProduct.addItem("Milk");
        comboBoxProduct.addItem("Water");
        comboBoxProduct.addItem("Juice");
        formPanel.add(comboBoxProduct);

        lblAmount = new JLabel("Amount:");
        lblAmount.setBounds(10, 130, 150, 20);
        formPanel.add(lblAmount);

        txtAmount = new JTextField();
        txtAmount.setBounds(170, 130, 200, 20);
        formPanel.add(txtAmount);
        txtAmount.setColumns(10);

        lblBoxes = new JLabel("Boxes:");
        lblBoxes.setBounds(10, 160, 150, 20);
        formPanel.add(lblBoxes);

        txtBoxes = new JTextField();
        txtBoxes.setBounds(170, 160, 200, 20);
        formPanel.add(txtBoxes);
        txtBoxes.setColumns(10);

        // Add Button
        btnAdd = new JButton("Add");
        btnAdd.setBounds(10, 190, 80, 30);
        btnAdd.setBackground(Color.BLUE); // Changed to blue
        formPanel.add(btnAdd);
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String distributerNames = txtDistributerNames.getText();
                    String distributerId = txtDistributerId.getText();
                    String product = (String) comboBoxProduct.getSelectedItem();
                    String amount = txtAmount.getText();
                    String boxes = txtBoxes.getText();
                    PreparedStatement insertStmt = con.prepareStatement("INSERT INTO sales (distributer_names, distributer_id, date_, product_, amount, boxes) VALUES (?, ?, NOW(), ?, ?, ?)");
                    insertStmt.setString(1, distributerNames);
                    insertStmt.setString(2, distributerId);
                    insertStmt.setString(3, product);
                    insertStmt.setString(4, amount);
                    insertStmt.setString(5, boxes);
                    insertStmt.executeUpdate();
                    JOptionPane.showMessageDialog(frame, "Sale added successfully");
                    showDataTable1();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error while adding sale", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Delete Button
        btnDelete = new JButton("Delete");
        btnDelete.setBounds(100, 190, 80, 30);
        btnDelete.setBackground(Color.RED); // Changed to red
        formPanel.add(btnDelete);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {
                    int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this sale?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            String saleId = (String) table1.getValueAt(selectedRow, 0);
                            PreparedStatement deleteStmt = con.prepareStatement("DELETE FROM sales WHERE id = ?");
                            deleteStmt.setString(1, saleId);
                            deleteStmt.executeUpdate();
                            JOptionPane.showMessageDialog(frame, "Sale deleted successfully");
                            showDataTable1();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(frame, "Error while deleting sale", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a sale to delete.");
                }
            }
        });

        // Update Button
        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(190, 190, 80, 30);
        btnUpdate.setBackground(Color.GREEN); // Changed to green
        formPanel.add(btnUpdate);
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {
                    int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to update this sale?", "Confirm Update", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            String saleId = (String) table1.getValueAt(selectedRow, 0);
                            String distributerNames = txtDistributerNames.getText();
                            String distributerId = txtDistributerId.getText();
                            String product = (String) comboBoxProduct.getSelectedItem();
                            String amount = txtAmount.getText();
                            String boxes = txtBoxes.getText();
                            PreparedStatement updateStmt = con.prepareStatement("UPDATE sales SET distributer_names=?, distributer_id=?, date_=NOW(), product_=?, amount=?, boxes=? WHERE id = ?");
                            updateStmt.setString(1, distributerNames);
                            updateStmt.setString(2, distributerId);
                            updateStmt.setString(3, product);
                            updateStmt.setString(4, amount);
                            updateStmt.setString(5, boxes);
                            updateStmt.setString(6, saleId);
                            updateStmt.executeUpdate();
                            JOptionPane.showMessageDialog(frame, "Sale updated successfully");
                            showDataTable1();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(frame, "Error while updating sale", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a sale to update.");
                }
            }
        });

        // Table Section
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 360, 900, 300);
        contentPane.add(scrollPane);

        Object[][] data = {{null, null, null, null, null}};
        String[] columnHeaders = {"id", "Distributer Names", "Distributer ID", "Date", "Product", "Amount", "Boxes"};
        DefaultTableModel model = new DefaultTableModel(data, columnHeaders);
        table1 = new JTable(model);
        table1.setBackground(Color.BLUE); // Changed table background to blue
        scrollPane.setViewportView(table1);
        showDataTable1();

        // Add ListSelectionListener to the table
        table1.getSelectionModel().addListSelectionListener((ListSelectionListener) new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table1.getSelectedRow();
                    if (selectedRow != -1) {
                        txtDistributerId.setText((String) table1.getValueAt(selectedRow, 2));
                        txtDistributerNames.setText((String) table1.getValueAt(selectedRow, 1));
                        comboBoxProduct.setSelectedItem((String) table1.getValueAt(selectedRow, 4));
                        txtAmount.setText((String) table1.getValueAt(selectedRow, 5));
                        txtBoxes.setText((String) table1.getValueAt(selectedRow, 6));
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    sales_page frame = new sales_page();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
