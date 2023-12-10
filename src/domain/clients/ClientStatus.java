package domain.clients;

public final class ClientStatus {
    private final String name;
    private final int priority;

    public ClientStatus(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }
}
