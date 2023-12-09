import java.util.List;

public class TicketBox implements Ticker {
    private final int id;
    private final Vector position;
    private final TicketProcessingTimeStrategy ticketProcessingTimeStrategy;
    private final ClientQueue queue;
    private boolean isEnabled;
    private int ticks;
    private int currentClientId;
    private int remainingProcessingTicks;

    public TicketBox(int id, Vector position, TicketProcessingTimeStrategy ticketProcessingTimeStrategy) {
        if (id < 0) {
            throw new IllegalArgumentException("CashDesk ID must be non-negative.");
        }
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null.");
        }

        this.id = id;
        this.position = position;
        this.ticketProcessingTimeStrategy = ticketProcessingTimeStrategy;
        this.isEnabled = true;
        this.ticks = 0;
        this.queue = new ClientQueue();
    }

    public int getId() {
        return id;
    }

    public Vector getPosition() {
        return position;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public int getTicks() {
        return ticks;
    }

    public int getQueueSize() { return queue.getSize(); }

    public void enable() {
        isEnabled = true;
    }

    public void disable() {
        isEnabled = false;
    }

    @Override
    public void tick() {
        if(!isEnabled) {
            ticks++;
            return;
        }

        processCurrentClient();
        ticks++;
    }

    public void enqueue(Client client) {
        queue.enqueue(client);
    }

    public void enqueueAll(Client[] clients) {
        queue.enqueueAll(clients);
    }

    public List<Client> dequeueAll() {
        return queue.dequeueAll();
    }

    private void processCurrentClient()
    {
        Client currentClient = queue.peek();
        if(currentClient == null) {
            currentClientId = 0;
            remainingProcessingTicks = 0;
            return;
        }
        if(currentClient.getTickets() == 0) {
            queue.dequeue();
            processCurrentClient();
        }
        if(currentClientId != currentClient.getId()) {
            currentClientId = currentClient.getId();
            remainingProcessingTicks = ticketProcessingTimeStrategy.getTime();
        }
        remainingProcessingTicks--;
        if(remainingProcessingTicks > 0) {
             return;
        }
        currentClient.processTicket();
        if(currentClient.getTickets() == 0) {
            queue.dequeue();
            currentClientId = 0;
            remainingProcessingTicks = 0;
        }
        remainingProcessingTicks = ticketProcessingTimeStrategy.getTime();
    }
}
