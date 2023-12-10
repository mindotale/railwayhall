package presentation.viewmodels;

import domain.clients.Client;
import domain.clients.ClientStatus;

import java.util.List;
import java.util.stream.Collectors;

public class ClientViewModel {
    private Client model;

    public ClientViewModel(Client client)
    {
        this.model = client;
    }

    public int getId()
    {
        return model.getId();
    }

    public PositionViewModel getPosition()
    {
        return new PositionViewModel(model.getPosition());
    }

    public double getPriority()
    {
        return model.getPriority();
    }

    public int getTicketsCount()
    {
        return model.getTickets();
    }

    public List<String> getStatuses()
    {
        return model.getStatuses().stream()
                .map(ClientStatus::getName)
                .collect(Collectors.toList());
    }
}
