import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SearchBook extends JFrame {
    private JTextField isbnField;
    private JTextField titleField, authorField, publisherField;
    private JButton searchButton, exitButton;
    private JLabel backgroundLabel;

    public SearchBook() {
        // Frame setup
        setTitle("Search Book");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setUndecorated(true); // Remove default close/minimize buttons
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the background image
        ImageIcon backgroundImage = new ImageIcon("src/ions/123456.png");
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new GridBagLayout());
        add(backgroundLabel);

        // Panel setup
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setOpaque(false); // Transparent panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ISBN Label and Field
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Enter ISBN:"), gbc);

        isbnField = new JTextField(15);
        isbnField.setBackground(new Color(255, 255, 100));
        gbc.gridx = 1;
        panel.add(isbnField, gbc);

        // Search Button
        searchButton = new JButton("Search");
        searchButton.setBackground(new Color(173, 216, 230));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(searchButton, gbc);

        // Title Label and Field
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Title:"), gbc);

        titleField = new JTextField(15);
        titleField.setBackground(new Color(255, 255, 100));
        titleField.setEditable(false);
        gbc.gridx = 1;
        panel.add(titleField, gbc);

        // Author Label and Field
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Author:"), gbc);

        authorField = new JTextField(15);
        authorField.setBackground(new Color(255, 255, 100));
        authorField.setEditable(false);
        gbc.gridx = 1;
        panel.add(authorField, gbc);

        // Publisher Label and Field
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Publisher:"), gbc);

        publisherField = new JTextField(15);
        publisherField.setBackground(new Color(255, 255, 100));
        publisherField.setEditable(false);
        gbc.gridx = 1;
        panel.add(publisherField, gbc);

        // Exit Button
        exitButton = new JButton("Exit");
        exitButton.setBackground(new Color(255, 0, 0));
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(exitButton, gbc);

        backgroundLabel.add(panel);

        // Button Listeners
        searchButton.addActionListener(e -> searchBook());
        exitButton.addActionListener(e -> {dispose();});
    }

    private void searchBook() {
        String isbn = isbnField.getText();

        if (isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an ISBN number!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/library_management", "root", "JA08@jaya");
            String query = "SELECT title, author, publisher FROM books WHERE isbn = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, isbn);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                titleField.setText(rs.getString("title"));
                authorField.setText(rs.getString("author"));
                publisherField.setText(rs.getString("publisher"));
            } else {
                JOptionPane.showMessageDialog(this, "No book found with the given ISBN!", "Not Found", JOptionPane.INFORMATION_MESSAGE);
                titleField.setText("");
                authorField.setText("");
                publisherField.setText("");
            }

            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving book details!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SearchBook().setVisible(true));
    }
}
