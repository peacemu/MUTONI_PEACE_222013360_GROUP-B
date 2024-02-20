package milk_processing;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class distr_sales extends JFrame {
    JFrame frame;
    private JPanel contentPane;
    private JLabel lblTopBar;
    private JLabel lblFormTitle;
    private JLabel lblProductName;
    private JLabel lblProductId;
    private JLabel lblAmount;
    private JComboBox<String> comboBoxProductName;
    private JTextField txtProductId;
    private JTextField txtAmount;
    private JButton btnBack;
    private JButton btnDelete;
    private JButton btnUpdate;
    private JButton btnAdd;

    private JTable table1;

    Connection con;
    PreparedStatement pst;

    private void showDataTable1() {
        try {
            pst = con.prepareStatement("SELECT * FROM distr_stock");
            ResultSet Rs = pst.executeQuery();
            DefaultTableModel dataInTable = (DefaultTableModel) table1.getModel();
            dataInTable.setRowCount(0);
            while (Rs.next()) {
                dataInTable.addRow(new Object[]{
                        Rs.getString("id"),
                        Rs.getString("product_name"),
                        Rs.getString("product_id"),
                        Rs.getString("amount")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/inyange_enterprise", "root", "");
            System.out.print("Connected successfully");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearForm() {
        comboBoxProductName.setSelectedIndex(0);
        txtProductId.setText("");
        txtAmount.setText("");
    }

    private void addProduct() {
        try {
            String productName = (String) comboBoxProductName.getSelectedItem();
            String productId = txtProductId.getText();
            String amount = txtAmount.getText();

            PreparedStatement insertStmt = con.prepareStatement("INSERT INTO distr_stock (product_name, product_id, amount) VALUES (?, ?, ?)");
            insertStmt.setString(1, productName);
            insertStmt.setString(2, productId);
            insertStmt.setString(3, amount);
            insertStmt.executeUpdate();

            JOptionPane.showMessageDialog(frame, "Product added successfully");
            showDataTable1();
            clearForm();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error while adding product", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteProduct() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this product?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    String productId = (String) table1.getValueAt(selectedRow, 0);
                    PreparedStatement deleteStmt = con.prepareStatement("DELETE FROM distr_stock WHERE id = ?");
                    deleteStmt.setString(1, productId);
                    deleteStmt.executeUpdate();

                    JOptionPane.showMessageDialog(frame, "Product deleted successfully");
                    showDataTable1();
                    clearForm();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error while deleting product", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a product to delete.");
        }
    }

    private void updateProduct() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to update this product?", "Confirm Update", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    String productId = (String) table1.getValueAt(selectedRow, 0);
                    String productName = (String) comboBoxProductName.getSelectedItem();
                    String amount = txtAmount.getText();

                    PreparedStatement updateStmt = con.prepareStatement("UPDATE distr_stock SET product_name = ?, amount = ? WHERE id = ?");
                    updateStmt.setString(1, productName);
                    updateStmt.setString(2, amount);
                    updateStmt.setString(3, productId);
                    updateStmt.executeUpdate();

                    JOptionPane.showMessageDialog(frame, "Product updated successfully");
                    showDataTable1();
                    clearForm();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error while updating product", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a product to update.");
        }
    }

    public distr_sales() {
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
        topBarPanel.setBackground(Color.BLUE);
        topBarPanel.setBounds(0, 0, 900, 50);
        contentPane.add(topBarPanel);
        topBarPanel.setLayout(null);

        lblTopBar = new JLabel("Here you can manage Sales");
        lblTopBar.setForeground(Color.WHITE);
        lblTopBar.setFont(new Font("Verdana", Font.BOLD, 16));
        lblTopBar.setBounds(10, 10, 400, 30);
        topBarPanel.add(lblTopBar);

        // Back Button
        btnBack = new JButton("Back");
        btnBack.setBounds(800, 10, 80, 30);
        btnBack.setBackground(Color.GRAY);
        topBarPanel.add(btnBack);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                user_inyange sales_page_ = new user_inyange();
                sales_page_.frame.setVisible(true);
                frame.dispose();
            }
        });

        // Form Section
        JPanel formPanel = new JPanel();
        formPanel.setBounds(0, 50, 450, 300);
        contentPane.add(formPanel);
        formPanel.setLayout(null);

        lblFormTitle = new JLabel("Product Information");
        lblFormTitle.setFont(new Font("Verdana", Font.BOLD, 14));
        lblFormTitle.setBounds(10, 10, 200, 20);
        formPanel.add(lblFormTitle);

        lblProductName = new JLabel("Product Name:");
        lblProductName.setBounds(10, 40, 150, 20);
        formPanel.add(lblProductName);

        comboBoxProductName = new JComboBox<>();
        comboBoxProductName.setBounds(170, 40, 200, 20);
        comboBoxProductName.addItem("Milk");
        comboBoxProductName.addItem("Juice");
        comboBoxProductName.addItem("Water");
        formPanel.add(comboBoxProductName);

        lblProductId = new JLabel("Product ID:");
        lblProductId.setBounds(10, 70, 150, 20);
        formPanel.add(lblProductId);

        txtProductId = new JTextField();
        txtProductId.setBounds(170, 70, 200, 20);
        formPanel.add(txtProductId);
        txtProductId.setColumns(10);

        lblAmount = new JLabel("Amount:");
        lblAmount.setBounds(10, 100, 150, 20);
        formPanel.add(lblAmount);

        txtAmount = new JTextField();
        txtAmount.setBounds(170, 100, 200, 20);
        formPanel.add(txtAmount);
        txtAmount.setColumns(10);

        // Add Button
        btnAdd = new JButton("Add");
        btnAdd.setBounds(10, 130, 80, 30);
        btnAdd.setBackground(Color.BLUE);
        formPanel.add(btnAdd);
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        // Delete Button
        btnDelete = new JButton("Delete");
        btnDelete.setBounds(100, 130, 80, 30);
        btnDelete.setBackground(Color.RED);
        formPanel.add(btnDelete);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
            }
        });

        // Update Button
        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(190, 130, 80, 30);
        btnUpdate.setBackground(Color.GREEN);
        formPanel.add(btnUpdate);
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateProduct();
            }
        });

        // Table Section
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 260, 900, 400);
        contentPane.add(scrollPane);

        Object[][] data = {{null, null, null, null}};
        String[] columnHeaders = {"ID", "Product Name", "Product ID", "Amount"};
        DefaultTableModel model = new DefaultTableModel(data, columnHeaders);
        table1 = new JTable(model);
        scrollPane.setViewportView(table1);
        showDataTable1();

        // Populate form when clicking on a row in the table
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table1.getSelectedRow();
                if (row != -1) {
                    comboBoxProductName.setSelectedItem(table1.getValueAt(row, 1));
                    txtProductId.setText((String) table1.getValueAt(row, 2));
                    txtAmount.setText((String) table1.getValueAt(row, 3));
                }
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    distr_sales frame = new distr_sales();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

