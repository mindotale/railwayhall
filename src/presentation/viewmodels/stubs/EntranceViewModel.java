package presentation.viewmodels.stubs;

import domain.common.Vector;
import domain.entrances.Entrance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EntranceViewModel implements presentation.viewmodels.abstractions.EntranceViewModel {

    public EntranceViewModel() {
    }

    @Override
    public int getId()
    {
        return 1;
    }

    @Override
    public presentation.viewmodels.abstractions.PositionViewModel getPosition()
    {
        return new PositionViewModel(new Vector(0, 0));
    }

    @Override
    public int getClientsCount()
    {
        return 1;
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
