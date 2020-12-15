package coordinate;


public class PlanarCoordinate extends Coordinate {

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
        return (int)
                Math.sqrt((getX() - pc.getX()) * (getX() - pc.getX()) + (getY() - pc.getY()) * (getY() - pc.getY()));
    }

}
