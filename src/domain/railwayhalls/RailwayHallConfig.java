package domain.railwayhalls;

import domain.entrances.EntranceConfig;
import domain.ticketboxes.TicketBoxConfig;

import java.util.Collection;
import java.util.List;

public final class RailwayHallConfig {
    private final List<TicketBoxConfig> ticketBoxes;
    private final TicketBoxConfig reservedTicketBox;
    private final List<EntranceConfig> entrances;
    private final int clientCapacity;
    private final int restartClientCapacity;

    public RailwayHallConfig(Collection<TicketBoxConfig> ticketBoxes, TicketBoxConfig reservedTicketBox, Collection<EntranceConfig> entrances, int clientCapacity, int restartClientCapacity) {
        this.ticketBoxes = List.copyOf(ticketBoxes);
        this.reservedTicketBox = reservedTicketBox;
        this.entrances = List.copyOf(entrances);
        this.clientCapacity = clientCapacity;
        this.restartClientCapacity = restartClientCapacity;
    }

    public List<TicketBoxConfig> getTicketBoxes() {
        return ticketBoxes;
    }

    public TicketBoxConfig getReservedTicketBox() {
        return reservedTicketBox;
    }

    public List<EntranceConfig> getEntrances() {
        return entrances;
    }

    public int getClientCapacity() {
        return clientCapacity;
    }

    public int getRestartClientCapacity() {
        return restartClientCapacity;
    }
}
