package presentation.pages.configpage.ticketboxes;

import domain.clients.ClientStatus;
import domain.clients.ConstantClientGenerationStrategy;
import domain.common.IdGenerator;
import domain.common.Vector;
import domain.entrances.EntranceConfig;

import javax.swing.*;
import java.util.Collection;
import java.util.Iterator;

public class EntranceConfigPanel extends JPanel {

    private JTextField xCoordinateField;
    private JTextField yCoordinateField;
    private JComboBox<String> strategyComboBox;
    private JCheckBox enableCheckBox;
    private int entranceNumber;
    private final IdGenerator<Integer> idGenerator;

    public EntranceConfigPanel(int entranceNumber, IdGenerator<Integer> idGenerator) {
        this.entranceNumber = entranceNumber;
        this.idGenerator = idGenerator;
        setLayout(null); // Use null layout

        // Ticket Box Number
        JLabel numberLabel = new JLabel("Entrance (" + entranceNumber + ")");
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

    public EntranceConfig getEntranceConfig() {
        int xCoordinate = Integer.parseInt(xCoordinateField.getText());
        int yCoordinate = Integer.parseInt(yCoordinateField.getText());

        return new EntranceConfig(entranceNumber, new Vector(xCoordinate, yCoordinate),
                new ConstantClientGenerationStrategy(idGenerator, 1,
                        new Vector(xCoordinate, yCoordinate), 1, 1, new Collection<ClientStatus>() {
                    @Override
                    public int size() {
                        return 0;
                    }

                    @Override
                    public boolean isEmpty() {
                        return false;
                    }

                    @Override
                    public boolean contains(Object o) {
                        return false;
                    }

                    @Override
                    public Iterator<ClientStatus> iterator() {
                        return null;
                    }

                    @Override
                    public Object[] toArray() {
                        return new Object[0];
                    }

                    @Override
                    public <T> T[] toArray(T[] a) {
                        return null;
                    }

                    @Override
                    public boolean add(ClientStatus clientStatus) {
                        return false;
                    }

                    @Override
                    public boolean remove(Object o) {
                        return false;
                    }

                    @Override
                    public boolean containsAll(Collection<?> c) {
                        return false;
                    }

                    @Override
                    public boolean addAll(Collection<? extends ClientStatus> c) {
                        return false;
                    }

                    @Override
                    public boolean removeAll(Collection<?> c) {
                        return false;
                    }

                    @Override
                    public boolean retainAll(Collection<?> c) {
                        return false;
                    }

                    @Override
                    public void clear() {

                    }
                }));
    }
}
