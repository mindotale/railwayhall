package presentation.pages.configpage;


import domain.entrances.EntranceConfig;
import domain.ticketboxes.TicketBoxConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfigPage extends JFrame {

    private JTextField clientCapacityField;
    private JTextField restartClientCapacityField;
    private DefaultListModel<TicketBoxConfig> ticketBoxesListModel;
    private JList<TicketBoxConfig> ticketBoxesList;
    private JTextField reservedTicketBoxField;
    private DefaultListModel<EntranceConfig> entrancesListModel;
    private JList<EntranceConfig> entrancesList;

    public ConfigPage() {
        setTitle("Railway Hall Configuration");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        // Create a panel to hold the configuration components
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Client Capacity
        JLabel clientCapacityLabel = new JLabel("Client Capacity:");
        panel.add(clientCapacityLabel, gbc);

        gbc.gridx++;
        clientCapacityField = new JTextField(10);
        panel.add(clientCapacityField, gbc);

        // Restart Client Capacity
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel restartClientCapacityLabel = new JLabel("Restart Client Capacity:");
        panel.add(restartClientCapacityLabel, gbc);

        gbc.gridx++;
        restartClientCapacityField = new JTextField(10);
        panel.add(restartClientCapacityField, gbc);

        // Ticket Boxes
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel ticketBoxesLabel = new JLabel("Ticket Boxes:");
        panel.add(ticketBoxesLabel, gbc);

        gbc.gridx++;
        ticketBoxesListModel = new DefaultListModel<>();
        ticketBoxesList = new JList<>(ticketBoxesListModel);
        JScrollPane ticketBoxesScrollPane = new JScrollPane(ticketBoxesList);
        ticketBoxesScrollPane.setPreferredSize(new Dimension(200, 100));
        panel.add(ticketBoxesScrollPane, gbc);

        // Reserved Ticket Box
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel reservedTicketBoxLabel = new JLabel("Reserved Ticket Box:");
        panel.add(reservedTicketBoxLabel, gbc);

        gbc.gridx++;
        reservedTicketBoxField = new JTextField(10);
        panel.add(reservedTicketBoxField, gbc);

        // Entrances
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel entrancesLabel = new JLabel("Entrances:");
        panel.add(entrancesLabel, gbc);

        gbc.gridx++;
        entrancesListModel = new DefaultListModel<>();
        entrancesList = new JList<>(entrancesListModel);
        JScrollPane entrancesScrollPane = new JScrollPane(entrancesList);
        entrancesScrollPane.setPreferredSize(new Dimension(200, 100));
        panel.add(entrancesScrollPane, gbc);

        // Save Configuration button
        gbc.gridx = 0;
        gbc.gridy++;
        JButton saveButton = new JButton("Save Configuration");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveConfiguration();
            }
        });
        panel.add(saveButton, gbc);

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
