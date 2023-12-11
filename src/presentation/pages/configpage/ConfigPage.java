package presentation.pages.configpage;


import domain.common.IntegerIdGenerator;
import presentation.pages.configpage.ticketboxes.EntranceConfigPanel;
import presentation.pages.configpage.ticketboxes.ReservedTicketBoxConfigPanel;
import presentation.pages.configpage.ticketboxes.TicketBoxConfigPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class ConfigPage extends JFrame {
    private List<TicketBoxConfigPanel> ticketBoxConfigPanels;
    private List<EntranceConfigPanel> entranceConfigPanels;
    private ReservedTicketBoxConfigPanel reservedTicketBoxConfigPanel;
    private JTextField clientCapacityField;
    private JTextField restartClientCapacityField;
    public ConfigPage() {
        setTitle("Railway Hall Configuration");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1920, 1080); // Set a fixed size for simplicity
        setLocationRelativeTo(null);

        // Create a panel to hold the configuration components
        JPanel panel = new JPanel(null); // Use null layout
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Ticket Boxes Configuration
        ticketBoxConfigPanels = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            TicketBoxConfigPanel ticketBoxPanel = new TicketBoxConfigPanel(i);
            ticketBoxConfigPanels.add(ticketBoxPanel);

            // Set bounds for ticket box components
            ticketBoxPanel.setBounds(50, (i-1)*100, 320, 100);
            panel.add(ticketBoxPanel);
        }

        // Entrances Configuration
        entranceConfigPanels = new ArrayList<>();
        var idGenerator = new IntegerIdGenerator();
        for (int i = 1; i <= 8; i++) {
            EntranceConfigPanel entrancePanel = new EntranceConfigPanel(i, idGenerator);
            entranceConfigPanels.add(entrancePanel);

            // Set bounds for entrance components
            entrancePanel.setBounds(400, (i-1)*100, 320, 100);
            panel.add(entrancePanel);
        }

        // Reserved Ticket Box Configuration Panel
        ReservedTicketBoxConfigPanel reservedTicketBoxPanel = new ReservedTicketBoxConfigPanel();
        reservedTicketBoxPanel.setBorder(BorderFactory.createTitledBorder("Reserved Ticket Box"));
        reservedTicketBoxPanel.setBounds(750, 0, 320, 100);

        panel.add(reservedTicketBoxPanel);

        // Client Capacity Configuration Panel
        JPanel clientCapacityPanel = new JPanel(null);
        clientCapacityPanel.setBorder(BorderFactory.createTitledBorder("Client Capacity Configuration"));
        clientCapacityPanel.setBounds(750, 130, 200, 80);

        // Client Capacity
        JLabel clientCapacityLabel = new JLabel("Client Capacity:");
        clientCapacityLabel.setBounds(10, 20, 150, 20);
        clientCapacityPanel.add(clientCapacityLabel);

        JTextField clientCapacityField = new JTextField(5);
        clientCapacityField.setBounds(130, 20, 50, 20);
        clientCapacityPanel.add(clientCapacityField);

        // Restart Client Capacity
        JLabel restartClientCapacityLabel = new JLabel("Restart Client Capacity:");
        restartClientCapacityLabel.setBounds(10, 50, 170, 20);
        clientCapacityPanel.add(restartClientCapacityLabel);

        JTextField restartClientCapacityField = new JTextField(5);
        restartClientCapacityField.setBounds(130, 50, 50, 20);
        clientCapacityPanel.add(restartClientCapacityField);

        panel.add(clientCapacityPanel);


        // Save Configuration button
        JButton saveButton = new JButton("Save Configuration");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveConfiguration();
            }
        });
        saveButton.setBounds(750, 220, 200, 50); // Adjusted position to the bottom-right corner
        panel.add(saveButton);

        // Add panel to the frame
        add(panel);

        setVisible(true);
    }

    private void saveConfiguration() {
        try {
            System.out.println("Configuration saved: " );
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
