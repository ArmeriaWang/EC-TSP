package coordinate;


public class PlanarCoordinate extends Coordinate {

    private static final double PI = Math.acos(-1.0);

    public PlanarCoordinate(double x, double y) {
        super(x, y);
    }

    public double getX() {
        return super.getX();
    }

    public double getY() {
        return super.getY();
    }

    @Override
    public int getDistanceTo(Coordinate c) {
        PlanarCoordinate pc = (PlanarCoordinate) c;
        return (int) Math.sqrt((getX() - c.getX()) * (getX() - c.getX()) + (getY() - c.getY()) * (getY() - c.getY()));
    }

}
