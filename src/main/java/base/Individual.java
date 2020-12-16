package base;


import problem.TSPProblem;

import java.util.*;

/**
 * A possible solution for the TSP problem
 */
public class Individual {

    private final List<Integer> tour;
    private final Random random = new Random();

    /**
     * construct a random solution
     *
     * @param cityNum number of cities
     */
    public Individual(int cityNum) {
        List<Integer> tourList = new ArrayList<>();
        for (int i = 0; i < cityNum; i++) {
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
        List<Integer> newTour = new ArrayList<>(tour);
        int x = random.nextInt();
        int y = random.nextInt();
        // swap their positions
        int tmp = tour.get(x);
        newTour.set(x, tour.get(y));
        newTour.set(y, tmp);
        return new Individual(newTour);
    }

    public Individual insert() {
        List<Integer> newTour = new ArrayList<>(tour);
        // pick two allele values at random
        int r1 = random.nextInt(), r2 = random.nextInt();
        int x = Math.min(r1, r2);
        int y = Math.max(r1, r2);
        // move the second to follow the first, shifting the rest along to accommodate
        newTour.remove(y);
        newTour.add(x + 1, tour.get(y));
        return new Individual(newTour);
    }

    public Individual inversion() {
        List<Integer> newTour = new ArrayList<>(tour);
        //pick two allele values at random
        int r1 = random.nextInt(), r2 = random.nextInt();
        int x = Math.min(r1, r2);
        int y = Math.max(r1, r2);
        //invert the substring between them
        List<Integer> subList = tour.subList(x, y);
        newTour.removeAll(subList);
        Collections.reverse(subList);
        newTour.addAll(x, subList);
        return new Individual(newTour);
    }

    public Individual scramble() {
        List<Integer> newTour = new ArrayList<>(tour);
        int subsetSize = random.nextInt(newTour.size()) + 1;

        Set<Integer> indices = new HashSet<>();
        for (int i = 0; i < subsetSize; i++) {
            int index;
            do {
                index = random.nextInt(newTour.size());
            } while (indices.contains(index));
            indices.add(index);
        }
        List<Integer> sortedIndices = new ArrayList<>(indices);
        List<Integer> randomIndices = new ArrayList<>(indices);
        Collections.shuffle(randomIndices);
        for (int i = 0; i < subsetSize; i++) {
            newTour.set(sortedIndices.get(i), tour.get(randomIndices.get(i)));
        }
        return new Individual(newTour);
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

    public int getTourDistance(TSPProblem problem) {
        int tourDistance = problem.getDistance(tour.get(0), tour.get(problem.getCityNum() - 1));
        for (int i = 1; i < problem.getCityNum(); i++) {
            tourDistance += problem.getDistance(tour.get(i - 1), tour.get(i));
        }
        return tourDistance;
    }

    public double getFitness(TSPProblem problem, int sum) {
        return 1.0 * sum / getTourDistance(problem);
    }

}
