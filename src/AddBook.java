import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddBook extends JFrame {
    private JTextField isbnField, titleField, authorField, publisherField;
    private JButton saveButton, exitButton;
    private JLabel backgroundLabel;

    public AddBook() {
        // Frame setup
        setTitle("Add Book");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true); // Remove default title bar

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
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ISBN Label and Field
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("ISBN:"), gbc);
        isbnField = new JTextField(20);
        isbnField.setBackground(new Color(255, 255, 100));
        gbc.gridx = 1;
        panel.add(isbnField, gbc);

        // Title Label and Field
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Title:"), gbc);
        titleField = new JTextField(20);
        titleField.setBackground(new Color(255, 255, 100));
        gbc.gridx = 1;
        panel.add(titleField, gbc);

        // Author Label and Field
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Author:"), gbc);
        authorField = new JTextField(20);
        authorField.setBackground(new Color(255, 255, 100));
        gbc.gridx = 1;
        panel.add(authorField, gbc);

        // Publisher Label and Field
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Publisher:"), gbc);
        publisherField = new JTextField(10);
        publisherField.setBackground(new Color(255, 255, 100));
        gbc.gridx = 1;
        panel.add(publisherField, gbc);

        // Save Button
        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> saveBook());
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setBackground(new Color(34, 139, 34));
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(saveButton, gbc);

        // Exit Button
        exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {dispose();});
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));// Exit application
        exitButton.setBackground(new Color(255, 0, 0));
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(exitButton, gbc);

        backgroundLabel.add(panel);
    }

    private void saveBook() {
        String isbn = isbnField.getText();
        String title = titleField.getText();
        String author = authorField.getText();
        String publisher = publisherField.getText();

        if (isbn.isEmpty() || title.isEmpty() || author.isEmpty() || publisher.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/library_management", "root", "JA08@jaya");
            String query = "INSERT INTO books ( title, author,isbn, publisher) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, isbn);
            stmt.setString(4, publisher);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Book details saved successfully!");
            }

            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving book details!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddBook().setVisible(true));
    }
}