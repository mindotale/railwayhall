package presentation.viewmodels.stubs;

import domain.common.Vector;
import domain.ticketboxes.TicketBox;

import java.util.ArrayList;
import java.util.List;

public class TicketBoxViewModel implements presentation.viewmodels.abstractions.TicketBoxViewModel {

    private TicketBox model;

    @Override
    public int getId()
    {
        return 1;
    }

    @Override
    public presentation.viewmodels.abstractions.PositionViewModel getPosition()
    {
        return new PositionViewModel(new Vector(200, 200));
    }

    @Override
    public int getClientsCount()
    {
        return 0;
    }

    @Override
    public List<presentation.viewmodels.abstractions.ClientViewModel> getClients()
    {
        return new ArrayList<>();
    }

    @Override
    public boolean isOpen()
    {
        return true;
    }

}
