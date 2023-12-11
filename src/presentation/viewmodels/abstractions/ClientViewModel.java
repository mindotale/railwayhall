package presentation.viewmodels.abstractions;

import java.util.List;

public interface ClientViewModel {
    int getId();

    PositionViewModel getPosition();

    double getPriority();

    int getTicketsCount();

    List<String> getStatuses();
}
