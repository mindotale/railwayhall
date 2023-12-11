package presentation.viewmodels.abstractions;

import java.util.List;

public interface TicketBoxViewModel {
    int getId();

    PositionViewModel getPosition();

    int getClientsCount();

    List<ClientViewModel> getClients();

    ClientViewModel getCurrentClient();

    List<ClientProcessingRecordViewModel> getRecords();
    void clearRecords();

    boolean isOpen();
}
