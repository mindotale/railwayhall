package presentation.viewmodels;

import domain.clients.ClientStatus;

public class ClientStatusViewModel {
    private String name;
    private int priority;

    public ClientStatusViewModel(ClientStatus status)
    {
        this.name = status.getName();
        this.priority = status.getPriority();
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }
}
