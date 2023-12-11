package domain.ticketboxes;


import domain.clients.Client;
import domain.clients.ClientProcessingLog;
import domain.clients.ClientProcessingRecord;
import domain.clients.ClientQueue;
import domain.common.Ticker;
import domain.common.Vector;

import java.util.List;

public class TicketBox implements Ticker {
    private final int id;
    private final Vector position;
    private final TicketProcessingTimeStrategy ticketProcessingTimeStrategy;
    private final ClientQueue queue;
    private boolean isEnabled;
    private int ticks;
    private Client currentClient;
    private int startTicks;
    private int endTicks;
    private int processingTicks;
    private final ClientProcessingLog log;

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
        this.log = new ClientProcessingLog();
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
    public List<ClientProcessingRecord> getRecords() {
        return log.getRecords();
    }
    public void clearRecords() {
        log.clearRecords();
    }

    private void processCurrentClient()
    {
        if(currentClient == null) {
            setCurrentClient();
        }

        if(currentClient == null) {
            return;
        }

        if(currentClient.getTickets() == 0){
            resetCurrentClient();
            return;
        }

        if(ticks < endTicks) {
            return;
        }

        currentClient.processTicket();
        setProcessingTicks();
    }

    private void setCurrentClient() {
        currentClient = queue.dequeue();
        if(currentClient == null) {
            resetProcessingTicks();
        }

        setProcessingTicks();
    }

    private void resetCurrentClient() {
        currentClient = null;
        resetProcessingTicks();
    }

    private void setProcessingTicks() {
        startTicks = ticks;
        processingTicks = ticketProcessingTimeStrategy.getTime();
        endTicks = ticks + processingTicks;
    }

    private  void resetProcessingTicks() {
        startTicks = 0;
        processingTicks = 0;
        endTicks = 0;
    }
}
