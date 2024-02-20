package milk_processing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class product_page extends JFrame {
    JFrame frame;
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblTopBar;
    private JLabel lblFormTitle;
    private JLabel lblProductName;
    private JLabel lblAmount;
    private JLabel lblBoxes;
    private JTextField txtProductName;
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
            pst = con.prepareStatement("SELECT * FROM products");
            ResultSet Rs = pst.executeQuery();
            java.sql.ResultSetMetaData RSMD = Rs.getMetaData();
            cc = RSMD.getColumnCount();
            DefaultTableModel dataInTable = (DefaultTableModel) table1.getModel();
            dataInTable.setRowCount(0);
            while (Rs.next()) {
                Vector v2 = new Vector();
                for (int i = 1; i <= cc; i++) {
                    v2.add(Rs.getString("id"));
                    v2.add(Rs.getString("product_name"));
                    v2.add(Rs.getString("amount"));
                    v2.add(Rs.getString("boxes"));
                    v2.add(Rs.getString("date_added"));
                }
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

    private void clearForm() {
        txtProductName.setText("");
        txtAmount.setText("");
        txtBoxes.setText("");
    }

    private void addProduct() {
        try {
            String productName = txtProductName.getText();
            String amount = txtAmount.getText();
            String boxes = txtBoxes.getText();

            PreparedStatement insertStmt = con.prepareStatement("INSERT INTO products (product_name, amount, boxes, date_added) VALUES (?, ?, ?, NOW())");
            insertStmt.setString(1, productName);
            insertStmt.setString(2, amount);
            insertStmt.setString(3, boxes);
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
                    PreparedStatement deleteStmt = con.prepareStatement("DELETE FROM products WHERE id = ?");
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
                    String productName = txtProductName.getText();
                    String amount = txtAmount.getText();
                    String boxes = txtBoxes.getText();

                    PreparedStatement updateStmt = con.prepareStatement("UPDATE products SET product_name = ?, amount = ?, boxes = ? WHERE id = ?");
                    updateStmt.setString(1, productName);
                    updateStmt.setString(2, amount);
                    updateStmt.setString(3, boxes);
                    updateStmt.setString(4, productId);
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

    public product_page() {
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
        topBarPanel.setBackground(Color.CYAN); // Change top header to CYAN
        topBarPanel.setBounds(0, 0, 900, 50);
        contentPane.add(topBarPanel);
        topBarPanel.setLayout(null);

        lblTopBar = new JLabel("Here you can manage Products");
        lblTopBar.setForeground(Color.WHITE);
        lblTopBar.setFont(new Font("Verdana", Font.BOLD, 16));
        lblTopBar.setBounds(10, 10, 400, 30);
        topBarPanel.add(lblTopBar);

        // Back Button
        btnBack = new JButton("Back");
        btnBack.setBounds(800, 10, 80, 30);
        btnBack.setBackground(Color.CYAN); // Change back button to CYAN
        topBarPanel.add(btnBack);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                milk_processing_Admin sales_page_ = new milk_processing_Admin();
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

        txtProductName = new JTextField();
        txtProductName.setBounds(170, 40, 200, 20);
        formPanel.add(txtProductName);
        txtProductName.setColumns(10);

        lblAmount = new JLabel("Amount:");
        lblAmount.setBounds(10, 70, 150, 20);
        formPanel.add(lblAmount);

        txtAmount = new JTextField();
        txtAmount.setBounds(170, 70, 200, 20);
        formPanel.add(txtAmount);
        txtAmount.setColumns(10);

        lblBoxes = new JLabel("Boxes:");
        lblBoxes.setBounds(10, 100, 150, 20);
        formPanel.add(lblBoxes);

        txtBoxes = new JTextField();
        txtBoxes.setBounds(170, 100, 200, 20);
        formPanel.add(txtBoxes);
        txtBoxes.setColumns(10);

        // Add Button
        btnAdd = new JButton("Add");
        btnAdd.setBounds(10, 130, 80, 30);
        btnAdd.setBackground(Color.CYAN); // Change button to CYAN
        formPanel.add(btnAdd);
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        // Delete Button
        btnDelete = new JButton("Delete");
        btnDelete.setBounds(100, 130, 80, 30);
        btnDelete.setBackground(Color.CYAN); // Change button to CYAN
        formPanel.add(btnDelete);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
            }
        });

        // Update Button
        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(190, 130, 80, 30);
        btnUpdate.setBackground(Color.CYAN); // Change button to CYAN
        formPanel.add(btnUpdate);
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateProduct();
            }
        });

        // Table Section
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 360, 900, 300);
        contentPane.add(scrollPane);

        Object[][] data = {{null, null, null, null, null}};
        String[] columnHeaders = {"ID", "Product Name", "Amount", "Boxes", "Date Added"};
        DefaultTableModel model = new DefaultTableModel(data, columnHeaders);
        table1 = new JTable(model) {
            // Override getTableHeader to customize the table header
            @Override
            protected JTableHeader createDefaultTableHeader() {
                return new JTableHeader(columnModel) {
                    @Override
                    public Dimension getPreferredSize() {
                        Dimension d = super.getPreferredSize();
                        d.height = 32; // Set header height
                        return d;
                    }
                };
            }
        };
        table1.getTableHeader().setBackground(Color.CYAN); // Change table header background to CYAN
        table1.setOpaque(true);
        table1.setBackground(Color.BLUE); // Change table background to BLUE
        table1.setForeground(Color.WHITE);
        table1.setFont(new Font("Verdana", Font.BOLD, 12));
        scrollPane.setViewportView(table1);
        showDataTable1();

        // Add ListSelectionListener to the table
        table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {
                    populateFormFromTable(selectedRow);
                }
            }
        });
    }

    private void populateFormFromTable(int selectedRow) {
        txtProductName.setText((String) table1.getValueAt(selectedRow, 1));
        txtAmount.setText((String) table1.getValueAt(selectedRow, 2));
        txtBoxes.setText((String) table1.getValueAt(selectedRow, 3));
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    product_page frame = new product_page();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
