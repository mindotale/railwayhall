package presentation.viewmodels.stubs;
import java.util.Random;

import domain.common.Vector;
import domain.ticketboxes.TicketBox;
import presentation.viewmodels.abstractions.ClientProcessingRecordViewModel;
import presentation.viewmodels.abstractions.ClientViewModel;


import java.util.ArrayList;
import java.util.List;

public class TicketBoxViewModel implements presentation.viewmodels.abstractions.TicketBoxViewModel {

    private TicketBox model;


    private ClientViewModel client;
    private ClientViewModel client2;
    private ClientViewModel client3;
    private ClientViewModel client4;

    public TicketBoxViewModel()
    {
        client = new ClientViewModel();
        client2 = new ClientViewModel();
        client3 = new ClientViewModel();
        client4 = new ClientViewModel();

    }
    @Override
    public int getId()
    {
        return 1;
    }

    @Override
    public presentation.viewmodels.abstractions.PositionViewModel getPosition()
    {
        Random random = new Random();
        int randomNumber = random.nextInt(500) + 20; // 800 - 20 = 780 + 1 (to include 800)

        return new PositionViewModel(new Vector(randomNumber, randomNumber));
    }

    @Override
    public int getClientsCount()
    {
        return 0;
    }

    @Override
    public List<presentation.viewmodels.abstractions.ClientViewModel> getClients()
    {
        var res = new ArrayList<presentation.viewmodels.abstractions.ClientViewModel>();
        res.add(client);
        res.add(client2);
        res.add(client3);
        res.add(client4);

        res.add(client);
        res.add(client2);
        res.add(client3);
        res.add(client4);


        res.add(client);
        res.add(client2);
        res.add(client3);
        res.add(client4);
        return res;
    }

    @Override
    public ClientViewModel getCurrentClient() {
        return null;
    }

    @Override
    public List<ClientProcessingRecordViewModel> getRecords() {
        return new ArrayList<>();
    }

    @Override
    public void clearRecords() {

    }

    @Override
    public boolean isOpen()
    {
        Random random = new Random();
        return random.nextBoolean();
    }

}
