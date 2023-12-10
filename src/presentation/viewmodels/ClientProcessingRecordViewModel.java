package presentation.viewmodels;

import domain.clients.ClientProcessingRecord;

public class ClientProcessingRecordViewModel {
    private ClientProcessingRecord model;
    public int getTicketBoxId()
    {
        return model.getCashDeskId();
    }

    public int getClientId()
    {
        return model.getClientId();
    }

    public int getStartTicks()
    {
        return model.getStartTicks();
    }

    public int getEndTicks()
    {
        return model.getEndTicks();
    }
}
