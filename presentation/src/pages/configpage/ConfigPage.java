package pages.configpage;

import javax.swing.*;

public class ConfigPage extends JFrame {

    public ConfigPage() {
        setTitle("Configuration Page");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 100);
        setLocationRelativeTo(null);

        // Create a label for configuration information
        JLabel configLabel = new JLabel("App Configuration Goes Here");
        configLabel.setHorizontalAlignment(JLabel.CENTER);

        // Add label to the configuration frame
        add(configLabel);

        setVisible(true);
    }
}
