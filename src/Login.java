import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel backgroundLabel;

    public Login() {
        // Frame setup
        setTitle("Library Management System - Login");
        setSize(1366, 766);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set the background image
        ImageIcon backgroundImage = new ImageIcon("src/ions/Login background.PNG");
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new BorderLayout());
        add(backgroundLabel);


        // Panel setup
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);
        backgroundLabel.add(panel);

        // Username label and text field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        usernameLabel.setBounds(700, 300, 100, 30);
        panel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 18));
        usernameField.setBounds(800, 300, 200, 30);
        usernameField.setBackground(new Color(255, 255, 100));
        panel.add(usernameField);

        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordLabel.setBounds(700, 350, 100, 30);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordField.setBounds(800, 350, 200, 30);
        passwordField.setBackground(new Color(255, 255, 100));
        panel.add(passwordField);

        // Login button
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setBounds(700, 400, 100, 40);
        loginButton.setBackground(new Color(34, 139, 34));
        loginButton.setForeground(Color.WHITE);
        panel.add(loginButton);

        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 18));
        closeButton.setBounds(850, 400, 100, 40); // Position and size
        closeButton.setBackground(Color.RED);
        closeButton.setForeground(Color.WHITE);
        panel.add(closeButton);

// Action for Close Button
        closeButton.addActionListener(e -> System.exit(0));

        // Login button action
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (validateLogin(username, password)) {
                    // Redirect to Dashboard
                    switchToDashboard();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password!");
                }
            }
        });
    }

    private boolean validateLogin(String username, String password) {
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/library_management", "root", "root123");
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                conn.close();
                return true;
            } else {
                conn.close();
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Switch to Dashboard screen
    private void switchToDashboard() {
        this.setContentPane(new Dashboard());
        this.revalidate();
        this.repaint();
    }

    public static void main(String[] args) {
        // Show login window
        Login login = new Login();
        login.setVisible(true);
    }
}
