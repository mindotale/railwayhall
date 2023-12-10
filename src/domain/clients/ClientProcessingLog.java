package domain.clients;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClientProcessingLog {
    private final List<ClientProcessingRecord> records;

    public ClientProcessingLog() {
        records = new ArrayList<>();
    }

    public void addRecord(ClientProcessingRecord record) {
        records.add(record);
    }

    public List<ClientProcessingRecord> getRecords() {
        return Collections.unmodifiableList(records);
    }

    public void clearRecords() {
        records.clear();
    }
}
