package presentation.pages.simulationpage;

import presentation.viewmodels.RailwayHallViewModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SimulationPage extends JFrame {
    private final RailwayHallViewModel railwayHallViewModel;
    private boolean simulationStarted;
    private Timer simulation;

    public SimulationPage(RailwayHallViewModel railwayHallViewModel) {
        setTitle("Simulation Page");
        simulationStarted = false;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(5, 5));

        add(createCapacityPanel(), BorderLayout.NORTH);
        add(createSimulationArea(), BorderLayout.CENTER);
        add(createRightPanel(), BorderLayout.EAST);
        add(createActionPanel(), BorderLayout.SOUTH);

        this.railwayHallViewModel = railwayHallViewModel;

        pack();
        setLocationRelativeTo(null);

        addEntrances();

        createTicketBoxes();

        setVisible(true);
    }

    private void createTicketBoxes() {
        var boxes = railwayHallViewModel.getTicketBoxes();
        for (var box : boxes) {
            int count = box.getClientsCount();
            simulationArea.addTicketBoxFigure(box.getPosition().getX(), box.getPosition().getY(), box.getId(), box.isOpen(), count);
        }
        setVisible(true);
    }

    private void addEntrances() {
        var entrances = railwayHallViewModel.getEntrances();
        for(var entrance : entrances) {
            List<String> clientIds = new ArrayList<>();
            var clients = entrance.getClients();
            for (presentation.viewmodels.abstractions.ClientViewModel client : clients) {
                clientIds.add(client.getId() + "");
            }
            simulationArea.addEntranceFigure(entrance.getPosition().getX(), entrance.getPosition().getY(),
                    entrance.getId(), entrance.isOpen(), clientIds);
        }
    }

    private JPanel createCapacityPanel() {
        JPanel capacityPanel = new JPanel();
        capacityPanel.setBorder(new TitledBorder("Capacity"));
        capacityPanel.add(new JLabel("16/50"));
        return capacityPanel;
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

    private JPanel itemsPanel; // Declare itemsPanel as a field

    private JPanel createItemsPanel() {
        itemsPanel = new JPanel(new BorderLayout());
        itemsPanel.setBorder(new TitledBorder("Items"));
        // Initial content
        JList<String> itemList = new JList<>(new String[]{});
        itemsPanel.add(new JScrollPane(itemList), BorderLayout.CENTER);
        return itemsPanel;
    }

    private JPanel createButtonsPanel() {
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        String[] buttonLabels = {"General", "Ticket Boxes", "Entrances", "Clients", "Logs"};

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setActionCommand(label);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedButtonActionCommand = e.getActionCommand();
                    updateItemsPanel(selectedButtonActionCommand);
                }
            });
            buttonsPanel.add(button);
        }
        return buttonsPanel;
    }

    private void updateItemsPanel(String actionCommand) {
        // You can define different lists for each button
        var generalItems = new ArrayList<String>();
        generalItems.add("Client Capacity: " + railwayHallViewModel.getClientCapacity() + "");
        generalItems.add("Restart Capacity: " + railwayHallViewModel.getRestartCapacity() + "");

        var ticketBoxItems = new ArrayList<String>();
        var boxes = railwayHallViewModel.getTicketBoxes();
        for (var box : boxes){
            ticketBoxItems.add("Ticket Box " + box.getId());
        }

        var entranceItems = new ArrayList<String>();
        var entrances = railwayHallViewModel.getEntrances();
        for (var entrance : entrances){
            entranceItems.add("Entrance " + entrance.getId());
        }

        var clientItems = new ArrayList<String>();

        var clients = railwayHallViewModel.getClients();
        for (var client : clients){
            clientItems.add("Client " + client.getId() + " with priority " + client.getPriority());
        }
        var logItems = new ArrayList<String>();

        ArrayList<String> itemsToDisplay = switch (actionCommand) {
            case "General" -> generalItems;
            case "Ticket Boxes" -> ticketBoxItems;
            case "Entrances" -> entranceItems;
            case "Clients" -> clientItems;
            case "Logs" -> logItems;
            default -> new ArrayList<String>();
        };

        // Update the JList with the new items
        JList<String> itemList = new JList<>(itemsToDisplay.toArray(new String[0]));
        itemsPanel.removeAll(); // Remove current content
        itemsPanel.add(new JScrollPane(itemList), BorderLayout.CENTER);
        itemsPanel.revalidate();
        itemsPanel.repaint();
    }
    private JScrollPane createDetailsPanel() {
        JTextArea detailsArea = new JTextArea();
        JScrollPane detailsScrollPane = new JScrollPane(detailsArea);
        detailsScrollPane.setBorder(new TitledBorder("Details"));
        return detailsScrollPane;
    }

    private JPanel createActionPanel() {
        JPanel actionPanel = new JPanel();
        JButton startButton = new JButton("Start / Stop");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(simulation != null && simulationStarted) {
                    simulation.stop();
                    simulationStarted = false;
                } else if (simulation != null && !simulationStarted) {
                    simulation.start();
                    simulationStarted = true;
                }
                else {
                    simulation = new Timer(300, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            railwayHallViewModel.tick();

                            createTicketBoxes();

                            var clients = railwayHallViewModel.getClients();
                            for (String clientId : new ArrayList<>(simulationArea.getClientFigureMap().keySet())) {
                                boolean clientExists = false;
                                for (var client : clients) {
                                    if (client.getId() == Integer.parseInt(clientId)) {
                                        clientExists = true;
                                        break;
                                    }
                                }

                                // Якщо клієнта немає у списку 'clients', видалити його з SimulationArea
                                if (!clientExists) {
                                    simulationArea.removeClientFigure(clientId);
                                }
                            }

                            for (var client : clients) {
                                if(!simulationArea.isClientOnPage(client.getId() + "")){
                                    simulationArea.addClientFigure(client.getPosition().getX(), client.getPosition().getY(), client.getId()+"");
                                }
                                simulationArea.animateClientMovement(client.getId() +"", client.getPosition().getX(), client.getPosition().getY()); // Нові координати X та Y
                            }
                        }
                    });

                    simulation.start();
                    simulationStarted = true;
                }
            }
        });
        actionPanel.add(startButton);
        actionPanel.add(new JButton("Cancel"));
        actionPanel.add(new JTextField("Ticket Box ID", 10));
        actionPanel.add(new JButton("Open / Close"));
        return actionPanel;
    }
    private SimulationArea simulationArea;

    private JPanel createSimulationArea() {
        simulationArea = new SimulationArea();
        simulationArea.setBorder(new TitledBorder("Simulation Area"));
        simulationArea.setPreferredSize(new Dimension(600, 600));
        return simulationArea;
    }
}
