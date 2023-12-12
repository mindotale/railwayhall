package presentation.viewmodels;

import domain.ticketboxes.TicketBox;
import presentation.viewmodels.abstractions.ClientProcessingRecordViewModel;
import presentation.viewmodels.abstractions.ClientViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TicketBoxViewModel implements presentation.viewmodels.abstractions.TicketBoxViewModel {
    public TicketBoxViewModel(TicketBox ticketBox) {
        this.model = ticketBox;
    }

    private TicketBox model;

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
    public int getClientsCount()
    {
        return model.getQueueSize();
    }

    @Override
    public List<presentation.viewmodels.abstractions.ClientViewModel> getClients()
    {
        var clients = model.getQueue();
        return clients.stream()
                .map(presentation.viewmodels.ClientViewModel::new)
                .collect(Collectors.toList());
    }

    @Override
    public ClientViewModel getCurrentClient() {
        var client = model.getCurrentClient();
        if(client == null)
            return null;
        return new presentation.viewmodels.ClientViewModel(client);

    }

    @Override
    public List<ClientProcessingRecordViewModel> getRecords() {
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
        return model.isEnabled();
    }

}
