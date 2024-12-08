import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DeleteBook extends JFrame {
    private JTextField isbnField;
    private JButton deleteButton, exitButton;
    private JLabel backgroundLabel;

    public DeleteBook() {
        // Frame setup
        setTitle("Delete Book");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
        panel.add(new JLabel("Enter ISBN to delete:"), gbc);

        isbnField = new JTextField(15);
        isbnField.setBackground(new Color(255, 255, 100));
        gbc.gridx = 1;
        panel.add(isbnField, gbc);

        // Delete Button
        deleteButton = new JButton("Delete");
        deleteButton.setBackground(new Color(173, 216, 230));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(deleteButton, gbc);

        // Exit Button
        exitButton = new JButton("Exit");
        exitButton.setBackground(new Color(255, 0, 0));
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(exitButton, gbc);

        backgroundLabel.add(panel);

        // Button Listeners
        deleteButton.addActionListener(e -> deleteBook());
        exitButton.addActionListener(e -> goBackToDashboard());
    }

    private void deleteBook() {
        String isbn = isbnField.getText().trim();  // Trim spaces

        if (isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an ISBN number!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_management", "root", "root123")) {
            // Query to delete the book based on ISBN
            String query = "DELETE FROM books WHERE isbn = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, isbn);

                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "Book deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "No book found with the given ISBN!", "Not Found", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting book!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void goBackToDashboard() {
        // Close the current frame and go back to the dashboard (replace Dashboard.class with your actual dashboard class)
        this.dispose();
        new Dashboard().setVisible(true);  // Assuming you have a Dashboard class
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DeleteBook().setVisible(true));
    }
}
