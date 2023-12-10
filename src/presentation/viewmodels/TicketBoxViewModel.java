package presentation.viewmodels;

import domain.ticketboxes.TicketBox;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TicketBoxViewModel {
    public TicketBoxViewModel(TicketBox ticketBox) {
        this.model = ticketBox;
    }

    private TicketBox model;

    public int getId()
    {
        return model.getId();
    }

    public PositionViewModel getPosition()
    {
        return new PositionViewModel(model.getPosition());
    }

    public int getClientsCount()
    {
        return model.getQueueSize();
    }

    public List<ClientViewModel> getClients()
    {
        return new ArrayList<ClientViewModel>();
    }

    public boolean isOpen()
    {
        return model.isEnabled();
    }

}
