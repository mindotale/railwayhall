package presentation.viewmodels;

import domain.clients.ClientProcessingLog;
import domain.clients.ClientProcessingRecord;

public class ClientProcessingRecordViewModel implements presentation.viewmodels.abstractions.ClientProcessingRecordViewModel {
    private ClientProcessingRecord model;

    public ClientProcessingRecordViewModel(ClientProcessingRecord record)
    {
        model = record;
    }
    @Override
    public int getTicketBoxId()
    {
        return model.getCashDeskId();
    }

    @Override
    public int getClientId()
    {
        return model.getClientId();
    }

    @Override
    public int getStartTicks()
    {
        return model.getStartTicks();
    }

    @Override
    public int getEndTicks()
    {
        return model.getEndTicks();
    }
}
