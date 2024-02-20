package milk_processing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.border.Border;

public class milk_processing_Admin {

    public JFrame frame;
    private JButton btnProfile;
    private JButton btnViewProposal;
    private JButton btnLogout;
    private JButton Distributors;
    private JButton Sales;
    private JButton Products;
    private JButton Orders;
    private JButton Users;
    private JButton btnFeedback;

    private JPanel tablePanel;
    private JPanel formPanel;
    
    private JLabel lblWelcome;

    private JTable table1;
    private JTable table12;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    milk_processing_Admin window = new milk_processing_Admin();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public milk_processing_Admin() {
        initialize();
        connection();
        table1 = new JTable();
    }

    Connection con;
    PreparedStatement pst;

    void connection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/milk_database", "root", "");
            System.out.print("Connected well");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1050, 733);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JPanel topBarPanel = new JPanel();
        topBarPanel.setBackground(Color.CYAN);
        topBarPanel.setBounds(0, 0, 1050, 50);
        frame.getContentPane().add(topBarPanel);
        topBarPanel.setLayout(null);

        lblWelcome = new JLabel("MILK PROCESSING SYSTEM ADMIN");
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Verdana", Font.BOLD, 16));
        lblWelcome.setBounds(0, 0, 1050, 50);
        topBarPanel.add(lblWelcome);

        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setBackground(Color.BLUE); // Change sidebar color to blue
        sidebarPanel.setBounds(0, 50, 200, 700);
        frame.getContentPane().add(sidebarPanel);
        sidebarPanel.setLayout(null);

        JLabel lblHomepage = new JLabel("ADMIN PAGE");
        lblHomepage.setForeground(Color.WHITE);
        lblHomepage.setHorizontalAlignment(SwingConstants.CENTER);
        lblHomepage.setFont(new Font("Verdana", Font.BOLD, 19));
        lblHomepage.setBounds(0, 0, 200, 28);
        sidebarPanel.add(lblHomepage);

        btnProfile = createButton("PROFILE", 50);
        btnProfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        sidebarPanel.add(btnProfile);

        btnViewProposal = createButton("Add New Message", 100);
        btnViewProposal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Announcements__milk_proc annoucement_page = new Announcements__milk_proc();
            	annoucement_page.frame.setVisible(true);
                frame.dispose(); // Close the login window
            }
        });
        sidebarPanel.add(btnViewProposal);

        Distributors = createButton("Distributors", 150);
        Distributors.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                distributers_page sales_page_ = new distributers_page();
                sales_page_.frame.setVisible(true);
                frame.dispose(); // Close the login window
            }
        });
        sidebarPanel.add(Distributors);

        Sales = createButton("Sales", 200);
        Sales.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sales_page sales_page_ = new sales_page();
                sales_page_.frame.setVisible(true);
                frame.dispose(); // Close the login window
            }
        });
        sidebarPanel.add(Sales);

        Products = createButton("Products", 250);
        Products.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                product_page sales_page_ = new product_page();
                sales_page_.frame.setVisible(true);
                frame.dispose(); // Close the login window
            }
        });
        sidebarPanel.add(Products);

        Orders = createButton("Orders", 300);
        Orders.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                oders_page usersPage = new oders_page();
                usersPage.frame.setVisible(true);
                frame.dispose(); // Close the current window
            }
        });
        sidebarPanel.add(Orders);

        Users = createButton("Users", 350);
        Users.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                users_page usersPage = new users_page();
                usersPage.frame.setVisible(true);
                frame.dispose(); // Close the current window
            }
        });
        sidebarPanel.add(Users);

        btnFeedback = createButton("Feedback/Contact Admin", 400);
        btnFeedback.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                feedBack_page sales_page_ = new feedBack_page();
                sales_page_.frame.setVisible(true);
                frame.dispose(); // Close the login window
            }
        });
        sidebarPanel.add(btnFeedback);

        // Create a small space between the Feedback button and the Logout button
        JPanel spacePanel = new JPanel();
        spacePanel.setBounds(0, 450, 200, 20);
        spacePanel.setOpaque(false);
        sidebarPanel.add(spacePanel);

        // Add a logout button to the sidebar
        btnLogout = createButton("Logout", 470);
        btnLogout.setBackground(Color.RED);
        btnLogout.setForeground(Color.WHITE);
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LogIn_user sales_page_ = new LogIn_user();
                sales_page_.frame.setVisible(true);
                frame.dispose(); // Close the login window
            }
        });
        sidebarPanel.add(btnLogout);

        // Create and add the three cards in the middle bar
        createCards();

        // Initially show the welcome message with the default username
        showWelcomeMessage("Username");
    }

    private JButton createButton(String text, int y) {
        JButton button = new JButton(text);
        button.setFont(new Font("Verdana", Font.BOLD, 14));
        button.setBounds(0, y, 200, 40);
        button.setOpaque(true);
        button.setBackground(Color.BLUE);
        button.setBorderPainted(false);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.GREEN);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.BLUE);
            }
        });
        return button;
    }

    // Function to create cards in the middle bar
    private void createCards() {
        // Define the card size and position
        int cardWidth = 200;
        int cardHeight = 150;
        int cardX = 250;
        int cardY = 100;

        // Card 1: Sales
        JPanel salesCard = createCard("Sales", "Total Sales", "21");
        salesCard.setBounds(cardX, cardY, cardWidth, cardHeight);
        frame.getContentPane().add(salesCard);

        // Card 2: Products
        JPanel productsCard = createCard("Products", "Available Products", "50");
        productsCard.setBounds(cardX + cardWidth + 50, cardY, cardWidth, cardHeight);
        frame.getContentPane().add(productsCard);

        // Card 3: Stock
        JPanel stockCard = createCard("Stock", "Total Stock", "1000");
        stockCard.setBounds(cardX + (cardWidth + 50) * 2, cardY, cardWidth, cardHeight);
        frame.getContentPane().add(stockCard);
    }

    // Function to create a card with specified title, subtitle, and value
    private JPanel createCard(String title, String subtitle, String value) {
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(null);
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cardPanel.setBackground(Color.CYAN); // Change card color to cyan
        cardPanel.setPreferredSize(new Dimension(200, 150));
        cardPanel.setMaximumSize(new Dimension(200, 150));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 14));
        titleLabel.setBounds(10, 10, 150, 20);
        cardPanel.add(titleLabel);

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font("Verdana", Font.PLAIN, 12));
        subtitleLabel.setBounds(10, 40, 150, 20);
        cardPanel.add(subtitleLabel);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Verdana", Font.BOLD, 36));
        valueLabel.setBounds(10, 70, 150, 50);
        cardPanel.add(valueLabel);

        // Round the borders of the card
        Border roundedBorder = BorderFactory.createLineBorder(Color.BLACK);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(roundedBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        return cardPanel;
    }

    private void showWelcomeMessage(String username) {
        lblWelcome.setText("MILK PROCESSING SYSTEMA");
    }
}
