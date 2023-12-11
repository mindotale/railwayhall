package presentation.viewmodels.stubs;

import domain.clients.Client;
import domain.clients.ClientStatus;
import domain.common.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientViewModel implements presentation.viewmodels.abstractions.ClientViewModel {
    int x;

    public ClientViewModel()
    {
        x = 400;
    }

    @Override
    public int getId()
    {
        return 1;
    }

    @Override
    public presentation.viewmodels.abstractions.PositionViewModel getPosition()
    {
        x++;
        return new PositionViewModel(new Vector(x, x));
    }

    @Override
    public double getPriority()
    {
        return 1;
    }

    @Override
    public int getTicketsCount()
    {
        return 3;
    }

    @Override
    public List<String> getStatuses()
    {
        return new ArrayList<>();
    }
}
