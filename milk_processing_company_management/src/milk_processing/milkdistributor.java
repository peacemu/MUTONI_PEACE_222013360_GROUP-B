package milk_processing;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class milkdistributor {

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
                    milkdistributor window = new milkdistributor();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public milkdistributor() {
        initialize();
        table1 = new JTable();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1050, 733);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JPanel topBarPanel = new JPanel();
        topBarPanel.setBackground(Color.CYAN); // Change to CYAN color
        topBarPanel.setBounds(0, 0, 1050, 50);
        frame.getContentPane().add(topBarPanel);
        topBarPanel.setLayout(null);

        lblWelcome = new JLabel("INYANGE MANAGEMENT SYSTEM");
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Verdana", Font.BOLD, 16));
        lblWelcome.setBounds(0, 0, 1050, 50);
        topBarPanel.add(lblWelcome);

        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setBackground(Color.BLUE); // Change to blue color
        sidebarPanel.setBounds(0, 50, 200, 700);
        frame.getContentPane().add(sidebarPanel);
        sidebarPanel.setLayout(null);

        // Initialize sidebar buttons
        btnProfile = createButton("DISTRIBUTER", 50);
        btnViewProposal = createButton("View Message", 100);
        Distributors = createButton("Distributors", 150);
        Sales = createButton("Sales", 200);
        Sales.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SALES_DISTR sales_page_ = new SALES_DISTR();
                sales_page_.frame.setVisible(true);
                frame.dispose(); // Close the login window
            }
        });
        Products = createButton("Products", 250);
        Products.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	product_page sales_page_ = new product_page();
                sales_page_.frame.setVisible(true);
                frame.dispose(); // Close the login window
            }
        });
        Orders = createButton("Orders", 300);
        Orders.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	product_page sales_page_ = new product_page();
                sales_page_.frame.setVisible(true);
                frame.dispose(); // Close the login window
            }
        });
        Users = createButton("Users", 350);
        Users.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	users_p_d sales_page_ = new users_p_d();
                sales_page_.frame.setVisible(true);
                frame.dispose(); // Close the login window
            }
        });
         
        btnFeedback = createButton("Feedback/Contact ", 400);
        btnFeedback.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	feed_back_distr sales_page_ = new feed_back_distr();
                sales_page_.frame.setVisible(true);
                frame.dispose(); // Close the login window
            }
        });
        btnLogout = createButton("Logout", 470);

        // Add sidebar buttons to sidebarPanel
        sidebarPanel.add(btnProfile);
        sidebarPanel.add(btnViewProposal);
        sidebarPanel.add(Distributors);
        sidebarPanel.add(Sales);
        sidebarPanel.add(Products);
        sidebarPanel.add(Orders);
        sidebarPanel.add(Users);
        sidebarPanel.add(btnFeedback);
        sidebarPanel.add(btnLogout);

        // Add hover effect to sidebar buttons
        addHoverEffect(btnProfile);
        addHoverEffect(btnViewProposal);
        addHoverEffect(Distributors);
        addHoverEffect(Sales);
        addHoverEffect(Products);
        addHoverEffect(Orders);
        addHoverEffect(Users);
        addHoverEffect(btnFeedback);
        addHoverEffect(btnLogout);

        // Create and add the three cards in the middle bar
        createCards();

        // Initially show the welcome message with the default username
        showWelcomeMessage("Username");
    }

    // Function to add hover effect to a button
    private void addHoverEffect(JButton button) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.GREEN); // Change to green when hovered
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 128, 0)); // Restore original color
            }
        });
    }

    // Function to create a button
    private JButton createButton(String text, int y) {
        JButton button = new JButton(text);
        button.setFont(new Font("Verdana", Font.BOLD, 14));
        button.setBounds(0, y, 200, 40);
        button.setOpaque(true);
        button.setBackground(Color.BLUE);
        button.setBorderPainted(false);
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

        // Change the background color of the cards to CYAN
        salesCard.setBackground(Color.CYAN);
        productsCard.setBackground(Color.CYAN);
        stockCard.setBackground(Color.CYAN);
    }

    // Function to create a card with specified title, subtitle, and value
    private JPanel createCard(String title, String subtitle, String value) {
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(null);
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cardPanel.setBackground(Color.WHITE);
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
        lblWelcome.setText("INYANGE MANAGEMENT SYSTEM DISTRIBUTER");
    }
}
