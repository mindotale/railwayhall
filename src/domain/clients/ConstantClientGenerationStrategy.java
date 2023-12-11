package domain.clients;


import domain.common.IdGenerator;
import domain.common.Vector;

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
    private final IdGenerator<Integer> idGenerator;

    private int nextGenerationTicks;
    private int ticks;

    public ConstantClientGenerationStrategy(IdGenerator<Integer> idGenerator, int generationTicks, Vector position, double velocity, int tickets, Collection<ClientStatus> statuses) {
        this.idGenerator = idGenerator;
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
        var id = idGenerator.generateId();
        return new Client(id, position, velocity, tickets, statuses);
    }
}
