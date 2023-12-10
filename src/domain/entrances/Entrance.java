package domain.entrances;

import domain.clients.Client;
import domain.clients.ClientGenerationStrategy;
import domain.common.Ticker;
import domain.common.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Entrance implements Ticker {
    private final ClientGenerationStrategy clientGenerationStrategy;
    private final int id;
    private final Vector position;
    private final ArrayList<Client> clients;
    private boolean isOpen;
    private int ticks;

    public Entrance(int id, Vector position, ClientGenerationStrategy clientGenerationStrategy) {
        this.id = id;
        this.position = position;
        this.clientGenerationStrategy = clientGenerationStrategy;
        this.clients = new ArrayList<>();
        this.isOpen = true;
        this.ticks = 0;
    }

    @Override
    public int getTicks() {
        return ticks;
    }

    @Override
    public void tick() {
        if (isOpen) {
            generateClients();
        }
        ticks++;
    }

    public void open() {
        isOpen = true;
    }

    public void close() {
        isOpen = false;
    }

    public List<Client> getClients() {
        return Collections.unmodifiableList(clients);
    }

    public int getId() {
        return id;
    }

    public Vector getPosition() {
        return position;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public List<Client> letClientsIn() {
        List<Client> newClients = new ArrayList<>(clients);
        clients.clear();
        return newClients;
    }

    private void generateClients() {
        clientGenerationStrategy.tick();
        List<Client> newClients = clientGenerationStrategy.popNext();
        clients.addAll(newClients);
    }
}
