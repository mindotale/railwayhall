package domain.ticketboxes;
import java.util.Random;

public class RandomTicketTimeProcessingStrategy implements TicketProcessingTimeStrategy {
    private final int minProcessingTicks;
    private final int maxProcessingTicks;
    private final Random random;
    public RandomTicketTimeProcessingStrategy(int minProcessingTicks, int maxProcessingTicks) {
        if (!isValidProcessingTime(minProcessingTicks, maxProcessingTicks)) {
            throw new IllegalArgumentException("Invalid processing time constraints.");
        }

        this.random = new Random(0);
        this.minProcessingTicks = minProcessingTicks;
        this.maxProcessingTicks = maxProcessingTicks;
    }

    private static boolean isValidProcessingTime(int minProcessingTicks, int maxProcessingTicks) {
        return minProcessingTicks > 0 || maxProcessingTicks > 0 || minProcessingTicks <= maxProcessingTicks;
    }

    @Override
    public int getTime() {
        return random.nextInt(minProcessingTicks, maxProcessingTicks + 1);
    }
}
