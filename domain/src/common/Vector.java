package common;

public final class Vector {
    private final double x;
    private final double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Vector add(Vector other) {
        return new Vector(this.x + other.x, this.y + other.y);
    }

    public Vector subtract(Vector other) {
        return new Vector(this.x - other.x, this.y - other.y);
    }

    public double dotProduct(Vector other) {
        return this.x * other.x + this.y * other.y;
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public double distanceTo(Vector other) {
        return this.subtract(other).magnitude();
    }

    public Vector scale(double scalar) {
        return new Vector(this.x * scalar, this.y * scalar);
    }

    public Vector normalize() {
        double mag = magnitude();
        if (mag != 0) {
            return new Vector(x / mag, y / mag);
        } else {
            return new Vector(0, 0);
        }
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}

