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

public class feedback extends JFrame {
    JFrame frame;
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblTopBar;
    private JLabel lblFormTitle;
    private JLabel lblid;
    private JTextField idName;
    private JLabel lblPosition;
    private JComboBox<String> comboBoxPosition;
    private JLabel lblDate;
    private JTextField txtDate;
    private JLabel lblStatus;
    private JTextField txtStatus;
    private JLabel lblWinnerStatus;
    private JTextField txtWinnerStatus;
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
            pst = con.prepareStatement("SELECT * FROM elections");
            ResultSet Rs = pst.executeQuery();
            java.sql.ResultSetMetaData RSMD = Rs.getMetaData();
            cc = RSMD.getColumnCount();
            DefaultTableModel dataInTable = (DefaultTableModel) table1.getModel();
            dataInTable.setRowCount(0);
            while (Rs.next()) {
                Vector v2 = new Vector();

                v2.add(Rs.getString("election_id"));
                v2.add(Rs.getString("election_position"));
                v2.add(Rs.getString("election_date"));
                int doneStatus = Rs.getInt("done_status");
                if (doneStatus == 0) {
                    v2.add("Pending");
                } else {
                    v2.add("Done");
                }
                v2.add(Rs.getString("winner_status"));
                dataInTable.addRow(v2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/votting_system", "root", "");
            System.out.print("Connected successfully");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public feedback() {
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
        topBarPanel.setBackground(Color.RED);
        topBarPanel.setBounds(0, 0, 900, 50);
        contentPane.add(topBarPanel);
        topBarPanel.setLayout(null);

        lblTopBar = new JLabel("Here you can manage Elections");
        lblTopBar.setForeground(Color.WHITE);
        lblTopBar.setFont(new Font("Verdana", Font.BOLD, 16));
        lblTopBar.setBounds(10, 10, 400, 30);
        topBarPanel.add(lblTopBar);

        // Back Button
        btnBack = new JButton("Back");
        btnBack.setBounds(750, 10, 100, 30);
        btnBack.setBackground(Color.BLUE);
        topBarPanel.add(btnBack);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                admin_page admin_ = new admin_page();
                admin_.frame.setVisible(true);
                frame.dispose(); // Close the login window
            }
        });

        // Form Section
        JPanel formPanel = new JPanel();
        formPanel.setBounds(0, 50, 450, 300);
        contentPane.add(formPanel);
        formPanel.setLayout(null);

        lblFormTitle = new JLabel("Election Information");
        lblFormTitle.setFont(new Font("Verdana", Font.BOLD, 14));
        lblFormTitle.setBounds(10, 10, 200, 20);
        formPanel.add(lblFormTitle);

        lblid = new JLabel("Id:");
        lblid.setBounds(10, 40, 100, 20);
        formPanel.add(lblid);

        idName = new JTextField();
        idName.setBounds(120, 40, 150, 20);
        formPanel.add(idName);
        idName.setColumns(10);

        lblPosition = new JLabel("Position:");
        lblPosition.setBounds(10, 70, 100, 20);
        formPanel.add(lblPosition);

        comboBoxPosition = new JComboBox<>();
        comboBoxPosition.setBounds(120, 70, 150, 20);
        comboBoxPosition.addItem("President");
        comboBoxPosition.addItem("Vice President");
        comboBoxPosition.addItem("CEO");
        comboBoxPosition.addItem("Vice CEO");
        comboBoxPosition.addItem("Manager");
        comboBoxPosition.addItem("Chairman");
        comboBoxPosition.addItem("Vice Chairman");
        formPanel.add(comboBoxPosition);

        lblDate = new JLabel("Date:");
        lblDate.setBounds(10, 100, 100, 20);
        formPanel.add(lblDate);

        txtDate = new JTextField();
        txtDate.setBounds(120, 100, 150, 20);
        formPanel.add(txtDate);
        txtDate.setColumns(10);

        lblStatus = new JLabel("Status:");
        lblStatus.setBounds(10, 130, 100, 20);
        formPanel.add(lblStatus);

        txtStatus = new JTextField();
        txtStatus.setBounds(120, 130, 150, 20);
        formPanel.add(txtStatus);
        txtStatus.setColumns(10);

        lblWinnerStatus = new JLabel("Winner Status:");
        lblWinnerStatus.setBounds(10, 160, 100, 20);
        formPanel.add(lblWinnerStatus);

        txtWinnerStatus = new JTextField();
        txtWinnerStatus.setBounds(120, 160, 150, 20);
        formPanel.add(txtWinnerStatus);
        txtWinnerStatus.setColumns(10);

        // Add Button
        btnAdd = new JButton("Add");
        btnAdd.setBounds(10, 190, 80, 30);
        btnAdd.setBackground(Color.BLUE);
        formPanel.add(btnAdd);
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Retrieve election details from the form
                String position = (String) comboBoxPosition.getSelectedItem();
                String date = txtDate.getText();
                int doneStatus = 0; // Set done_status to 0 by default
                String winnerStatus = "Pending"; // Set winner_status to "Pending" by default
                try {
                    // Insert the new election
                    PreparedStatement insertStmt = con.prepareStatement("INSERT INTO elections (election_position, election_date, done_status, winner_status) VALUES (?, ?, ?, ?)");
                    insertStmt.setString(1, position);
                    insertStmt.setString(2, date);
                    insertStmt.setInt(3, doneStatus);
                    insertStmt.setString(4, winnerStatus);
                    insertStmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Election added");
                    showDataTable1();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error while adding Election", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Delete Button
        btnDelete = new JButton("Delete");
        btnDelete.setBounds(100, 190, 80, 30);
        btnDelete.setBackground(Color.RED);
        formPanel.add(btnDelete);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Retrieve election ID
                String id = idName.getText();
                try {
                    // Delete election
                    PreparedStatement deleteStmt = con.prepareStatement("DELETE FROM elections WHERE election_id = ?");
                    deleteStmt.setString(1, id);
                    int affectedRows = deleteStmt.executeUpdate();
                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(null, "Election deleted");
                        showDataTable1();
                    } else {
                        JOptionPane.showMessageDialog(null, "Election with ID " + id + " not found");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error while deleting Election", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Update Button
        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(190, 190, 80, 30);
        btnUpdate.setBackground(Color.GREEN);
        formPanel.add(btnUpdate);
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Retrieve election details from the form
                String position = (String) comboBoxPosition.getSelectedItem();
                String date = txtDate.getText();
                String id = idName.getText();
                int doneStatus = 1; // Set done_status to 0 by default
                String winnerStatus = "End"; // Set winner_status to "Pending" by default

                try {
                    // Update the election
                    PreparedStatement updateStmt = con.prepareStatement("UPDATE elections SET election_position=?, election_date=?, done_status=? ,winner_status=? WHERE election_id=?");
                    updateStmt.setString(1, position);
                    updateStmt.setString(2, date);
                    updateStmt.setInt(3, doneStatus);
                    updateStmt.setString(4, winnerStatus);
                    updateStmt.setString(5, id);
                    updateStmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Election updated");
                    showDataTable1();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error while updating Election", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Table Section
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 360, 900, 300);
        contentPane.add(scrollPane);

        Object[][] data = {
                {null, null, null, null, null}
        };
        String[] columnHeaders = {"Election ID", "Position", "Date", "Status", "Winner Status"};
        DefaultTableModel model = new DefaultTableModel(data, columnHeaders) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Disable editing for certain columns
                return column != 0 && column != 3 && column != 4;
            }
        };
        table1 = new JTable(model);
        scrollPane.setViewportView(table1);
        showDataTable1();

        // Add ListSelectionListener to the table
        table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table1.getSelectedRow();
                    if (selectedRow != -1) {
                        // Populate form fields with data from the selected row
                        idName.setText((String) table1.getValueAt(selectedRow, 0));
                        comboBoxPosition.setSelectedItem((String) table1.getValueAt(selectedRow, 1));
                        txtDate.setText((String) table1.getValueAt(selectedRow, 2));
                        txtStatus.setText((String) table1.getValueAt(selectedRow, 3));
                        txtWinnerStatus.setText((String) table1.getValueAt(selectedRow, 4));
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	feedback frame = new feedback();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
