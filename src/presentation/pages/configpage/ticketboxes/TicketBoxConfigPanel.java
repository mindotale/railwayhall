package presentation.pages.configpage.ticketboxes;

import domain.common.Vector;
import domain.ticketboxes.TicketBoxConfig;
import domain.ticketboxes.TicketProcessingTimeStrategy;

import javax.swing.*;

public class TicketBoxConfigPanel extends JPanel {

    private JTextField xCoordinateField;
    private JTextField yCoordinateField;
    private JComboBox<String> strategyComboBox;
    private JCheckBox enableCheckBox;
    private int ticketBoxNumber;

    public TicketBoxConfigPanel(int ticketBoxNumber) {
        this.ticketBoxNumber = ticketBoxNumber;
        setLayout(null); // Use null layout

        // Ticket Box Number
        JLabel numberLabel = new JLabel("Ticket Box (" + ticketBoxNumber + ")");
        numberLabel.setBounds(10, 10, 150, 20);
        add(numberLabel);

        // Enable Checkbox
        enableCheckBox = new JCheckBox("Enable");
        enableCheckBox.setSelected(true);
        enableCheckBox.setBounds(160, 10, 80, 20);
        add(enableCheckBox);

        // X Coordinate
        JLabel xLabel = new JLabel("X coordinate:");
        xLabel.setBounds(10, 30, 150, 20);
        add(xLabel);

        xCoordinateField = new JTextField(5);
        xCoordinateField.setBounds(160, 30, 40, 20);
        add(xCoordinateField);

        // Y Coordinate
        JLabel yLabel = new JLabel("Y coordinate:");
        yLabel.setBounds(10, 50, 150, 20);
        add(yLabel);

        yCoordinateField = new JTextField(5);
        yCoordinateField.setBounds(160, 50, 40, 20);
        add(yCoordinateField);

        // Strategy
        JLabel strategyLabel = new JLabel("Strategy:");
        strategyLabel.setBounds(10, 70, 150, 20);
        add(strategyLabel);

        String[] strategies = {"Strategy1", "Strategy2", "Strategy3"}; // Replace with your actual strategies
        strategyComboBox = new JComboBox<>(strategies);
        strategyComboBox.setBounds(160, 70, 150, 20);
        add(strategyComboBox);
    }

    public boolean isEnabled() {
        return enableCheckBox.isSelected();
    }
    public TicketBoxConfig getTicketBoxConfig() {
        if(!isEnabled())
            return null;
        int xCoordinate = Integer.parseInt(xCoordinateField.getText());
        int yCoordinate = Integer.parseInt(yCoordinateField.getText());
        TicketProcessingTimeStrategy strategy = new TicketProcessingTimeStrategy() {
            @Override
            public int getTime() {
                return 0;
            }
        };

        return new TicketBoxConfig(ticketBoxNumber, new Vector(xCoordinate, yCoordinate), strategy);
    }
}
