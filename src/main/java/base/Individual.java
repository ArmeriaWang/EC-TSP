package base;


import problem.TSPProblem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A possible solution for the TSP problem
 */
public class Individual {

    private final List<Integer> tour;

    /**
     * construct a random solution
     *
     * @param cityNum number of cities
     */
    public Individual(int cityNum) {
        List<Integer> tourList = new ArrayList<Integer>();
        for (int i = 1; i <= cityNum; i++) {
            tourList.add(i);
        }
        Collections.shuffle(tourList);
        tour = Collections.unmodifiableList(tourList);
    }

    /**
     * construct a solution by a specified tour list
     *
     * @param tour the tour list
     */
    public Individual(List<Integer> tour) {
        this.tour = Collections.unmodifiableList(tour);
    }

    /**
     * @return get the i-th city in the tour
     */
    public int getAt(int i) {
        return tour.get(i);
    }

    /**
     * @return get a copy of the tour
     */
    public List<Integer> getTour() {
        return new ArrayList<>(tour);
    }

    public Individual swap() {
        // TODO
        return null;
    }

    public Individual insert() {
        // TODO
        return null;
    }

    public Individual inversion() {
        // TODO
        return null;
    }

    public Individual scramble() {
        // TODO
        return null;
    }

    public List<Individual> orderCrossover(Individual o) {
        // TODO
        return null;
    }

    public List<Individual> partiallyMappedCrossover(Individual o) {
        // TODO
        return null;
    }

    public List<Individual> cycleCrossover(Individual o) {
        // TODO
        return null;
    }

    public List<Individual> edgeRecombination(Individual o) {
        // TODO
        return null;
    }

    public int getPathDistance(TSPProblem problem) {
        // TODO
        return 0;
    }

}
