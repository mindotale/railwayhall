package presentation.viewmodels;

import domain.common.Vector;

public class PositionViewModel {
    private int x;
    private int y;

    public PositionViewModel(Vector v) {
        this.x = (int)Math.round(v.getX());
        this.y = (int)Math.round(v.getY());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
