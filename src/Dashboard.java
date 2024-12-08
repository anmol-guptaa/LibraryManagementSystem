import javax.swing.*;
import java.awt.*;

public class Dashboard extends JPanel {
    private JButton addBookButton, updateButton, deleteButton, searchButton, logoutButton;

    public Dashboard() {
        // Set layout and background
        setLayout(null);
        setBackground(new Color(240, 248, 255));

        // Add background image
        JLabel background = new JLabel(new ImageIcon("src/ions/Library_Book_532388_1366x768.jpg"));
        background.setBounds(0, 0, 1366, 766);
        add(background);
        background.setLayout(null);

        // Add Book button
        addBookButton = createButton("Add Book");
        addBookButton.setBounds(500, 300, 150, 40);
        addBookButton.setBackground(new Color(255, 255, 100));
        background.add(addBookButton);

        // Update Book button
        updateButton = createButton("Update Book");
        updateButton.setBounds(700, 300, 150, 40);
        updateButton.setBackground(new Color(255, 255, 100));
        background.add(updateButton);

        // Delete Book button
        deleteButton = createButton("Delete Book");
        deleteButton.setBounds(500, 400, 150, 40);
        deleteButton.setBackground(new Color(255, 255, 100));
        background.add(deleteButton);

        // Search Book button
        searchButton = createButton("Search Book");
        searchButton.setBounds(700, 400, 150, 40);
        searchButton.setBackground(new Color(255, 255, 100));
        background.add(searchButton);

        // Search Book button
        logoutButton = createButton("Logout");
        logoutButton.setBounds(600, 500, 150, 40);
        logoutButton.setBackground(new Color(255, 0, 0));
        background.add(logoutButton);

        // Button Actions
        addBookButton.addActionListener(e -> openFrame(new AddBook()));
        updateButton.addActionListener(e -> openFrame(new UpdateBook()));
        deleteButton.addActionListener(e -> openFrame(new DeleteBook()));
        searchButton.addActionListener(e -> openFrame(new SearchBook()));
        logoutButton.addActionListener(e -> openFrame(new Login()));
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(Color.WHITE); // White background
        button.setForeground(Color.BLACK); // Black text
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return button;
    }



    private void openFrame(JFrame frame) {
        frame.setVisible(true);
    }
}