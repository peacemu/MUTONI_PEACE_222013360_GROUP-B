package milk_processing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class choose_candidate extends JFrame {
    JFrame frame;
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblTopBar;
    private JComboBox<String> comboBoxUsers;
    private JPasswordField txtPassword;
    private JButton btnChoose;

    private JTable tableCandidates;

    Connection con;
    PreparedStatement pst;
 
    private void showCandidatesData() {
        try {
            pst = con.prepareStatement("SELECT * FROM candidates");
            ResultSet Rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) tableCandidates.getModel();
            model.setRowCount(0);
            while (Rs.next()) {
                String candidateId = Rs.getString("candidate_id");
                String name = Rs.getString("name");
                String position = Rs.getString("position");
                String gender = Rs.getString("gender");
                model.addRow(new Object[]{candidateId, name, position, gender});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error while fetching candidate data", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/votting_system", "root", "");
            System.out.print("Connected successfully");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public choose_candidate() {
        connection();
        frame = this;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Top Bar Section
        JPanel topBarPanel = new JPanel();
        topBarPanel.setBackground(Color.RED);
        topBarPanel.setBounds(0, 0, 600, 50);
        contentPane.add(topBarPanel);
        topBarPanel.setLayout(null);

        lblTopBar = new JLabel("Choose Candidate");
        lblTopBar.setForeground(Color.WHITE);
        lblTopBar.setFont(new Font("Verdana", Font.BOLD, 16));
        lblTopBar.setBounds(10, 10, 200, 30);
        topBarPanel.add(lblTopBar);

        // Form Section
        JPanel formPanel = new JPanel();
        formPanel.setBounds(0, 50, 600, 100);
        contentPane.add(formPanel);
        formPanel.setLayout(null);

        JLabel lblSelectUser = new JLabel("Select User:");
        lblSelectUser.setFont(new Font("Verdana", Font.PLAIN, 12));
        lblSelectUser.setBounds(10, 10, 200, 20);
        formPanel.add(lblSelectUser);

        comboBoxUsers = new JComboBox<>();
        comboBoxUsers.setBounds(10, 40, 150, 20);
        formPanel.add(comboBoxUsers);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(180, 10, 100, 20);
        formPanel.add(lblPassword);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(180, 40, 150, 20);
        formPanel.add(txtPassword);

        // Choose Button
        btnChoose = new JButton("Choose");
        btnChoose.setBounds(250, 40, 100, 20); // Adjusted position to accommodate the back button
        btnChoose.setBackground(Color.GREEN);
        formPanel.add(btnChoose);
        btnChoose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userId = comboBoxUsers.getSelectedItem().toString();
                String password = String.valueOf(txtPassword.getPassword());

                if (!userId.isEmpty() && !password.isEmpty()) {
                    chooseCandidate(userId, password);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a user and enter the password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Back Button
        JButton btnBack = new JButton("Back");
        btnBack.setBounds(470, 10, 100, 20); // Position the back button after the "Choose candidate" label
        btnBack.setBackground(Color.RED);
        formPanel.add(btnBack);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add action to go back to the previous screen or perform any other desired action
                userHome jobSeekersWindow = new userHome();
                jobSeekersWindow.frame.setVisible(true);
                frame.dispose(); // Close the login window

            }
        });

        // Table Section
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 160, 600, 200);
        contentPane.add(scrollPane);

        tableCandidates = new JTable();
        scrollPane.setViewportView(tableCandidates);
        DefaultTableModel model = new DefaultTableModel();
        tableCandidates.setModel(model);
        model.addColumn("Candidate ID");
        model.addColumn("Name");
        model.addColumn("Position");
        model.addColumn("Gender");

        showCandidatesData();
        populateUserComboBox();
    }

    private void populateUserComboBox() {
        try {
            pst = con.prepareStatement("SELECT user_id FROM users");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String userId = rs.getString("user_id");
                comboBoxUsers.addItem(userId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error while fetching user data", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void chooseCandidate(String userId, String password) {
        try {
            pst = con.prepareStatement("SELECT * FROM users WHERE user_id = ? AND password = ?");
            pst.setString(1, userId);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                // Check if the user already voted
                pst = con.prepareStatement("SELECT * FROM choose_candidate WHERE user_id = ?");
                pst.setString(1, userId);
                ResultSet rsVote = pst.executeQuery();
                if (rsVote.next()) {
                    JOptionPane.showMessageDialog(null, "You can only vote once.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Insert the vote
                    int selectedRow = tableCandidates.getSelectedRow();
                    if (selectedRow != -1) {
                        String candidateId = tableCandidates.getValueAt(selectedRow, 0).toString();
                        pst = con.prepareStatement("INSERT INTO choose_candidate (user_id, candidate_id) VALUES (?, ?)");
                        pst.setString(1, userId);
                        pst.setString(2, candidateId);
                        int rowsAffected = pst.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, "Your vote has been recorded successfully.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to record your vote.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a candidate.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid user ID or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error while choosing candidate", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    choose_candidate frame = new choose_candidate();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
