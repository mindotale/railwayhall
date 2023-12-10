package presentation.viewmodels;

import domain.entrances.Entrance;

import java.util.List;
import java.util.stream.Collectors;

public class EntranceViewModel implements presentation.viewmodels.abstractions.EntranceViewModel {
    private Entrance model;

    public EntranceViewModel(Entrance entrance) {
        this.model = entrance;
    }

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
        return model.getClients().size();
    }

    @Override
    public List<presentation.viewmodels.abstractions.ClientViewModel> getClients()
    {
        var clients = model.getClients();
        return clients.stream()
                .map(ClientViewModel::new) // This assumes a constructor in NewObject that takes an OldObject
                .collect(Collectors.toList());
    }

    @Override
    public boolean isOpen()
    {
        return model.isOpen();
    }
}
