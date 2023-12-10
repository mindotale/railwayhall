package presentation.viewmodels;

import domain.clients.Client;
import domain.clients.ClientStatus;

import java.util.List;
import java.util.stream.Collectors;

public class ClientViewModel implements presentation.viewmodels.abstractions.ClientViewModel {
    private Client model;

    public ClientViewModel(Client client)
    {
        this.model = client;
    }

    @Override
    public int getId()
    {
        return model.getId();
    }

    @Override
    public presentation.viewmodels.abstractions.PositionViewModel getPosition()
    {
        return new PositionViewModel(model.getPosition());
    }

    @Override
    public double getPriority()
    {
        return model.getPriority();
    }

    @Override
    public int getTicketsCount()
    {
        return model.getTickets();
    }

    @Override
    public List<String> getStatuses()
    {
        return model.getStatuses().stream()
                .map(ClientStatus::getName)
                .collect(Collectors.toList());
    }
}
