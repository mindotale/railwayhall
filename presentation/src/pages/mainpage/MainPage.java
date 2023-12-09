package pages.mainpage;

import pages.configpage.ConfigPage;

import javax.swing.*;
import java.awt.*;

public class MainPage extends JFrame {

    public MainPage() {
        setTitle("Main Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        // Create a panel to hold the buttons
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Add some spacing around buttons

        // Create "Start" button
        JButton startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(100, 50));
        startButton.addActionListener(e -> {
            // Open the configuration page
            new ConfigPage();
            // Close the main page if needed
            dispose();
        });

        // Create "Quit" button
        JButton quitButton = new JButton("Quit");
        quitButton.setPreferredSize(new Dimension(100, 50));
        quitButton.addActionListener(e -> System.exit(0));

        // Add buttons to the panel
        panel.add(startButton, gbc);
        gbc.gridy++;
        panel.add(quitButton, gbc);

        // Add panel to the frame
        add(panel);

        setVisible(true);
    }
}
