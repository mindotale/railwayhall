package presentation.viewmodels.stubs;

import domain.clients.ClientStatus;

public class ClientStatusViewModel implements presentation.viewmodels.abstractions.ClientStatusViewModel {

    @Override
    public String getName() {
        return "StatusName";
    }

    @Override
    public int getPriority() {
        return 1;
    }
}
