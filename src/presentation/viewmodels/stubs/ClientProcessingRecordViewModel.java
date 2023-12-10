package presentation.viewmodels.stubs;

import domain.clients.ClientProcessingRecord;

public class ClientProcessingRecordViewModel implements presentation.viewmodels.abstractions.ClientProcessingRecordViewModel {
    @Override
    public int getTicketBoxId()
    {
        return 1;
    }

    @Override
    public int getClientId()
    {
        return 1;
    }

    @Override
    public int getStartTicks()
    {
        return 10;
    }

    @Override
    public int getEndTicks()
    {
        return 12;
    }
}
