package domain.common;

public class IntegerIdGenerator implements IdGenerator<Integer> {
    private int counter;

    public IntegerIdGenerator() {
        this.counter = 0;
    }

    @Override
    public Integer generateId() {
        return counter++;
    }
}
