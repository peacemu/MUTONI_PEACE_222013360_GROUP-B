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

public class events extends JFrame {
    JFrame frame;
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblTopBar;
    private JLabel lblFormTitle;
    private JLabel lblName;
    private JLabel lblid;
    private JTextField txtName;
    private JTextField idName;
    private JLabel lblLocation;
    private JTextField txtLocation;
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
            pst = con.prepareStatement("SELECT * FROM events");
            ResultSet Rs = pst.executeQuery();
            java.sql.ResultSetMetaData RSMD = Rs.getMetaData();
            cc = RSMD.getColumnCount();
            DefaultTableModel dataInTable = (DefaultTableModel) table1.getModel();
            dataInTable.setRowCount(0);
            while (Rs.next()) {
                Vector v2 = new Vector();

                v2.add(Rs.getString("id"));
                v2.add(Rs.getString("Event_name"));
                v2.add(Rs.getString("Event_location"));
                v2.add(Rs.getString("Event_date_"));
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

    public events() {
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

        lblTopBar = new JLabel("Here you can manage Events");
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

        lblFormTitle = new JLabel("Event Information");
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

        lblName = new JLabel("Event Name:");
        lblName.setBounds(10, 70, 100, 20);
        formPanel.add(lblName);

        txtName = new JTextField();
        txtName.setBounds(120, 70, 150, 20);
        formPanel.add(txtName);
        txtName.setColumns(10);

        lblLocation = new JLabel("Location:");
        lblLocation.setBounds(10, 100, 100, 20);
        formPanel.add(lblLocation);

        txtLocation = new JTextField();
        txtLocation.setBounds(120, 100, 150, 20);
        formPanel.add(txtLocation);
        txtLocation.setColumns(10);

        // Add Button
        btnAdd = new JButton("Add");
        btnAdd.setBounds(10, 130, 80, 30);
        btnAdd.setBackground(Color.BLUE);
        formPanel.add(btnAdd);
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Retrieve event details from the form
                String name = txtName.getText();
                String location = txtLocation.getText();
                try {
                    // Insert the new event
                    PreparedStatement insertStmt = con.prepareStatement("INSERT INTO events (Event_name, Event_location) VALUES (?, ?)");
                    insertStmt.setString(1, name);
                    insertStmt.setString(2, location);
                    insertStmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Event added");
                    showDataTable1();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error while adding Event", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Delete Button
        btnDelete = new JButton("Delete");
        btnDelete.setBounds(100, 130, 80, 30);
        btnDelete.setBackground(Color.RED);
        formPanel.add(btnDelete);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Retrieve event ID
                String id = idName.getText();
                try {
                    // Delete event
                    PreparedStatement deleteStmt = con.prepareStatement("DELETE FROM events WHERE id = ?");
                    deleteStmt.setString(1, id);
                    int affectedRows = deleteStmt.executeUpdate();
                    if (affectedRows > 0) {
                        JOptionPane.showMessageDialog(null, "Event deleted");
                        showDataTable1();
                    } else {
                        JOptionPane.showMessageDialog(null, "Event with ID " + id + " not found");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error while deleting Event", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Update Button
        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(190, 130, 80, 30);
        btnUpdate.setBackground(Color.GREEN);
        formPanel.add(btnUpdate);
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Retrieve event details from the form
                String name = txtName.getText();
                String location = txtLocation.getText();
                String id = idName.getText();
                try {
                    // Update the event
                    PreparedStatement updateStmt = con.prepareStatement("UPDATE events SET Event_name=?, Event_location=? WHERE id=?");
                    updateStmt.setString(1, name);
                    updateStmt.setString(2, location);
                    updateStmt.setString(3, id);
                    updateStmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Event updated");
                    showDataTable1();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error while updating Event", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Table Section
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 360, 900, 300);
        contentPane.add(scrollPane);

        Object[][] data = {
                {null, null, null, null}
        };
        String[] columnHeaders = {"id", "Event Name", "Location", "Date"};
        DefaultTableModel model = new DefaultTableModel(data, columnHeaders) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Disable editing for the Date column
                return column != 3;
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
                        txtName.setText((String) table1.getValueAt(selectedRow, 1));
                        txtLocation.setText((String) table1.getValueAt(selectedRow, 2));
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    events frame = new events();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
