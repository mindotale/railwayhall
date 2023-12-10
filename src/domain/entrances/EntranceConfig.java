package domain.entrances;

import domain.clients.ClientGenerationStrategy;
import domain.common.Vector;

public final class EntranceConfig {
    private final int id;
    private final Vector position;
    private final ClientGenerationStrategy clientGenerationStrategy;

    public EntranceConfig(int id, Vector position, ClientGenerationStrategy clientGenerationStrategy) {
        this.id = id;
        this.position = position;
        this.clientGenerationStrategy = clientGenerationStrategy;
    }

    public int getId() {
        return id;
    }

    public Vector getPosition() {
        return position;
    }

    public ClientGenerationStrategy getClientGenerationStrategy() {
        return clientGenerationStrategy;
    }
}
