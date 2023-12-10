package domain.ticketboxes;

public class ConstantTicketTimeProcessingStrategy implements TicketProcessingTimeStrategy {
    private final int processingTicks;

    public ConstantTicketTimeProcessingStrategy(int processingTicks) {
        if (processingTicks <= 0) {
            throw new IllegalArgumentException("Invalid processing time constraints.");
        }

        this.processingTicks = processingTicks;
    }

    @Override
    public int getTime() {
        return processingTicks;
    }
}
