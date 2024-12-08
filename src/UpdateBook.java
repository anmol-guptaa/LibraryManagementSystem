import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UpdateBook extends JFrame {
    private JTextField isbnField, titleField, authorField, publisherField;
    private JButton searchButton, updateButton, exitButton;
    private JLabel backgroundLabel;

    public UpdateBook() {
        // Frame setup
        setTitle("Update Book");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Background image
        ImageIcon backgroundImage = new ImageIcon("src/ions/123456.png");
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(new GridBagLayout()); // Center components
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
        gbc.gridx = 1;
        panel.add(titleField, gbc);

        // Author Label and Field
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Author:"), gbc);

        authorField = new JTextField(15);
        authorField.setBackground(new Color(255, 255, 100));
        gbc.gridx = 1;
        panel.add(authorField, gbc);

        // Publisher Label and Field
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Publisher:"), gbc);

        publisherField = new JTextField(15);
        publisherField.setBackground(new Color(255, 255, 100));
        gbc.gridx = 1;
        panel.add(publisherField, gbc);

        // Update Button
        updateButton = new JButton("Update");
        updateButton.setBackground(new Color(34, 139, 34));
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(updateButton, gbc);

        // Exit Button
        exitButton = new JButton("Exit");
        exitButton.setBackground(new Color(255, 0, 0));
        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(exitButton, gbc);

        backgroundLabel.add(panel);

        // Button Listeners
        searchButton.addActionListener(e -> searchBook());
        updateButton.addActionListener(e -> updateBook());
        exitButton.addActionListener(e -> dispose()); //
    }

    private void searchBook() {
        String isbn = isbnField.getText().trim();  // Trim spaces

        if (isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an ISBN number!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_management", "root", "JA08@jaya")) {
            String query = "SELECT title, author, publisher FROM books WHERE isbn = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, isbn);
                try (ResultSet rs = stmt.executeQuery()) {
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
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving book details!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateBook() {
        String isbn = isbnField.getText().trim(); // Trim spaces
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String publisher = publisherField.getText().trim();

        // Check if fields are empty
        if (isbn.isEmpty() || title.isEmpty() || author.isEmpty() || publisher.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_management", "root", "JA08@jaya")) {
            // Update query using the correct column order
            String query = "UPDATE books SET title = ?, author = ?, publisher = ? WHERE isbn = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, title);
                stmt.setString(2, author);
                stmt.setString(3, publisher);
                stmt.setString(4, isbn);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Book details updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "No book found with the given ISBN to update.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating book details!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UpdateBook().setVisible(true));
    }
}