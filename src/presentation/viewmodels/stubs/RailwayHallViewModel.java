package presentation.viewmodels.stubs;

import domain.railwayhalls.RailwayHall;

import java.util.ArrayList;
import java.util.List;

public class RailwayHallViewModel implements presentation.viewmodels.abstractions.RailwayHallViewModel {
    private RailwayHall model;
    private TicketBoxViewModel ticketBox;
    private ClientViewModel client;

    public RailwayHallViewModel()
    {
        ticketBox = new TicketBoxViewModel();
        client = new ClientViewModel();

    }

    @Override
    public void tick()
    {
        System.out.println("Nais Chell");
    }

    @Override
    public int getClientCapacity()
    {
        return 50;
    }

    @Override
    public int getRestartCapacity()
    {
        return 40;
    }

    @Override
    public List<presentation.viewmodels.abstractions.EntranceViewModel> getEntrances()
    {
        var res =  new ArrayList<presentation.viewmodels.abstractions.EntranceViewModel>();
        res.add(new EntranceViewModel());
        return res;
    }

    @Override
    public List<presentation.viewmodels.abstractions.TicketBoxViewModel> getTicketBoxes() {
        var res =  new ArrayList<presentation.viewmodels.abstractions.TicketBoxViewModel>();
        res.add(ticketBox);
        return res;
    }


    @Override
    public TicketBoxViewModel getReservedTicketBox()
    {
        return new TicketBoxViewModel();
    }

    @Override
    public List<presentation.viewmodels.abstractions.ClientViewModel> getClients()
    {
        var res = new ArrayList<presentation.viewmodels.abstractions.ClientViewModel>();
        res.add(client);
        return res;
    }

    @Override
    public List<presentation.viewmodels.abstractions.ClientViewModel> getTotalClients()
    {
        var res = new ArrayList<presentation.viewmodels.abstractions.ClientViewModel>();
        res.add(new ClientViewModel());
        return res;
    }

    @Override
    public boolean isOpen()
    {
        return true;
    }

    @Override
    public void closeTicketBox(int id)
    {

    }

    @Override
    public void openTicketBox(int id)
    {

    }
}
