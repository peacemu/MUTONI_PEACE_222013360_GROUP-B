package milk_processing;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;

public class oders_page_distr extends JFrame {
    JFrame frame;
    private JPanel contentPane;
    private JLabel lblTopBar;
    private JLabel lblFormTitle;
    private JLabel lblDistributorName;
    private JLabel lblDistributorId;
    private JLabel lblProductName;
    private JLabel lblAmount;
    private JTextField txtDistributorName;
    private JTextField txtDistributorId;
    private JComboBox<String> comboBoxProductName;
    private JTextField txtAmount;
    private JButton btnBack;
    private JButton btnAdd;
    private JButton btnViewAll;

    private JTable table1;

    Connection con;
    PreparedStatement pst;

    private void showDataTable1() {
        try {
            pst = con.prepareStatement("SELECT * FROM orders");
            ResultSet Rs = pst.executeQuery();
            DefaultTableModel dataInTable = (DefaultTableModel) table1.getModel();
            dataInTable.setRowCount(0);
            while (Rs.next()) {
                dataInTable.addRow(new Object[]{
                        Rs.getString("id"),
                        Rs.getString("distributer_names"),
                        Rs.getString("distributer_id"),
                        Rs.getString("product_"),
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
        txtDistributorName.setText("");
        txtDistributorId.setText("");
        txtAmount.setText("");
    }

    private void addOrder() {
        try {
            String distributorName = txtDistributorName.getText();
            String distributorId = txtDistributorId.getText();
            String productName = (String) comboBoxProductName.getSelectedItem();
            String amount = txtAmount.getText();

            // Check if distributor ID exists in the users table
            boolean distributorExists = checkDistributorExists(distributorId);
            if (!distributorExists) {
                JOptionPane.showMessageDialog(frame, "Distributor does not exist. Order not submitted.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            PreparedStatement insertStmt = con.prepareStatement("INSERT INTO orders (distributer_names, distributer_id, product_, amount) VALUES (?, ?, ?, ?)");
            insertStmt.setString(1, distributorName);
            insertStmt.setString(2, distributorId);
            insertStmt.setString(3, productName);
            insertStmt.setString(4, amount);
            insertStmt.executeUpdate();

            JOptionPane.showMessageDialog(frame, "Order added successfully");
            showDataTable1();
            clearForm();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error while adding order", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean checkDistributorExists(String distributorId) throws SQLException {
        PreparedStatement checkStmt = con.prepareStatement("SELECT * FROM users WHERE id = ?");
        checkStmt.setString(1, distributorId);
        ResultSet resultSet = checkStmt.executeQuery();
        return resultSet.next();
    }

    public oders_page_distr() {
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

        lblTopBar = new JLabel("Here you can manage Orders");
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

        lblFormTitle = new JLabel("Order Information");
        lblFormTitle.setFont(new Font("Verdana", Font.BOLD, 14));
        lblFormTitle.setBounds(10, 10, 200, 20);
        formPanel.add(lblFormTitle);

        lblDistributorName = new JLabel("Distributor Name:");
        lblDistributorName.setBounds(10, 40, 150, 20);
        formPanel.add(lblDistributorName);

        txtDistributorName = new JTextField();
        txtDistributorName.setBounds(170, 40, 200, 20);
        formPanel.add(txtDistributorName);
        txtDistributorName.setColumns(10);

        lblDistributorId = new JLabel("Distributor ID:");
        lblDistributorId.setBounds(10, 70, 150, 20);
        formPanel.add(lblDistributorId);

        txtDistributorId = new JTextField();
        txtDistributorId.setBounds(170, 70, 200, 20);
        formPanel.add(txtDistributorId);
        txtDistributorId.setColumns(10);

        lblProductName = new JLabel("Product:");
        lblProductName.setBounds(10, 100, 150, 20);
        formPanel.add(lblProductName);

        comboBoxProductName = new JComboBox<>();
        comboBoxProductName.setBounds(170, 100, 200, 20);
        comboBoxProductName.addItem("Milk");
        comboBoxProductName.addItem("Juice");
        comboBoxProductName.addItem("Water");
        formPanel.add(comboBoxProductName);

        lblAmount = new JLabel("Amount:");
        lblAmount.setBounds(10, 130, 150, 20);
        formPanel.add(lblAmount);

        txtAmount = new JTextField();
        txtAmount.setBounds(170, 130, 200, 20);
        formPanel.add(txtAmount);
        txtAmount.setColumns(10);

        // Add Button
        btnAdd = new JButton("Add Order");
        btnAdd.setBounds(10, 170, 120, 30);
        btnAdd.setBackground(Color.BLUE);
        formPanel.add(btnAdd);
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addOrder();
            }
        });

        // View All Orders Button
        btnViewAll = new JButton("View All Orders");
        btnViewAll.setBounds(140, 170, 140, 30);
        btnViewAll.setBackground(Color.GREEN);
        formPanel.add(btnViewAll);
        btnViewAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showDataTable1();
            }
        });

        // Table Section
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 260, 900, 400);
        contentPane.add(scrollPane);

        Object[][] data = {{null, null, null, null, null}};
        String[] columnHeaders = {"ID", "Distributor Name", "Distributor ID", "Product", "Amount"};
        DefaultTableModel model = new DefaultTableModel(data, columnHeaders);
        table1 = new JTable(model);
        scrollPane.setViewportView(table1);
        showDataTable1();

        // Populate form when clicking on a row in the table
        table1.addMouseListener((MouseListener) new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table1.getSelectedRow();
                if (row != -1) {
                    txtDistributorName.setText((String) table1.getValueAt(row, 1));
                    txtDistributorId.setText((String) table1.getValueAt(row, 2));
                    // Set the product name in the combo box
                    String productName = (String) table1.getValueAt(row, 3);
                    comboBoxProductName.setSelectedItem(productName);
                    txtAmount.setText((String) table1.getValueAt(row, 4));
                }
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    oders_page_distr frame = new oders_page_distr();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
