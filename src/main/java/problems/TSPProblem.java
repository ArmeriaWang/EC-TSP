package problems;

/**
 * The TSP problem class
 */
public abstract class TSPProblem {

    private final String name;
    private final int cityNum;

    public TSPProblem(String name, int cityNum) {
        this.name = name;
        this.cityNum = cityNum;
    }

    public String getName() {
        return name;
    }

    public int getCityNum() {
        return cityNum;
    }

    abstract public Number getDistance(int city1, int city2);

}
