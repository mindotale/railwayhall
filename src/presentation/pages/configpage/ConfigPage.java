package presentation.pages.configpage;


import domain.Main.*;
import domain.clients.ClientStatus;
import domain.clients.ConstantClientGenerationStrategy;
import domain.common.Vector;
import domain.entrances.EntranceConfig;
import domain.railwayhalls.RailwayHall;
import domain.railwayhalls.RailwayHallConfig;
import domain.ticketboxes.RandomTicketTimeProcessingStrategy;
import domain.ticketboxes.TicketBoxConfig;
import domain.common.IntegerIdGenerator;
import presentation.pages.configpage.ticketboxes.EntranceConfigPanel;
import presentation.pages.configpage.ticketboxes.ReservedTicketBoxConfigPanel;
import presentation.pages.configpage.ticketboxes.TicketBoxConfigPanel;
import presentation.pages.mainpage.MainPage;
import presentation.pages.simulationpage.SimulationPage;
import presentation.viewmodels.RailwayHallViewModel;

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
        reservedTicketBoxConfigPanel = new ReservedTicketBoxConfigPanel();
        reservedTicketBoxConfigPanel.setBorder(BorderFactory.createTitledBorder("Reserved Ticket Box"));
        reservedTicketBoxConfigPanel.setBounds(750, 0, 320, 100);

        panel.add(reservedTicketBoxConfigPanel);

        // Client Capacity Configuration Panel
        JPanel clientCapacityPanel = new JPanel(null);
        clientCapacityPanel.setBorder(BorderFactory.createTitledBorder("Client Capacity Configuration"));
        clientCapacityPanel.setBounds(750, 130, 200, 80);

        // Client Capacity
        JLabel clientCapacityLabel = new JLabel("Client Capacity:");
        clientCapacityLabel.setBounds(10, 20, 150, 20);
        clientCapacityPanel.add(clientCapacityLabel);

        clientCapacityField = new JTextField(5);
        clientCapacityField.setBounds(130, 20, 50, 20);
        clientCapacityPanel.add(clientCapacityField);

        // Restart Client Capacity
        JLabel restartClientCapacityLabel = new JLabel("Restart Client Capacity:");
        restartClientCapacityLabel.setBounds(10, 50, 170, 20);
        clientCapacityPanel.add(restartClientCapacityLabel);

        restartClientCapacityField = new JTextField(5);
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


        // Save Configuration button
        JButton defaultButton = new JButton("Default Configuration");
        defaultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                defaultConfiguration();
            }
        });
        defaultButton.setBounds(750, 280, 200, 50); // Adjusted position to the bottom-right corner
        panel.add(defaultButton);
        // Add panel to the frame
        add(panel);

        setVisible(true);
    }

    private void saveConfiguration() {
        try {
            var resultTicketBoxConfigs = ticketBoxConfigPanels.stream().filter(TicketBoxConfigPanel::isEnabled)
                    .map(TicketBoxConfigPanel::getTicketBoxConfig).toList();
            var resultEntranceConfigs = entranceConfigPanels.stream().filter(EntranceConfigPanel::isEnabled)
                    .map(EntranceConfigPanel::getEntranceConfig).toList();

            var resultReservedTicketBoxConfig = reservedTicketBoxConfigPanel.getTicketBoxConfig();

            var resultClientCapacity = Integer.parseInt(clientCapacityField.getText());
            var resultrestartClientCapacity = Integer.parseInt(restartClientCapacityField.getText());

            if(ValidateElementsDistance(resultTicketBoxConfigs, resultEntranceConfigs, resultReservedTicketBoxConfig))
            {
                var railwayHallConfig = new RailwayHallConfig(resultTicketBoxConfigs,
                        resultReservedTicketBoxConfig, resultEntranceConfigs, resultClientCapacity,
                        resultrestartClientCapacity);
                var railwayHallViewModel = new RailwayHallViewModel(new RailwayHall(railwayHallConfig));
                SwingUtilities.invokeLater(() -> new SimulationPage(railwayHallViewModel));
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void defaultConfiguration()
    {
        var strategy = new RandomTicketTimeProcessingStrategy(1,3);
        var tickeboxConfigs = new ArrayList<TicketBoxConfig>();
        tickeboxConfigs.add(new TicketBoxConfig(1,new Vector(50, 50), strategy));
        tickeboxConfigs.add(new TicketBoxConfig(2,new Vector(30, 500), strategy));
        tickeboxConfigs.add(new TicketBoxConfig(3,new Vector(700, 370), strategy));
        var resultTicketBoxConfigs = tickeboxConfigs;

        var entranceCConfigs = new ArrayList<EntranceConfig>();
        var idGenerator = new IntegerIdGenerator();
        var statuses = new ArrayList<ClientStatus>();
        statuses.add(new ClientStatus("Status 1", 1));
        statuses.add(new ClientStatus("Status 2", 2));
        statuses.add(new ClientStatus("Status 3", 3));
        var entranceStrategy1 = new ConstantClientGenerationStrategy(idGenerator, 2,
                new Vector(380, 30), 10, 2, statuses);
        var entranceStrategy2 = new ConstantClientGenerationStrategy(idGenerator, 2,
                new Vector(350, 350), 10, 2, statuses);
        var entranceStrategy3 = new ConstantClientGenerationStrategy(idGenerator, 2,
                new Vector(330, 700), 10, 2, statuses);
        entranceCConfigs.add(new EntranceConfig(1,new Vector(380, 30), entranceStrategy1));
        entranceCConfigs.add(new EntranceConfig(2,new Vector(350, 350), entranceStrategy2));
        entranceCConfigs.add(new EntranceConfig(3,new Vector(330, 700), entranceStrategy3));

        var resultEntranceConfigs = entranceCConfigs;

        var resultReservedTicketBoxConfig = new TicketBoxConfig(4, new Vector(750,600),
                new RandomTicketTimeProcessingStrategy(1,3));

        var resultClientCapacity = 50;
        var resultrestartClientCapacity = 40;


        if(ValidateElementsDistance(resultTicketBoxConfigs, resultEntranceConfigs, resultReservedTicketBoxConfig))
        {
            var railwayHallConfig = new RailwayHallConfig(resultTicketBoxConfigs,
                    resultReservedTicketBoxConfig, resultEntranceConfigs, resultClientCapacity,
                    resultrestartClientCapacity);
            var railwayHallViewModel = new RailwayHallViewModel(new RailwayHall(railwayHallConfig));
            SwingUtilities.invokeLater(() -> new SimulationPage(railwayHallViewModel));
        }
    }

    private boolean ValidateElementsDistance(List<TicketBoxConfig> ticketBoxConfigList,
                                             List<EntranceConfig> entranceConfigList,
                                             TicketBoxConfig reservedTicketBoxConfig) {

        int minimalDistance = 20; // Could be changed for UI developers

        // Compare ticket boxes coordinates
        if(!hasMinimumGap(ticketBoxConfigList, entranceConfigList, reservedTicketBoxConfig, minimalDistance)) {
            JOptionPane.showMessageDialog(this, "Invalid coordinates. It should be a " + minimalDistance + " between elements", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Additional validation could be added here (Ping Valera)

        return true;
    }

    public static boolean hasMinimumGap(List<TicketBoxConfig> ticketBoxConfigList,
                                        List<EntranceConfig> entranceConfigList,
                                        TicketBoxConfig reservedTicketBoxConfig,
                                        int minimalDistance) {
        // Check for gaps between TicketBoxConfig elements
        for (int i = 0; i < ticketBoxConfigList.size(); i++) {
            for (int j = i + 1; j < ticketBoxConfigList.size(); j++) {
                Vector position1 = ticketBoxConfigList.get(i).getPosition();
                Vector position2 = ticketBoxConfigList.get(j).getPosition();

                if (!hasMinimumGap(position1, position2, minimalDistance)) {
                    return false;
                }
            }
        }

        // Check for gaps between EntranceConfig elements
        for (int i = 0; i < entranceConfigList.size(); i++) {
            for (int j = i + 1; j < entranceConfigList.size(); j++) {
                Vector position1 = entranceConfigList.get(i).getPosition();
                Vector position2 = entranceConfigList.get(j).getPosition();

                if (!hasMinimumGap(position1, position2, minimalDistance)) {
                    return false;
                }
            }
        }

        // Check for gaps between TicketBoxConfig and EntranceConfig elements
        for (TicketBoxConfig ticketBox : ticketBoxConfigList) {
            for (EntranceConfig entrance : entranceConfigList) {
                Vector position1 = ticketBox.getPosition();
                Vector position2 = entrance.getPosition();

                if (!hasMinimumGap(position1, position2, minimalDistance)) {
                    return false;
                }
            }
        }

        // Check for gaps between Reserved TicketBox and TicketBoxConfig
        for (TicketBoxConfig ticketBox : ticketBoxConfigList) {
                Vector position1 = ticketBox.getPosition();
                Vector position2 = reservedTicketBoxConfig.getPosition();

                if (!hasMinimumGap(position1, position2, minimalDistance)) {
                    return false;
                }
        }

        // Check for gaps between Reserved TicketBox and EntranceConfig
        for (EntranceConfig entrance : entranceConfigList) {
            Vector position1 = entrance.getPosition();
            Vector position2 = reservedTicketBoxConfig.getPosition();

            if (!hasMinimumGap(position1, position2, minimalDistance)) {
                return false;
            }
        }

        return true;
    }

    private static boolean hasMinimumGap(Vector position1, Vector position2, int minimalDistance) {
        int deltaX = (int) Math.abs(position1.getX() - position2.getX());
        int deltaY = (int) Math.abs(position1.getY() - position2.getY());

        return deltaX >= minimalDistance && deltaY >= minimalDistance;
    }
}
