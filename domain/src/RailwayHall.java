import java.util.*;
import java.util.stream.Collectors;

public class RailwayHall implements Ticker {
    private final Map<Integer, TicketBox> ticketBoxes;
    private final TicketBox reservedTicketBox;
    private final Map<Integer, Entrance> entrances;
    private final Map<Integer, Client> clients;
    private final ClientProcessingLog log;
    private int ticks;

    public RailwayHall(RailwayHallConfig config) {
        this.ticketBoxes = config.getTicketBoxes().stream()
                .collect(Collectors.toMap(TicketBoxConfig::getId, this::createTicketBox, (existing, replacement) -> existing));
        this.reservedTicketBox = createTicketBox(config.getReservedTicketBox());
        this.entrances = config.getEntrances().stream()
                .collect(Collectors.toMap(EntranceConfig::getId, this::createEntrance, (existing, replacement) -> existing));
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

    @Override
    public int getTicks() {
        return ticks;
    }

    @Override
    public void tick() {
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

    private void moveClientsToReservedTicketBox(Collection<Client> clients)
    {
        for (Client client : clients) {
            client.moveToInstantly(reservedTicketBox.getPosition());
            reservedTicketBox.enqueue(client);
        }
    }

    public List<ClientProcessingRecord> getRecords() {
        return log.getRecords();
    }

    public void clearRecords() {
        log.clearRecords();
    }
}
