package milk_processing;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.*;

public class Register_new_user {

    private JFrame frame;
    private JTextField textFieldUsername;
    private JTextField textFieldEmail;
    private JPasswordField passwordField;
    private JComboBox<String> comboBoxRole;
    private JTextField textFieldAddress;
    private JTextField textFieldAge;
    private JComboBox<String> comboBoxGender;

    private Connection con;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	Register_new_user window = new Register_new_user();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Register_new_user() {
        initialize();
        connection();
    }

    void connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/milk_database", "root", "");
            System.out.print("Connected successfully");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializgalie the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Register Form");
        lblNewLabel.setFont(new Font("Verdana", Font.BOLD, 18));
        lblNewLabel.setBounds(145, 23, 147, 29);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Verdana", Font.PLAIN, 14));
        lblUsername.setBounds(59, 84, 87, 23);
        frame.getContentPane().add(lblUsername);

        textFieldUsername = new JTextField();
        textFieldUsername.setBounds(145, 84, 214, 23);
        frame.getContentPane().add(textFieldUsername);
        textFieldUsername.setColumns(10);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Verdana", Font.PLAIN, 14));
        lblEmail.setBounds(59, 128, 87, 23);
        frame.getContentPane().add(lblEmail);

        textFieldEmail = new JTextField();
        textFieldEmail.setColumns(10);
        textFieldEmail.setBounds(145, 128, 214, 23);
        frame.getContentPane().add(textFieldEmail);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Verdana", Font.PLAIN, 14));
        lblPassword.setBounds(59, 172, 87, 23);
        frame.getContentPane().add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(145, 172, 214, 23);
        frame.getContentPane().add(passwordField);

        JLabel lblRole = new JLabel("Role:");
        lblRole.setFont(new Font("Verdana", Font.PLAIN, 14));
        lblRole.setBounds(59, 216, 87, 23);
        frame.getContentPane().add(lblRole);

        comboBoxRole = new JComboBox<>();
        comboBoxRole.addItem("Admin");
        comboBoxRole.addItem("User");
        comboBoxRole.setBounds(145, 216, 214, 23);
        frame.getContentPane().add(comboBoxRole);

        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setFont(new Font("Verdana", Font.PLAIN, 14));
        lblAddress.setBounds(59, 260, 87, 23);
        frame.getContentPane().add(lblAddress);

        textFieldAddress = new JTextField();
        textFieldAddress.setBounds(145, 260, 214, 23);
        frame.getContentPane().add(textFieldAddress);
        textFieldAddress.setColumns(10);

        JLabel lblAge = new JLabel("Age:");
        lblAge.setFont(new Font("Verdana", Font.PLAIN, 14));
        lblAge.setBounds(59, 304, 87, 23);
        frame.getContentPane().add(lblAge);

        textFieldAge = new JTextField();
        textFieldAge.setBounds(145, 304, 214, 23);
        frame.getContentPane().add(textFieldAge);
        textFieldAge.setColumns(10);

        JLabel lblGender = new JLabel("Gender:");
        lblGender.setFont(new Font("Verdana", Font.PLAIN, 14));
        lblGender.setBounds(59, 348, 87, 23);
        frame.getContentPane().add(lblGender);

        comboBoxGender = new JComboBox<>();
        comboBoxGender.addItem("Male");
        comboBoxGender.addItem("Female");
        comboBoxGender.setBounds(145, 348, 214, 23);
        frame.getContentPane().add(comboBoxGender);

        JButton btnRegister = new JButton("Register");
        btnRegister.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = textFieldUsername.getText();
                String email = textFieldEmail.getText();
                String password = new String(passwordField.getPassword());
                String role = (String) comboBoxRole.getSelectedItem();
                String address = textFieldAddress.getText();
                String age = textFieldAge.getText();
                String gender = (String) comboBoxGender.getSelectedItem();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields");
                    return;
                }

                try {
                    PreparedStatement pst = con.prepareStatement("INSERT INTO users (names, email, password, role, address, age, gender) VALUES (?, ?, ?, ?, ?, ?, ?)");
                    pst.setString(1, username);
                    pst.setString(2, email);
                    pst.setString(3, password);
                    pst.setString(4, role);
                    pst.setString(5, address);
                    pst.setString(6, age);
                    pst.setString(7, gender);

                    int rowsAffected = pst.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(frame, "Registration successful");
                        LogIn_user jobSeekersWindow = new LogIn_user();
                        jobSeekersWindow.frame.setVisible(true);
                        frame.dispose(); // Close the login window

                        // Clear fields after successful registration
                        textFieldUsername.setText("");
                        textFieldEmail.setText("");
                        passwordField.setText("");
                        comboBoxRole.setSelectedIndex(0);
                        textFieldAddress.setText("");
                        textFieldAge.setText("");
                        comboBoxGender.setSelectedIndex(0);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Registration failed");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error occurred while registering");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid age");
                }
            }
        });
        btnRegister.setBounds(156, 400, 89, 23);
        frame.getContentPane().add(btnRegister);
    }
}
