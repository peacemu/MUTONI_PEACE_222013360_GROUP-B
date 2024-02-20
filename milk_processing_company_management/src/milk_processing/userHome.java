package milk_processing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class userHome {

    protected JFrame frame;
    private JButton btnProfile;
    private JButton btnViewProposal;
    private JButton btnLogout;
    private JButton btnVote;
    private JButton btnViewCandidates;
    private JButton btnViewUpcomingElections;
    private JButton btnChangePassword;
    private JButton btnViewPastElections;
    private JButton btnFeedback;

    private JPanel tablePanel;
    private JPanel formPanel;
    private JLabel lblWelcome;

    private JTable table1;
    private JTable table2;
    private JTable table12;
    private JTable table13;
    private JTable table30;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    userHome window = new userHome();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void showDataTable12() {
        int cc;
        try {
            pst = con.prepareStatement("SELECT * FROM events");
            ResultSet Rs = pst.executeQuery();
            java.sql.ResultSetMetaData RSMD = Rs.getMetaData();
            cc = RSMD.getColumnCount();
            DefaultTableModel dataInTable = (DefaultTableModel) table13.getModel();
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
    private void showDataTable30() {
        int cc;
        try {
            pst = con.prepareStatement("SELECT * FROM candidates");
            ResultSet Rs = pst.executeQuery();
            java.sql.ResultSetMetaData RSMD = Rs.getMetaData();
            cc = RSMD.getColumnCount();
            DefaultTableModel dataInTable = (DefaultTableModel) table30.getModel();
            dataInTable.setRowCount(0);
            while (Rs.next()) {
                Vector v2 = new Vector();

                v2.add(Rs.getString("candidate_id"));
                v2.add(Rs.getString("name"));
                v2.add(Rs.getString("position"));
                v2.add(Rs.getString("election_id"));
                v2.add(Rs.getString("gender"));
                
                dataInTable.addRow(v2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showDataTable2() {
        int cc;
        try {
            pst = con.prepareStatement("SELECT * FROM elections");
            ResultSet Rs = pst.executeQuery();
            java.sql.ResultSetMetaData RSMD = Rs.getMetaData();
            cc = RSMD.getColumnCount();
            DefaultTableModel dataInTable = (DefaultTableModel) table12.getModel();
            dataInTable.setRowCount(0);
            while (Rs.next()) {
                Vector v2 = new Vector();

                v2.add(Rs.getString("election_id"));
                v2.add(Rs.getString("election_position"));
                v2.add(Rs.getString("done_status"));
                v2.add(Rs.getString("winner_status"));
                v2.add(Rs.getString("election_date"));
                
                dataInTable.addRow(v2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public userHome() {
        initialize();
        connection();
    }
    Connection con;
    PreparedStatement pst;

    void connection() {
   	 try {
   		Class.forName("com.mysql.cj.jdbc.Driver");
   		 con =  DriverManager.getConnection("jdbc:mysql://localhost/votting_system","root", "");
            System.out.print("Connected succesfully");
   	} catch (ClassNotFoundException e) {
   		// TODO Auto-generated catch block
   		e.printStackTrace();
   	} catch (SQLException e) {
   		// TODO Auto-generated catch block
   		e.printStackTrace();
   	}
   	 
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1050, 733);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JPanel topBarPanel = new JPanel();
        topBarPanel.setBackground(Color.RED);
        topBarPanel.setBounds(0, 0, 1050, 50);
        frame.getContentPane().add(topBarPanel);
        topBarPanel.setLayout(null);

        lblWelcome = new JLabel("You are Welcome to, ONLINE VOTTING SYSTEM");
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Verdana", Font.BOLD, 16));
        lblWelcome.setBounds(0, 0, 1050, 50);
        topBarPanel.add(lblWelcome);

        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setBackground(new Color(64, 128, 128));
        sidebarPanel.setBounds(0, 50, 200, 700);
        frame.getContentPane().add(sidebarPanel);
        sidebarPanel.setLayout(null);

        JLabel lblHomepage = new JLabel("HOMEPAGE");
        lblHomepage.setForeground(Color.WHITE);
        lblHomepage.setHorizontalAlignment(SwingConstants.CENTER);
        lblHomepage.setFont(new Font("Verdana", Font.BOLD, 19));
        lblHomepage.setBounds(0, 0, 200, 28);
        sidebarPanel.add(lblHomepage);

        btnProfile = new JButton("PROFILE");
        btnProfile.setFont(new Font("Verdana", Font.BOLD, 14));
        btnProfile.setBounds(0, 50, 200, 40);
        sidebarPanel.add(btnProfile);

        btnViewProposal = new JButton("View Annoucements");
        btnViewProposal.setFont(new Font("Verdana", Font.BOLD, 14));
        btnViewProposal.setBounds(0, 100, 200, 40);
        sidebarPanel.add(btnViewProposal);

        btnVote = new JButton("Vote");
        btnVote.setFont(new Font("Verdana", Font.BOLD, 14));
        btnVote.setBounds(0, 150, 200, 40);
        sidebarPanel.add(btnVote);

        btnViewCandidates = new JButton("View Candidates");
        btnViewCandidates.setFont(new Font("Verdana", Font.BOLD, 14));
        btnViewCandidates.setBounds(0, 200, 200, 40);
        sidebarPanel.add(btnViewCandidates);

        btnViewUpcomingElections = new JButton("View Upcoming Elections");
        btnViewUpcomingElections.setFont(new Font("Verdana", Font.BOLD, 14));
        btnViewUpcomingElections.setBounds(0, 250, 200, 40);
        sidebarPanel.add(btnViewUpcomingElections);

        btnChangePassword = new JButton("Change Password");
        btnChangePassword.setFont(new Font("Verdana", Font.BOLD, 14));
        btnChangePassword.setBounds(0, 300, 200, 40);
        sidebarPanel.add(btnChangePassword);

        btnViewPastElections = new JButton("View Past Elections");
        btnViewPastElections.setFont(new Font("Verdana", Font.BOLD, 14));
        btnViewPastElections.setBounds(0, 350, 200, 40);
        sidebarPanel.add(btnViewPastElections);

        btnFeedback = new JButton("Feedback/Contact Admin");
        btnFeedback.setFont(new Font("Verdana", Font.BOLD, 14));
        btnFeedback.setBounds(0, 400, 200, 40);
        sidebarPanel.add(btnFeedback);

        tablePanel = new JPanel();
        tablePanel.setBounds(200, 50, 850, 700);
        frame.getContentPane().add(tablePanel);
        tablePanel.setLayout(null);

        formPanel = new JPanel();
        formPanel.setBounds(200, 50, 850, 700);
        frame.getContentPane().add(formPanel);
        formPanel.setLayout(null);

        btnViewProposal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                formPanel.setVisible(false);
                // Show table panel
                tablePanel.setVisible(true);
                // Show the appropriate table (table1, table2, etc.) based on your requirement
                viewAnnouncement();
                    }
                });

                btnVote.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        formPanel.setVisible(false);
                        // Show table panel
                        tablePanel.setVisible(true);
                        // Show the appropriate table (table1, table2, etc.) based on your requirement
                        choose_candidate jobSeekersWindow = new choose_candidate();
                        jobSeekersWindow.frame.setVisible(true);
                        frame.dispose(); // Close the login window
                    }
                });
                btnViewCandidates.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Hide form panel
                        formPanel.setVisible(false);
                        // Show table panel
                        tablePanel.setVisible(true);
                        // Show the appropriate table (table1, table2, etc.) based on your requirement
                        showVoteTable30();
                    }
                });
                btnViewUpcomingElections.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Hide form panel
                        formPanel.setVisible(false);
                        // Show table panel
                        tablePanel.setVisible(true);
                        // Show the appropriate table (table1, table2, etc.) based on your requirement
                        showVoteTable3();
                    }
                });
                btnChangePassword.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Hide form panel
                        formPanel.setVisible(false);
                        // Show table panel
                        tablePanel.setVisible(true);
                        // Show the appropriate table (table1, table2, etc.) based on your requirement
                        showChangePasswordForm();
                    }
                });
                btnViewPastElections.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Hide form panel
                        formPanel.setVisible(false);
                        // Show table panel
                        tablePanel.setVisible(true);
                        // Show the appropriate table (table1, table2, etc.) based on your requirement
                        showVoteTable4();
                    }
                });
                btnFeedback.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Hide form panel
                        formPanel.setVisible(false);
                        // Show table panel
                        tablePanel.setVisible(true);
                        // Show the appropriate table (table1, table2, etc.) based on your requirement
                        feedBackForm2();
                    }
                });

                // Initially show the welcome message with the default username
                showWelcomeMessage("Username");
            }

            private void showWelcomeMessage(String username) {
                lblWelcome.setText("You are Welcome to, ONLINE VOTTING SYSTEM");
            }

            // Method to show the vote table
            private void showVoteTable() {
                // Sample data for the table
                Object[][] data = {
                    {"Candidate 1", "Party A"},
                    {"Candidate 2", "Party B"},
                    {"Candidate 3", "Party C"}
                };
                // Column headers
                String[] columnHeaders = {"Candidate", "Party"};

                // Create the table model with the sample data and column headers
                DefaultTableModel model = new DefaultTableModel(data, columnHeaders);

                // Create the table
                JTable table = new JTable(model);

                // Create a scroll pane and add the table to it
                JScrollPane scrollPane = new JScrollPane(table);

                // Set the bounds of the scroll pane
                scrollPane.setBounds(0, 20, 850, 680);

                // Add a title label above the table
                JLabel titleLabel = new JLabel("Candidates for Voting");
                titleLabel.setFont(new Font("Verdana", Font.BOLD, 16));
                titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
                titleLabel.setBounds(0, 0, 850, 20);

                // Clear any existing components in the table panel
                tablePanel.removeAll();

                // Add the title label and the scroll pane to the table panel
                tablePanel.add(titleLabel);
                tablePanel.add(scrollPane);

                // Refresh the table panel
                tablePanel.revalidate();
                tablePanel.repaint();
            }
            private void viewAnnouncement() {
                try {
                    // Retrieve the latest announcement from the database
                    PreparedStatement stmt = con.prepareStatement("SELECT annoucement_message FROM annoucement ORDER BY id DESC LIMIT 1");
                    ResultSet rs = stmt.executeQuery();
                    
                    // Check if there is a result
                    if (rs.next()) {
                        // Get the announcement message
                        String announcementMessage = rs.getString("annoucement_message");
                        
                        // Create a label to display the announcement message
                        JLabel lblAnnouncement = new JLabel("<html><body><p style='font-size: 18px; font-weight: bold;'>" + announcementMessage + "</p></body></html>");
                        lblAnnouncement.setBounds(50, 50, 750, 100);
                        
                        // Add the label to the form panel
                        formPanel.add(lblAnnouncement);
                    } else {
                        // If no announcement found, display a message
                        JOptionPane.showMessageDialog(frame, "No announcement available", "Announcement", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error while retrieving announcement", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
                // Show the form panel and hide the table panel
                formPanel.setVisible(true);
                tablePanel.setVisible(false);
                
                // Refresh the form panel
                formPanel.revalidate();
                formPanel.repaint();
            }

            private void showVoteTable2() {
                // Sample data for the table
                Object[][] data = {
                    {"Candidate 1", "Party A"},
                    {"Candidate 2", "Party B"},
                    {"Candidate 3", "Party C"}
                };
                // Column headers
                String[] columnHeaders = {"Candidate", "Party"};

                // Create the table model with the sample data and column headers
                DefaultTableModel model = new DefaultTableModel(data, columnHeaders);

                // Create the table
                JTable table = new JTable(model);

                // Create a scroll pane and add the table to it
                JScrollPane scrollPane = new JScrollPane(table);

                // Set the bounds of the scroll pane
                scrollPane.setBounds(0, 20, 850, 680);

                // Add a title label above the table
                JLabel titleLabel = new JLabel("Candidates for Voting");
                titleLabel.setFont(new Font("Verdana", Font.BOLD, 16));
                titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
                titleLabel.setBounds(0, 0, 850, 20);

                // Clear any existing components in the table panel
                tablePanel.removeAll();

                // Add the title label and the scroll pane to the table panel
                tablePanel.add(titleLabel);
                tablePanel.add(scrollPane);

                // Refresh the table panel
                tablePanel.revalidate();
                tablePanel.repaint();
            }
            private void showVoteTable30() {
                // Sample data for the table
                Object[][] data = {
                        {null, null, null,null,null},
                };
                // Column headers
                String[] columnHeaders = {"Candidate ID", "Position", "Election_id", "Gender"};

                // Create the table model with the sample data and column headers
                DefaultTableModel model = new DefaultTableModel(data, columnHeaders);

                // Initialize the member variable instead of re-declaring it
                table30 = new JTable(model);

                // Create a scroll pane and add the table to it
                JScrollPane scrollPane = new JScrollPane(table30);

                // Set the bounds of the scroll pane
                scrollPane.setBounds(0, 80, 850, 560);
                showDataTable30();

                // Add a mouse listener to the table
                


                // Add a title label above the table
                JLabel titleLabel = new JLabel("Candidates Available");
                titleLabel.setFont(new Font("Verdana", Font.BOLD, 16));
                titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
                titleLabel.setBounds(0, 0, 850, 40);

                // Create buttons for different actions
     


                // Clear any existing components in the table panel
                //  tablePanel.removeAll();

                // Add components to the table panel
                tablePanel.add(titleLabel);
                tablePanel.add(scrollPane);

                // Refresh the table panel
                tablePanel.revalidate();
                tablePanel.repaint();

            }
            private void showVoteTable3() {
                // Sample data for the table
                Object[][] data = {
                        {null, null, null,null,null},
                };
                // Column headers
                String[] columnHeaders = {"ID", "Events's Name", "Event location", "Event Date"};

                // Create the table model with the sample data and column headers
                DefaultTableModel model = new DefaultTableModel(data, columnHeaders);

                // Initialize the member variable instead of re-declaring it
                table13 = new JTable(model);

                // Create a scroll pane and add the table to it
                JScrollPane scrollPane = new JScrollPane(table13);

                // Set the bounds of the scroll pane
                scrollPane.setBounds(0, 80, 850, 560);
                showDataTable12();

                // Add a mouse listener to the table
                


                // Add a title label above the table
                JLabel titleLabel = new JLabel("UPCOMING EVENTS");
                titleLabel.setFont(new Font("Verdana", Font.BOLD, 16));
                titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
                titleLabel.setBounds(0, 0, 850, 40);

                // Create buttons for different actions
     


                // Clear any existing components in the table panel
                //  tablePanel.removeAll();

                // Add components to the table panel
                tablePanel.add(titleLabel);
                tablePanel.add(scrollPane);

                // Refresh the table panel
                tablePanel.revalidate();
                tablePanel.repaint();

            }
            private void showVoteTable4() {
                // Sample data for the table
                Object[][] data = {
                        {null, null, null,null,null},
                };
                // Column headers
                String[] columnHeaders = {"ID", "Election position", "Status", "Winner_status","Date"};

                // Create the table model with the sample data and column headers
                DefaultTableModel model = new DefaultTableModel(data, columnHeaders);

                // Initialize the member variable instead of re-declaring it
                table12 = new JTable(model);

                // Create a scroll pane and add the table to it
                JScrollPane scrollPane = new JScrollPane(table12);

                // Set the bounds of the scroll pane
                scrollPane.setBounds(0, 80, 850, 560);
                showDataTable2();

                // Add a mouse listener to the table
                


                // Add a title label above the table
                JLabel titleLabel = new JLabel("PAST ELECTIONS");
                titleLabel.setFont(new Font("Verdana", Font.BOLD, 16));
                titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
                titleLabel.setBounds(0, 0, 850, 40);

                // Create buttons for different actions
     


                // Clear any existing components in the table panel
                //  tablePanel.removeAll();

                // Add components to the table panel
                tablePanel.add(titleLabel);
                tablePanel.add(scrollPane);

                // Refresh the table panel
                tablePanel.revalidate();
                tablePanel.repaint();
            }
            private void feedBackForm2() {
                // Clear any existing components in the form panel
                formPanel.removeAll();

                // Create labels for names and messages
                JLabel lblName = new JLabel("Name:");
                lblName.setFont(new Font("Verdana", Font.PLAIN, 14));
                lblName.setBounds(50, 50, 150, 25);

                JLabel lblMessage = new JLabel("Message:");
                lblMessage.setFont(new Font("Verdana", Font.PLAIN, 14));
                lblMessage.setBounds(50, 100, 150, 25);

                // Create text fields for name
                JTextField txtName = new JTextField();
                txtName.setBounds(200, 50, 200, 25);

                // Create text area for message
                JTextArea txtMessage = new JTextArea();
                txtMessage.setBounds(200, 100, 300, 150);
                txtMessage.setLineWrap(true);

                // Create a send button
                JButton btnSend = new JButton("Send");
                btnSend.setFont(new Font("Verdana", Font.BOLD, 14));
                btnSend.setBounds(200, 270, 100, 30);

                // Add action listener to the send button
                btnSend.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Retrieve data from the form fields
                        String name = txtName.getText();
                        String message = txtMessage.getText();

                        // Get the current date
                        java.util.Date currentDate = new java.util.Date();
                        java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());

                        try {
                            // Insert the feedback into the database
                            PreparedStatement insertStmt = con.prepareStatement("INSERT INTO feedback (names, messages, date_) VALUES (?, ?, ?)");
                            insertStmt.setString(1, name);
                            insertStmt.setString(2, message);
                            insertStmt.setDate(3, sqlDate);
                            insertStmt.executeUpdate();
                            JOptionPane.showMessageDialog(frame, "Feedback sent successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                           // showDataTable1(); // Refresh the table to display the new feedback
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(frame, "Error while sending feedback", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                // Add components to the form panel
                formPanel.add(lblName);
                formPanel.add(lblMessage);
                formPanel.add(txtName);
                formPanel.add(txtMessage);
                formPanel.add(btnSend);

                // Show the form panel and hide the table panel
                formPanel.setVisible(true);
                tablePanel.setVisible(false);

                // Refresh the form panel
                formPanel.revalidate();
                formPanel.repaint();
            }

            private void feedBackForm() {
                // Clear any existing components in the form panel
                formPanel.removeAll();

                // Create labels for current password and new password
                JLabel lblCurrentPassword = new JLabel("Current Password:");
                lblCurrentPassword.setFont(new Font("Verdana", Font.PLAIN, 14));
                lblCurrentPassword.setBounds(50, 50, 150, 25);

                JLabel lblNewPassword = new JLabel("New Password:");
                lblNewPassword.setFont(new Font("Verdana", Font.PLAIN, 14));
                lblNewPassword.setBounds(50, 100, 150, 25);

                // Create text fields for current password and new password
                JTextField txtCurrentPassword = new JTextField();
                txtCurrentPassword.setBounds(200, 50, 200, 25);

                JTextField txtNewPassword = new JTextField();
                txtNewPassword.setBounds(200, 100, 200, 25);

                // Create an update button
                JButton btnUpdate = new JButton("Update");
                btnUpdate.setFont(new Font("Verdana", Font.BOLD, 14));
                btnUpdate.setBounds(200, 150, 100, 30);

                // Add action listener to the update button
                btnUpdate.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Perform action to update the password
                        String currentPassword = txtCurrentPassword.getText();
                        String newPassword = txtNewPassword.getText();

                        // Add code here to update the password in the database or perform any other action

                        // Display a message indicating success or failure
                        JOptionPane.showMessageDialog(frame, "Password updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                });

                // Add components to the form panel
                formPanel.add(lblCurrentPassword);
                formPanel.add(lblNewPassword);
                formPanel.add(txtCurrentPassword);
                formPanel.add(txtNewPassword);
                formPanel.add(btnUpdate);

                // Show the form panel and hide the table panel
                formPanel.setVisible(true);
                tablePanel.setVisible(false);

                // Refresh the form panel
                formPanel.revalidate();
                formPanel.repaint();
            }


            private void showChangePasswordForm() {
                // Clear any existing components in the form panel
                formPanel.removeAll();

                // Create labels for current password and new password
                JLabel lblCurrentPassword = new JLabel("Current Password:");
                lblCurrentPassword.setFont(new Font("Verdana", Font.PLAIN, 14));
                lblCurrentPassword.setBounds(50, 50, 150, 25);

                JLabel lblNewPassword = new JLabel("New Password:");
                lblNewPassword.setFont(new Font("Verdana", Font.PLAIN, 14));
                lblNewPassword.setBounds(50, 100, 150, 25);

                // Create text fields for current password and new password
                JTextField txtCurrentPassword = new JTextField();
                txtCurrentPassword.setBounds(200, 50, 200, 25);

                JTextField txtNewPassword = new JTextField();
                txtNewPassword.setBounds(200, 100, 200, 25);

                // Create an update button
                JButton btnUpdate = new JButton("Update");
                btnUpdate.setFont(new Font("Verdana", Font.BOLD, 14));
                btnUpdate.setBounds(200, 150, 100, 30);

                // Add action listener to the update button
                btnUpdate.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Perform action to update the password
                        String currentPassword = txtCurrentPassword.getText();
                        String newPassword = txtNewPassword.getText();

                        // Check if the current password matches the new password
                        if (currentPassword.equals(newPassword)) {
                            JOptionPane.showMessageDialog(frame, "New password must be different from the current password", "Error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            // Add code here to update the password in the database or perform any other action

                            // Display a message indicating success or failure
                            JOptionPane.showMessageDialog(frame, "Password updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                });

                // Add components to the form panel
                formPanel.add(lblCurrentPassword);
                formPanel.add(lblNewPassword);
                formPanel.add(txtCurrentPassword);
                formPanel.add(txtNewPassword);
                formPanel.add(btnUpdate);

                // Show the form panel and hide the table panel
                formPanel.setVisible(true);
                tablePanel.setVisible(false);

                // Refresh the form panel
                formPanel.revalidate();
                formPanel.repaint();
            }

        }
