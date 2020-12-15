package coordinate;

public class ATTCoordinate extends Coordinate{

    public ATTCoordinate(double x, double y) {
        super(x, y);
    }

    @Override
    public double getDistanceTo(Coordinate c) {
        double xd = getX() - getX();
        double yd = c.getY() - c.getY();
        double r = Math.sqrt((xd * xd + yd * yd) / 10.0);
        int t = (int) Math.round(r);
        return t < r ? t + 1 : t;
    }

}
