package presentation.viewmodels.abstractions;

import java.util.List;

public interface EntranceViewModel {
    int getId();

    PositionViewModel getPosition();

    int getClientsCount();

    List<ClientViewModel> getClients();

    boolean isOpen();
}
