package presentation.viewmodels.abstractions;

import java.util.List;

public interface TicketBoxViewModel {
    int getId();

    PositionViewModel getPosition();

    int getClientsCount();

    List<ClientViewModel> getClients();

    boolean isOpen();
}
