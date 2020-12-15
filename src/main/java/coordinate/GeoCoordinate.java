package coordinate;

public class GeoCoordinate extends Coordinate {

    private final double latitude, longitude;
    private static final double PI = Math.acos(-1.0);

    public GeoCoordinate(double latitude, double longitude) {
        super(latitude, longitude);
        int deg = (int) Math.round(super.getX());
        double min = super.getX() - deg;
        this.latitude = PI * (deg + 5.0 * min / 3.0) / 180.0;
        deg = (int) Math.round(super.getY());
        min = super.getY() - deg;
        this.longitude = PI * (deg + 5.0 * min / 3.0) / 180.0;
    }

    public double getLatitude() {
        return super.getX();
    }

    public double getLongitude() {
        return super.getY();
    }

    @Override
    public double getDistanceTo(Coordinate c) {
        GeoCoordinate gc = (GeoCoordinate) c;
        double RRR = 6378.388;
        double q1 = Math.cos(this.longitude - gc.longitude);
        double q2 = Math.cos(this.latitude - gc.latitude);
        double q3 = Math.cos(this.latitude + gc.latitude);
        return (int) (RRR * Math.acos(0.5 * ((1.0 + q1) * q2 - (1.0 - q1) * q3)) + 1.0);
    }

}
