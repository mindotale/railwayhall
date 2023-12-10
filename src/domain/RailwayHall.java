package domain;

import java.util.*;
import java.util.stream.Collectors;
import domain.Vector;
public class RailwayHall implements Ticker {
    private final Map<Integer, TicketBox> ticketBoxes;
    private final TicketBox reservedTicketBox;
    private final Map<Integer, Entrance> entrances;
    private final Map<Integer, Client> clients;
    private final ClientProcessingLog log;
    private final int clientCapacity;
    private final int restartClientCapacity;
    private int ticks;
    private boolean isOpen;

    public RailwayHall(RailwayHallConfig config) {
        this.ticketBoxes = config.getTicketBoxes().stream()
                .collect(Collectors.toMap(TicketBoxConfig::getId, this::createTicketBox, (existing, replacement) -> existing));
        this.reservedTicketBox = createTicketBox(config.getReservedTicketBox());
        this.entrances = config.getEntrances().stream()
                .collect(Collectors.toMap(EntranceConfig::getId, this::createEntrance, (existing, replacement) -> existing));
        if (config.getClientCapacity() < 0) {
            throw new IllegalArgumentException("Client capacity must be non-negative.");
        }
        if (config.getRestartClientCapacity() < 0) {
            throw new IllegalArgumentException("Restart client capacity must be non-negative.");
        }
        if (config.getRestartClientCapacity() > config.getClientCapacity()) {
            throw new IllegalArgumentException("Restart client capacity must be less than or equal to client capacity.");
        }
        this.clientCapacity = config.getClientCapacity();
        this.restartClientCapacity = config.getRestartClientCapacity();
        this.clients = new HashMap<>();
        this.log = new ClientProcessingLog();
        this.ticks = 0;
    }

    private TicketBox createTicketBox(TicketBoxConfig config) {
        return new TicketBox(config.getId(), config.getPosition(), config.getTicketProcessingTimeStrategy());
    }

    private Entrance createEntrance(EntranceConfig config) {
        return new Entrance(config.getId(), config.getPosition(), config.getClientGenerationStrategy());
    }

    public int getClientCapacity() { return clientCapacity; }

    public int getRestartClientCapacity() { return restartClientCapacity; }

    public int getClientCount() { return clients.size(); }

    public int getTicketBoxClientCount() {
        var clientCount = ticketBoxes.values().stream().mapToInt(TicketBox::getQueueSize).sum();
        clientCount += reservedTicketBox.getQueueSize();
        return clientCount;
    }

    public int getTotalClientCount() {
        var clientCount = getClientCount();
        clientCount += getTicketBoxClientCount();
        return clientCount;
    }

    public boolean isOpen() { return isOpen; }

    @Override
    public int getTicks() {
        return ticks;
    }

    @Override
    public void tick() {
        tickTicketBoxes();
        tickEntrances();
        moveClientsToBestTicketBoxes();
        checkCapacity();
        ticks++;
    }

    public void enableTicketBox(int id) {
        TicketBox ticketBox = ticketBoxes.get(id);
        if(ticketBox == null) {
            throw new IllegalArgumentException("Ticket box with ID " + id + " not found.");
        }
        ticketBox.enable();
    }

    public void disableTicketBox(int id) {
        TicketBox ticketBox = ticketBoxes.get(id);
        if(ticketBox == null) {
            throw new IllegalArgumentException("Ticket box with ID " + id + " not found.");
        }
        ticketBox.disable();
        moveClientsToReservedTicketBox(ticketBox.dequeueAll());
    }

    private void moveClientsToReservedTicketBox(Collection<Client> clients) {
        for (Client client : clients) {
            client.moveToInstantly(reservedTicketBox.getPosition());
            reservedTicketBox.enqueue(client);
        }
    }

    private void moveClientsToBestTicketBoxes() {
        clients.forEach((id, client) -> moveClientToBestTicketBox(client));
    }

    private void moveClientToBestTicketBox(Client client) {
        var bestTicketBox = getBestTicketBox(client);
        if(bestTicketBox == null) {
            return;
        }
        client.moveTo(bestTicketBox.getPosition());
        if(client.getPosition() == bestTicketBox.getPosition()) {
            bestTicketBox.enqueue(client);
        }
    }

    private TicketBox getBestTicketBox(Client client) {
        return ticketBoxes.values().stream()
                .filter(TicketBox::isEnabled)
                .min(Comparator.comparingInt(TicketBox::getQueueSize)
                        .thenComparingDouble(tb -> client.getPosition().distanceTo(tb.getPosition())))
                .orElse(null);
    }

    private void tickEntrances() {
        entrances.values().forEach(entrance -> {
            addClients(entrance.letClientsIn());
            entrance.tick();
        });
    }

    private void addClients(Collection<Client> clients) {
        clients.forEach(client -> this.clients.put(client.getId(), client));
    }

    private void tickTicketBoxes() {
        ticketBoxes.values().forEach(TicketBox::tick);
    }

    private void checkCapacity() {
        if(isOpen) {
            closeIfNotEnoughCapacity();
            return;
        }

        openIfEnoughCapacity();
    }

    private void closeIfNotEnoughCapacity() {
        var totalClients = getTotalClientCount();
        if(totalClients > clientCapacity) {
            close();
        }
    }

    private void openIfEnoughCapacity() {
        var totalClients = getTotalClientCount();
        if(totalClients < restartClientCapacity) {
            open();
        }
    }

    private void close() {
        isOpen = false;
        entrances.values().forEach(Entrance::close);
    }

    private void open() {
        isOpen = true;
        entrances.values().forEach(Entrance::open);
    }

    public List<ClientProcessingRecord> getRecords() {
        return log.getRecords();
    }

    public void clearRecords() {
        log.clearRecords();
    }
}