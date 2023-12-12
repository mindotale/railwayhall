package presentation.pages.simulationpage;

import presentation.viewmodels.RailwayHallViewModel;
import presentation.viewmodels.abstractions.ClientViewModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimulationPage extends JFrame {
    private final RailwayHallViewModel railwayHallViewModel;
    private boolean simulationStarted;
    private Timer simulation;
    private String currentPanel = "General";
    private String currentSelectedItem = "General";
    private int currentSelectedIndex = 0;
    JLabel capacity;

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
        capacity.setText(railwayHallViewModel.getClientCapacity() + "/" + railwayHallViewModel.getRestartCapacity());
        pack();
        setLocationRelativeTo(null);

        addEntrances();
        addTicketBoxes();
        addReservedTicketBoxes();


        setVisible(true);
    }

    private void addReservedTicketBoxes(){
        var box = railwayHallViewModel.getReservedTicketBox();
        System.out.println("reserved tb" + box.isOpen());
        simulationArea.addTicketBoxFigure(box.getPosition().getX(), box.getPosition().getY(), box.getId(), box.isOpen(), box.getClientsCount(),true);
        setVisible(true);
    }

    private void addTicketBoxes() {
        var boxes = railwayHallViewModel.getTicketBoxes();
        for (var box : boxes) {
            int count = box.getClientsCount();
            simulationArea.addTicketBoxFigure(box.getPosition().getX(), box.getPosition().getY(), box.getId(), box.isOpen(), count, false);
        }
        setVisible(true);
    }

    private void addEntrances() {
        var entrances = railwayHallViewModel.getEntrances();
        for (var entrance : entrances) {
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
        capacity = new JLabel();
        capacityPanel.add(capacity);
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
        currentPanel = actionCommand;
        // You can define different lists for each button
        var generalItems = new ArrayList<String>();
        generalItems.add("Client Capacity: " + railwayHallViewModel.getClientCapacity() + "");
        generalItems.add("Restart Capacity: " + railwayHallViewModel.getRestartCapacity() + "");

        var ticketBoxItems = new ArrayList<String>();
        var boxes = railwayHallViewModel.getTicketBoxes();
        for (var box : boxes){
            ticketBoxItems.add("Ticket Box - " + box.getId());
        }
        var rbox = railwayHallViewModel.getReservedTicketBox();
        ticketBoxItems.add("Reserved Ticket Box - " + rbox.getId());



        var entranceItems = new ArrayList<String>();
        var entrances = railwayHallViewModel.getEntrances();
        for (var entrance : entrances){
            entranceItems.add("Entrance - " + entrance.getId());
        }

        var clientItems = new ArrayList<String>();

        var clients = railwayHallViewModel.getClients();
        for (var client : clients){
            clientItems.add("Client - " + client.getId() + " with priority " + client.getPriority());
        }
        var logItems = new ArrayList<String>();
        var logs = railwayHallViewModel.getTotalRecords();
        for(var log : logs) {
            logItems.add("Log - " + log.getClientId() + " client on " + log.getTicketBoxId() + "ticket box");
        }
        railwayHallViewModel.getTotalRecords().stream().map(r -> Integer.toString(r.getClientId())).toList();

        List<String> itemsToDisplay = switch (actionCommand) {
            case "General" -> generalItems;
            case "Ticket Boxes" -> ticketBoxItems;
            case "Entrances" -> entranceItems;
            case "Clients" -> clientItems;
            case "Logs" -> logItems;
            default -> new ArrayList<String>();
        };

        // Update the JList with the new items
        JList<String> itemList = new JList<>(itemsToDisplay.toArray(new String[0]));

        itemList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedItem = itemList.getSelectedValue();
                updateDetailsPanel(selectedItem, itemList.getSelectedIndex());
                // Do something with the selected item
                System.out.println("Selected item: " + selectedItem);
            }
        });

        itemsPanel.removeAll(); // Remove current content
        itemsPanel.add(new JScrollPane(itemList), BorderLayout.CENTER);
        itemsPanel.revalidate();
        itemsPanel.repaint();
    }

    private JTextArea detailsArea;
    private JScrollPane createDetailsPanel() {
        detailsArea = new JTextArea();
        JScrollPane detailsScrollPane = new JScrollPane(detailsArea);
        detailsScrollPane.setBorder(new TitledBorder("Details"));
        return detailsScrollPane;
    }
    private void updateDetailsPanel(String selectedItem, int selectedIndex) {
        currentSelectedItem = selectedItem;
        currentSelectedIndex = selectedIndex + 1;
        detailsArea.selectAll();
        detailsArea.replaceSelection("");

        switch (selectedItem.split("-")[0].strip()) {
            case "Ticket Box" -> {
                var ticketBox = railwayHallViewModel.getTicketBoxes().get(selectedIndex);
                detailsArea.append("Ticket Box - " + ticketBox.getId() + "\n");
                detailsArea.append("Position X - " + ticketBox.getPosition().getX() + "\n");
                detailsArea.append("Position Y - " + ticketBox.getPosition().getY() + "\n");
                detailsArea.append("Is open - " + ticketBox.isOpen() + "\n");
                detailsArea.append("Clients count - " + ticketBox.getClientsCount() + "\n");
                detailsArea.append("Clients in queue - " + Arrays.toString(ticketBox.getClients().stream().map(ClientViewModel::getId).toArray()) + "\n");
                if(ticketBox.getCurrentClient() != null)
                    detailsArea.append("Current client - " + ticketBox.getCurrentClient().getId() + "\n");
            }
            case "Reserved Ticket Box" -> {
                var ticketBox = railwayHallViewModel.getReservedTicketBox();
                detailsArea.append("Reserved Ticket Box - " + ticketBox.getId() + "\n");
                detailsArea.append("Position X - " + ticketBox.getPosition().getX() + "\n");
                detailsArea.append("Position Y - " + ticketBox.getPosition().getY() + "\n");
                detailsArea.append("Is open - " + ticketBox.isOpen() + "\n");
                detailsArea.append("Clients count - " + ticketBox.getClientsCount() + "\n");
                detailsArea.append("Clients in queue - " + Arrays.toString(ticketBox.getClients().stream().map(ClientViewModel::getId).toArray()) + "\n");
                if(ticketBox.getCurrentClient() != null)
                    detailsArea.append("Current client - " + ticketBox.getCurrentClient().getId() + "\n");
            }
            case "Entrance" -> {
                var entrance = railwayHallViewModel.getEntrances().get(selectedIndex);
                detailsArea.append("Entrance - " + entrance.getId() + "\n");
                detailsArea.append("Position X - " + entrance.getPosition().getX() + "\n");
                detailsArea.append("Position Y - " + entrance.getPosition().getY() + "\n");
                detailsArea.append("Is open - " + entrance.isOpen() + "\n");
                detailsArea.append("Clients count - " + entrance.getClientsCount() + "\n");
                detailsArea.append("Clients in queue - " + Arrays.toString(entrance.getClients().stream().map(ClientViewModel::getId).toArray()) + "\n");
            }
            case "Client" -> {
                var client = railwayHallViewModel.getClients().get(selectedIndex);
                detailsArea.append("Client - " + client.getId() + "\n");
                detailsArea.append("Position X - " + client.getPosition().getX() + "\n");
                detailsArea.append("Position Y - " + client.getPosition().getY() + "\n");
                detailsArea.append("Priority - " + client.getPriority() + "\n");
                detailsArea.append("Tickets count - " + client.getTicketsCount() + "\n");
                detailsArea.append("Statuses - " + Arrays.toString(client.getStatuses().toArray()));
            }
            case "Log" -> {
                var log = railwayHallViewModel.getTotalRecords().get(selectedIndex);
                detailsArea.append("Client - " + log.getClientId() + "\n");
                detailsArea.append("Ticket box - " + log.getTicketBoxId() + "\n");
                detailsArea.append("Start ticks - " + log.getStartTicks() + "\n");
                detailsArea.append("End ticks - " + log.getEndTicks() + "\n");
            }
        }
    }

    private JPanel createActionPanel() {
        JPanel actionPanel = new JPanel();
        JButton startButton = new JButton("Start / Stop");
        startButton.addActionListener(e -> {
            System.out.println(simulation);
            System.out.println(simulationStarted);
            if (simulation != null && simulationStarted) {
                simulation.stop();
                simulationStarted = false;
            } else if (simulation != null && !simulationStarted) {
                simulation.start();
                simulationStarted = true;
            } else {
                simulation = new Timer(150, e1 -> {
                    railwayHallViewModel.tick();

                    var boxes = railwayHallViewModel.getTicketBoxes();
                    for(var box: boxes){
                        simulationArea.removeTicketBoxFigure(box.getId());
                    }
                    var rbox = railwayHallViewModel.getReservedTicketBox();
                    simulationArea.removeTicketBoxFigure(rbox.getId());

                    addTicketBoxes();
                    addReservedTicketBoxes();
                    addEntrances();

                    var clients = railwayHallViewModel.getClients();
                    for (String clientId : new ArrayList<>(simulationArea.getClientFigureMap().keySet())) {
                        boolean clientExists = false;
                        for (var client : clients) {
                            if (client.getId() == Integer.parseInt(clientId)) {
                                clientExists = true;
                                break;
                            }
                        }

                        if (!clientExists) {
                            simulationArea.removeClientFigure(clientId);
                        }
                    }
                    updateItemsPanel(currentPanel);
                    updateDetailsPanel(currentSelectedItem, Integer.min(clients.size(), currentSelectedIndex) - 1);

                    simulationArea.animateClientsMovement(clients);
                });

                simulation.start();
                simulationStarted = true;
            }
        });

        //actionPanel.add(new JButton("Cancel"));
        JTextField ticketboxid = new JTextField("Ticket Box ID", 10);
        // create and add listener to open/close button
        JButton openCloseBut = new JButton("Open / Close");
        openCloseBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                var deskId = Integer.parseInt(ticketboxid.getText()) -1;
                var tickBox = railwayHallViewModel.getTicketBoxes().get(deskId);
                System.out.println(tickBox.isOpen());
                System.out.println(deskId);

                if (tickBox.isOpen())
                    railwayHallViewModel.closeTicketBox(tickBox.getId());
                else
                    railwayHallViewModel.openTicketBox(tickBox.getId());
            }
        });

        JButton cancelButt = new JButton("Cancel");
        cancelButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        actionPanel.add(startButton);
        actionPanel.add(ticketboxid);
        actionPanel.add(openCloseBut);
        actionPanel.add(cancelButt);
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
