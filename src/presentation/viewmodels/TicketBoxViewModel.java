package presentation.viewmodels;

import domain.ticketboxes.TicketBox;

import java.util.ArrayList;
import java.util.List;

public class TicketBoxViewModel implements presentation.viewmodels.abstractions.TicketBoxViewModel {
    public TicketBoxViewModel(TicketBox ticketBox) {
        this.model = ticketBox;
    }

    private final TicketBox model;

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
        return new ArrayList<presentation.viewmodels.abstractions.ClientViewModel>();
    }

    @Override
    public boolean isOpen()
    {
        return model.isEnabled();
    }

}
