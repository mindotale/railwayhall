package domain.clients;

import domain.common.IdGenerator;
import domain.common.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class RandomClientGenerationStrategy implements ClientGenerationStrategy {
    private final int minGenerationTicks;
    private final int maxGenerationTicks;
    private final Vector minPosition;
    private final Vector maxPosition;
    private final double minVelocity;
    private final double maxVelocity;
    private final int minTickets;
    private final int maxTickets;
    private final List<ClientStatus> statuses;
    private final List<Client> clients;
    private final IdGenerator<Integer> idGenerator;

    private int nextGenerationTicks;
    private int ticks;
    private final Random random;

    public RandomClientGenerationStrategy(IdGenerator<Integer> idGenerator, int minGenerationTicks, int maxGenerationTicks,
                                          Vector minPosition, Vector maxPosition,
                                          double minVelocity, double maxVelocity,
                                          int minTickets, int maxTickets,
                                          Collection<ClientStatus> statuses) {
        this.idGenerator = idGenerator;
        if (minGenerationTicks <= 0 || maxGenerationTicks <= 0 || minGenerationTicks > maxGenerationTicks) {
            throw new IllegalArgumentException("Invalid generation time range.");
        }

        if (minVelocity > maxVelocity) {
            throw new IllegalArgumentException("Invalid velocity range.");
        }

        if (minTickets <= 0 || maxTickets <= 0 || minTickets > maxTickets) {
            throw new IllegalArgumentException("Invalid ticket range.");
        }

        if (statuses.isEmpty()) {
            throw new IllegalArgumentException("Statuses collection cannot be null or empty.");
        }

        this.random = new Random(0);
        this.clients = new ArrayList<>();
        this.ticks = 0;

        this.minGenerationTicks = minGenerationTicks;
        this.maxGenerationTicks = maxGenerationTicks;
        this.minPosition = minPosition;
        this.maxPosition = maxPosition;
        this.minVelocity = minVelocity;
        this.maxVelocity = maxVelocity;
        this.minTickets = minTickets;
        this.maxTickets = maxTickets;
        this.statuses = statuses.stream().toList();

        this.nextGenerationTicks = getRandomValue(minGenerationTicks, maxGenerationTicks);
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
        if (nextGenerationTicks == 0) {
            Client client = generateRandomClient();
            clients.add(client);
            nextGenerationTicks = getRandomValue(minGenerationTicks, maxGenerationTicks);
        }
    }

    private Client generateRandomClient() {
        var id = idGenerator.generateId();
        Vector randomPosition = new Vector(
                getRandomValue(minPosition.getX(), maxPosition.getX()),
                getRandomValue(minPosition.getY(), maxPosition.getY())
        );
        double randomVelocity = getRandomValue(minVelocity, maxVelocity);
        int randomTickets = getRandomValue(minTickets, maxTickets);

        List<ClientStatus> randomStatuses = getRandomClientStatuses();

        return new Client(id, randomPosition, randomVelocity, randomTickets, randomStatuses);
    }

    private List<ClientStatus> getRandomClientStatuses() {
        int numberOfStatuses = random.nextInt(statuses.size() + 1); // Random count of statuses
        List<ClientStatus> randomStatuses = new ArrayList<>();

        for (int i = 0; i < numberOfStatuses; i++) {
            int index = random.nextInt(statuses.size());
            randomStatuses.add(statuses.get(index));
        }

        return randomStatuses;
    }

    private double getRandomValue(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    private int getRandomValue(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}
