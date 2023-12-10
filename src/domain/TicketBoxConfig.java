package domain;

public final class TicketBoxConfig {
    private final int id;
    private final Vector position;
    private final TicketProcessingTimeStrategy ticketProcessingTimeStrategy;

    public TicketBoxConfig(int id, Vector position, TicketProcessingTimeStrategy ticketProcessingTimeStrategy) {
        this.id = id;
        this.position = position;
        this.ticketProcessingTimeStrategy = ticketProcessingTimeStrategy;
    }

    public int getId() {
        return id;
    }

    public Vector getPosition() {
        return position;
    }

    public TicketProcessingTimeStrategy getTicketProcessingTimeStrategy() {
        return ticketProcessingTimeStrategy;
    }
}
