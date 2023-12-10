package presentation.pages.simulationpage;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SimulationPage extends JFrame {

    public SimulationPage() {
        setTitle("Simulation Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(5, 5));

        add(createCapacityPanel(), BorderLayout.NORTH);
        add(createSimulationArea(), BorderLayout.CENTER);
        add(createRightPanel(), BorderLayout.EAST);
        add(createActionPanel(), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createCapacityPanel() {
        JPanel capacityPanel = new JPanel();
        capacityPanel.setBorder(new TitledBorder("Capacity"));
        capacityPanel.add(new JLabel("16/50"));
        return capacityPanel;
    }

    private JPanel createSimulationArea() {
        JPanel simulationArea = new JPanel();
        simulationArea.setBorder(new TitledBorder("Simulation Area"));
        simulationArea.setPreferredSize(new Dimension(600, 600));
        return simulationArea;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        rightPanel.setBorder(new TitledBorder("Controls"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Column index
        gbc.gridy = 0; // Row index
        gbc.gridwidth = 2; // Component occupies two columns
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 2, 2, 2); // Margin for aesthetics

        // Add button panel
        JPanel buttonsPanel = createButtonsPanel();
        rightPanel.add(buttonsPanel, gbc);

        // Move to the next row for the items panel
        gbc.gridy++;
        JPanel itemsPanel = createItemsPanel();
        rightPanel.add(itemsPanel, gbc);

        // Move to the next row for the details panel
        gbc.gridy++;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0; // Assign remaining space to details area
        gbc.fill = GridBagConstraints.BOTH; // Fill both horizontally and vertically
        JScrollPane detailsScrollPane = createDetailsPanel();
        rightPanel.add(detailsScrollPane, gbc);

        return rightPanel;
    }

    private GridBagConstraints createGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        return gbc;
    }

    private JPanel createButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        buttonsPanel.add(new JButton("General"));
        buttonsPanel.add(new JButton("Ticket Boxes"));
        buttonsPanel.add(new JButton("Entrances"));
        buttonsPanel.add(new JButton("Clients"));
        buttonsPanel.add(new JButton("Logs"));
        return buttonsPanel;
    }

    private JPanel createItemsPanel() {
        JPanel itemsPanel = new JPanel(new BorderLayout());
        itemsPanel.setBorder(new TitledBorder("Items"));
        JList<String> itemList = new JList<>(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"});
        itemsPanel.add(new JScrollPane(itemList), BorderLayout.CENTER);
        return itemsPanel;
    }

    private JScrollPane createDetailsPanel() {
        JTextArea detailsArea = new JTextArea();
        JScrollPane detailsScrollPane = new JScrollPane(detailsArea);
        detailsScrollPane.setBorder(new TitledBorder("Details"));
        return detailsScrollPane;
    }

    private JPanel createActionPanel() {
        JPanel actionPanel = new JPanel();
        actionPanel.add(new JButton("Start / Stop"));
        actionPanel.add(new JButton("Cancel"));
        actionPanel.add(new JTextField("Ticket Box ID", 10));
        actionPanel.add(new JButton("Open / Close"));
        return actionPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimulationPage::new);
    }
}
