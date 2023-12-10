package presentation.viewmodels;

import domain.entrances.Entrance;

import java.util.List;
import java.util.stream.Collectors;

public class EntranceViewModel {
    private Entrance model;

    public EntranceViewModel(Entrance entrance) {
        this.model = entrance;
    }

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
        return model.getClients().size();
    }

    public List<ClientViewModel> getClients()
    {
        var clients = model.getClients();
        return clients.stream()
                .map(ClientViewModel::new) // This assumes a constructor in NewObject that takes an OldObject
                .collect(Collectors.toList());
    }

    public boolean isOpen()
    {
        return model.isOpen();
    }
}
