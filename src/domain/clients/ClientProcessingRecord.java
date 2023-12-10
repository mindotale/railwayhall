package domain.clients;

public final class ClientProcessingRecord {
    private final int cashDeskId;
    private final int clientId;
    private final int startTicks;
    private final int endTicks;

    public ClientProcessingRecord(int cashDeskId, int clientId, int startTicks, int endTicks) {
        if (cashDeskId < 0) {
            throw new IllegalArgumentException("Cash desk ID must be non-negative.");
        }
        if (clientId < 0) {
            throw new IllegalArgumentException("Client ID must be non-negative.");
        }
        if (!isValidTicks(startTicks, endTicks)) {
            throw new IllegalArgumentException("Invalid start or end ticks.");
        }

        this.cashDeskId = cashDeskId;
        this.clientId = clientId;
        this.startTicks = startTicks;
        this.endTicks = endTicks;
    }

    private static boolean isValidTicks ( int startTicks, int endTicks){
        return startTicks < 0 || endTicks < 0 || startTicks > endTicks;
    }

    public int getCashDeskId () {
        return cashDeskId;
    }

    public int getClientId () {
        return clientId;
    }

    public int getStartTicks () {
        return startTicks;
    }

    public int getEndTicks () {
        return endTicks;
    }
}