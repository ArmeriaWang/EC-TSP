package problem;

import coordinate.Coordinate;

import java.util.Collections;
import java.util.List;

public class CoordProblem extends TSPProblem{

    private final List<Coordinate> coordinates;

    public CoordProblem(String name, int cityNum, List<Coordinate> coordinates) {
        super(name, cityNum);
        this.coordinates = Collections.unmodifiableList(coordinates);
    }

    @Override
    public int getDistance(int city1, int city2) {
        return coordinates.get(city1).getDistanceTo(coordinates.get(city2));
    }

}
