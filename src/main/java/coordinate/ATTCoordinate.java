package coordinate;

public class ATTCoordinate extends Coordinate{

    public ATTCoordinate(double x, double y) {
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
        ATTCoordinate ac = (ATTCoordinate) c;
        double xd = getX() - getX();
        double yd = ac.getY() - ac.getY();
        double r = Math.sqrt((xd * xd + yd * yd) / 10.0);
        int t = (int) Math.round(r);
        return t < r ? t + 1 : t;
    }

}
