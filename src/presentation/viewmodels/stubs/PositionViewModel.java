package presentation.viewmodels.stubs;

import domain.common.Vector;

public class PositionViewModel implements presentation.viewmodels.abstractions.PositionViewModel {
    private int x;
    private int y;

    public PositionViewModel(Vector v) {
        this.x = (int)Math.round(v.getX());
        this.y = (int)Math.round(v.getY());
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
