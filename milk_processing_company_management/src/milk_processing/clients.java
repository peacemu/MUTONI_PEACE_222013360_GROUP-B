package milk_processing;

import javax.swing.*;
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

public class clients extends JFrame {
    JFrame frame;
    private JPanel contentPane;
    private JTable table;

    Connection con;
    PreparedStatement pst;

    public clients() {
        frame = this;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 700);
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        // Add the top header with the title "Users" and the back button
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.BLUE);
        contentPane.add(headerPanel, BorderLayout.NORTH);

        JLabel lblHeader = new JLabel("Clients");
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setFont(new Font("Verdana", Font.BOLD, 24));
        headerPanel.add(lblHeader);

        JButton btnBack = new JButton("Back");
        btnBack.setBackground(Color.GRAY);
        headerPanel.add(btnBack);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                user_inyange ppp = new user_inyange();
                ppp.frame.setVisible(true);
                frame.dispose();
            }
        });

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        table = new JTable();
        scrollPane.setViewportView(table);

        // Connect to the database and fetch user data
        connection();
        showUserData();
    }

    void connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/inyange_enterprise", "root", "");
            System.out.print("Connected successfully");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showUserData() {
        int cc;
        try {
            pst = con.prepareStatement("SELECT * FROM clients");
            ResultSet Rs = pst.executeQuery();
            java.sql.ResultSetMetaData RSMD = Rs.getMetaData();
            cc = RSMD.getColumnCount();
            DefaultTableModel dataInTable = new DefaultTableModel();
            Vector columnNames = new Vector();
            Vector dataRows = new Vector();

            // Get column names
            for (int i = 1; i <= cc; i++) {
                columnNames.addElement(RSMD.getColumnName(i));
            }

            // Get row data
            while (Rs.next()) {
                Vector row = new Vector(cc);
                for (int i = 1; i <= cc; i++) {
                    row.addElement(Rs.getObject(i));
                }
                dataRows.addElement(row);
            }

            // Add column names and data rows to the table model
            dataInTable.setDataVector(dataRows, columnNames);
            table.setModel(dataInTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	clients frame = new clients();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
