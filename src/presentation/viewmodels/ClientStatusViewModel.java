package presentation.viewmodels;

import domain.clients.ClientStatus;

public class ClientStatusViewModel implements presentation.viewmodels.abstractions.ClientStatusViewModel {
    private String name;
    private int priority;

    public ClientStatusViewModel(ClientStatus status)
    {
        this.name = status.getName();
        this.priority = status.getPriority();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPriority() {
        return priority;
    }
}
