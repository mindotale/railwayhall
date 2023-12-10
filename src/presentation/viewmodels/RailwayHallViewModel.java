package presentation.viewmodels;

import domain.railwayhalls.RailwayHall;

import java.util.List;
import java.util.stream.Collectors;

public class RailwayHallViewModel {
    private RailwayHall model;

    public int getClientCapacity()
    {
        return model.getClientCapacity();
    }

    public int getRestartCapacity()
    {
        return model.getRestartClientCapacity();
    }

    public List<EntranceViewModel> getEntrances()
    {
        return model.getEntrances().stream()
                .map(EntranceViewModel::new)
                .collect(Collectors.toList());
    }

    public List<TicketBoxViewModel> getTicketBoxes()
    {
        return model.getTicketBoxes().stream()
                .map(TicketBoxViewModel::new)
                .collect(Collectors.toList());
    }

    public TicketBoxViewModel getReservedTicketBox()
    {
        return new TicketBoxViewModel(model.getReservedTicketBox());
    }

    public List<ClientViewModel> getClients()
    {
        return model.getClients().stream()
                .map(ClientViewModel::new)
                .collect(Collectors.toList());
    }

    public List<ClientViewModel> getTotalClients()
    {
        var clients = model.getClients().stream()
                .map(ClientViewModel::new)
                .collect(Collectors.toList());
        for (var t : getTicketBoxes()) {
            clients.addAll(t.getClients());
        }
        return clients;
    }

    public boolean isOpen()
    {
        return model.isOpen();
    }

    public void closeTicketBox(int id)
    {
        model.disableTicketBox(id);
    }

    public void openTicketBox(int id)
    {
        model.enableTicketBox(id);
    }
}
