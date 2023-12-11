package presentation.viewmodels.abstractions;

import java.util.List;

public interface RailwayHallViewModel {

    void tick();
    int getClientCapacity();

    int getRestartCapacity();

    List<EntranceViewModel> getEntrances();

    List<TicketBoxViewModel> getTicketBoxes();

    TicketBoxViewModel getReservedTicketBox();

    List<ClientViewModel> getClients();

    List<ClientViewModel> getTotalClients();

    boolean isOpen();

    void closeTicketBox(int id);

    void openTicketBox(int id);
}
