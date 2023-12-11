package presentation.viewmodels;

import domain.railwayhalls.RailwayHall;
import domain.railwayhalls.RailwayHallConfig;

import java.util.List;
import java.util.stream.Collectors;

public class RailwayHallViewModel implements presentation.viewmodels.abstractions.RailwayHallViewModel {
    private RailwayHall model;

    public RailwayHallViewModel(RailwayHall hall)
    {
        model = hall;
    }


    public void tick()
    {
        model.tick();
    }

    @Override
    public int getClientCapacity()
    {
        return model.getClientCapacity();
    }

    @Override
    public int getRestartCapacity()
    {
        return model.getRestartClientCapacity();
    }

    @Override
    public List<presentation.viewmodels.abstractions.EntranceViewModel> getEntrances()
    {
        return model.getEntrances().stream()
                .map(EntranceViewModel::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<presentation.viewmodels.abstractions.TicketBoxViewModel> getTicketBoxes()
    {
        return model.getTicketBoxes().stream()
                .map(TicketBoxViewModel::new)
                .collect(Collectors.toList());
    }

    @Override
    public presentation.viewmodels.abstractions.TicketBoxViewModel getReservedTicketBox()
    {
        return new TicketBoxViewModel(model.getReservedTicketBox());
    }

    @Override
    public List<presentation.viewmodels.abstractions.ClientViewModel> getClients()
    {
        return model.getClients().stream()
                .map(ClientViewModel::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<presentation.viewmodels.abstractions.ClientViewModel> getTotalClients()
    {
        var clients = model.getClients().stream()
                .map(ClientViewModel::new)
                .map((o) -> (presentation.viewmodels.abstractions.ClientViewModel)o)
                .collect(Collectors.toList());
        for (var t : getTicketBoxes()) {
            clients.addAll(t.getClients());
        }
        for (var t : getTicketBoxes()) {
            var client = t.getCurrentClient();
            if(client != null)
                clients.add(t.getCurrentClient());
        }
        for (var e : getEntrances()) {
            clients.addAll(e.getClients());
        }
        return clients;
    }

    @Override
    public List<presentation.viewmodels.abstractions.ClientProcessingRecordViewModel> getTotalRecords() {
        var records = model.getRecords();
        return records.stream()
                .map(presentation.viewmodels.ClientProcessingRecordViewModel::new)
                .collect(Collectors.toList());
    }

    @Override
    public void clearRecords() {
        model.clearRecords();
    }

    @Override
    public boolean isOpen()
    {
        return model.isOpen();
    }

    @Override
    public void closeTicketBox(int id)
    {
        model.disableTicketBox(id);
    }

    @Override
    public void openTicketBox(int id)
    {
        model.enableTicketBox(id);
    }
}
