package clients;

import common.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConstantClientGenerationStrategy implements ClientGenerationStrategy {
    private final int generationTicks;
    private final Vector position;
    private final double velocity;
    private final int tickets;
    private final Collection<ClientStatus> statuses;
    private final List<Client> clients;

    private int nextGenerationTicks;
    private int ticks;
    private int idCounter;


    public ConstantClientGenerationStrategy(int initialId, int generationTicks, Vector position, double velocity, int tickets, Collection<ClientStatus> statuses) {
        if (generationTicks <= 0) {
            throw new IllegalArgumentException("Invalid generation time.");
        }

        if (tickets <= 0) {
            throw new IllegalArgumentException("Tickets should be a positive value.");
        }

        if (statuses.isEmpty()) {
            throw new IllegalArgumentException("Statuses cannot be null or empty.");
        }

        this.generationTicks = generationTicks;
        this.nextGenerationTicks = generationTicks;
        this.ticks = 0;
        this.idCounter = initialId;

        this.position = position;
        this.velocity = velocity;
        this.tickets = tickets;
        this.statuses = statuses;
        clients = new ArrayList<>();
    }

    @Override
    public List<Client> popNext() {
        List<Client> readyClients = new ArrayList<>(clients);
        clients.clear();
        return readyClients;
    }

    @Override
    public int getTicks() {
        return ticks;
    }

    @Override
    public void tick() {
        ticks++;
        nextGenerationTicks--;
        if(nextGenerationTicks == 0)
        {
            var client = generateClient();
            clients.add(client);
            nextGenerationTicks = generationTicks;
        }
    }

    private Client generateClient() {
        idCounter++;
        return new Client(idCounter, position, velocity, tickets, statuses);
    }
}
