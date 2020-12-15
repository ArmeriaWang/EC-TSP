package problem;

import algo.SimpleGeneticAlgo;

import java.io.IOException;

/**
 * The TSP problem class
 */
public abstract class TSPProblem {

    private final String name;
    private final int cityNum;

    protected TSPProblem(String name, int cityNum) {
        this.name = name;
        this.cityNum = cityNum;
    }

    public String getName() {
        return name;
    }

    public int getCityNum() {
        return cityNum;
    }

    abstract public int getDistance(int city1, int city2);

    public void solve(SimpleGeneticAlgo algo) throws IOException {
        algo.solve(this);
    }

}
