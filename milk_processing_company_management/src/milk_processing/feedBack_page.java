package milk_processing;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class feedBack_page extends JFrame {
    JFrame frame;
    private JPanel contentPane;
    private JTable table;

    Connection con;
    PreparedStatement pst;

    public feedBack_page() {
        frame = this;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 700);
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        // Add the top header with the title "Distributers" and the back button
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.BLUE);
        contentPane.add(headerPanel, BorderLayout.NORTH);

        JLabel lblHeader = new JLabel("Feedback");
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setFont(new Font("Verdana", Font.BOLD, 24));
        headerPanel.add(lblHeader);

        JButton btnBack = new JButton("Back");
        btnBack.setBackground(Color.GRAY);
        headerPanel.add(btnBack);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	milk_processing_Admin  ppp = new milk_processing_Admin();
                ppp.frame.setVisible(true);
                frame.dispose();
            }
        });

        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);

        table = new JTable();
        scrollPane.setViewportView(table);

        // Connect to the database and fetch feedback data
        connection();
        showFeedbackData();
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

    private void showFeedbackData() {
        try {
            pst = con.prepareStatement("SELECT * FROM feedback");
            ResultSet rs = pst.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();

            DefaultTableModel dataInTable = new DefaultTableModel();
            Vector<String> columnNames = new Vector<>();
            Vector<Vector<Object>> dataRows = new Vector<>();

            // Get column names
            for (int i = 1; i <= cc; i++) {
                columnNames.add(rsmd.getColumnName(i));
            }

            // Get row data
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= cc; i++) {
                    row.add(rs.getObject(i));
                }
                dataRows.add(row);
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
                    feedBack_page frame = new feedBack_page();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
