package base;


import problem.TSPProblem;

import java.util.*;

import static utils.Utils.getRandomPair;

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

    public int getTourDistance(TSPProblem problem) {
        int tourDistance = problem.getDistance(tour.get(0), tour.get(problem.getCityNum() - 1));
        for (int i = 1; i < problem.getCityNum(); i++) {
            tourDistance += problem.getDistance(tour.get(i - 1), tour.get(i));
        }
        return tourDistance;
    }

    /**
     * return the fitness
     */
    public double getFitness(TSPProblem problem, int sum) {
        return 1.0 * sum / getTourDistance(problem);
    }

    /**
     * Swap mutation for permutations
     */
    public Individual swap() {
        List<Integer> newTour = new ArrayList<>(tour);
        int[] randomPair = getRandomPair(newTour.size());
        int x = randomPair[0];
        int y = randomPair[1];
        // swap their positions
        newTour.set(x, tour.get(y));
        newTour.set(y, tour.get(x));
        return new Individual(newTour);
    }

    /**
     * Insert mutation for permutations
     */
    public Individual insert() {
        List<Integer> newTour = new ArrayList<>(tour);
        // pick two allele values at random
        int[] randomPair = getRandomPair(newTour.size());
        int x = randomPair[0];
        int y = randomPair[1];
        // move the second to follow the first, shifting the rest along to accommodate
        int tmp = tour.get(y);
        newTour.remove(y);
        newTour.add(x + 1, tmp);
        return new Individual(newTour);
    }

    /**
     * Inversion mutation for permutations
     */
    public Individual inversion() {
        List<Integer> newTour = new ArrayList<>(tour);
        //pick two allele values at random
        int[] randomPair = getRandomPair(newTour.size());
        int l = randomPair[0];
        int r = randomPair[1] + 1;
        //invert the substring between them
        for (int i = l; i < l + (r - l) / 2; i++) {
            int j = r - (i - l + 1);
            int tmp = newTour.get(i);
            newTour.set(i, newTour.get(j));
            newTour.set(j, tmp);
        }
        return new Individual(newTour);
    }

    /**
     * Scramble mutation for permutations
     */
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

    /**
     * order crossover algo for permutations
     */
    public List<Individual> orderCrossover(Individual o) {
        int[] randomPair = getRandomPair(tour.size());
        int l = randomPair[0];
        int r = randomPair[1] + 1;
        Individual i1 = singleOrderCrossover(o, l, r);
        Individual i2 = o.singleOrderCrossover(this, l, r);
        return List.of(i1, i2);
    }

    private Individual singleOrderCrossover(Individual o, int l, int r) {
        List<Integer> child = new ArrayList<>(tour);
        boolean[] flags = new boolean[tour.size()];
        for (int i = 0; i < tour.size(); i++) {
            flags[i] = false;
        }
        for (int i = l; i < r; i++) {
            flags[child.get(i)] = true;
        }
        int j = r % tour.size();
        for (int i = r % tour.size(); i != l; i = (i + 1) % tour.size()) {
            while (flags[o.getAt(j)]) {
                j = (j + 1) % tour.size();
            }
            child.set(i, o.getAt(j));
            flags[child.get(i)] = true;
        }
        return new Individual(child);
    }

    /**
     * Partially Mapped Crossover for permutations
     */
    public List<Individual> partiallyMappedCrossover(Individual o) {
        int[] randomPair = getRandomPair(tour.size());
        int l = randomPair[0];
        int r = randomPair[1] + 1;
        Individual i1 = singlePartiallyMappedCrossover(o, l, r);
        Individual i2 = o.singlePartiallyMappedCrossover(this, l, r);
        return List.of(i1, i2);
    }

    private Individual singlePartiallyMappedCrossover(Individual o, int l, int r) {
        List<Integer> child = new ArrayList<>(tour);
        int[] mapping = new int[tour.size()];
        for (int i = 0; i < tour.size(); i++) {
            mapping[i] = -1;
        }
        for (int i = l; i < r; i++) {
            mapping[getAt(i)] = o.getAt(i);
        }
        for (int i = r % tour.size(); i != l; i = (i + 1) % tour.size()) {
            int mut = mapping[o.getAt(i)];
            int last = -1;
            while (mut != -1) {
                last = mut;
                mut = mapping[mut];
            }
            child.set(i, last == -1 ? o.getAt(i) : last);
        }
        return new Individual(child);
    }

    /**
     * Cycle crossover algo for permutations
     */
    public List<Individual> cycleCrossover(Individual o) {
        Individual i1 = singleCycleCrossover(o);
        Individual i2 = o.singleCycleCrossover(this);
        return List.of(i1, i2);
    }

    private Individual singleCycleCrossover(Individual o) {
        List<Integer> child = new ArrayList<>(tour);
        List<List<Integer>> cycles = new ArrayList<>();
        int cityInCycleCnt = 0;
        int[] mapping = new int[tour.size()];
        int[] position = new int[tour.size()];
        for (int i = 0; i < tour.size(); i++) {
            mapping[i] = -1;
            position[tour.get(i)] = i;
        }
        int index = 0;
        while (cityInCycleCnt != tour.size()) {
            if (mapping[index] == -1) {
                int i = index;
                List<Integer> cycle = new ArrayList<>();
                while (mapping[i] == -1) {
                    cycle.add(i);
                    cityInCycleCnt++;
                    mapping[i] = o.getAt(i);
                    i = position[mapping[i]];
                }
                cycles.add(cycle);
            }
            index++;
        }
        for (int i = 0; i < cycles.size(); i++) {
            if (i % 2 == 0) {
                List<Integer> cycle = cycles.get(i);
                for (int pos : cycle) {
                    child.set(pos, o.getAt(pos));
                }
            }
        }
        return new Individual(child);
    }

    /**
     * Edge Recombination for permutations
     */
    public Individual[] EdgeRecombinationCrossover(Individual i1, Individual i2) {

        List<Integer> l1 = i1.getTour();
        List<Integer> l2 = i2.getTour();
        int len = l1.size();

        int[] children = new int[len];
        Arrays.fill(children, -1);

        // construct a table
        Map<Integer, Set<EdgeElement>> table = EdgeTable(i1, i2);  //all Integers represent element
        // pick an initial element at random
        int start = random.nextInt(len) + 1;
        children[0] = start;
        // update table
        table.remove(start);
        for (Set<EdgeElement> s : table.values()) {
            for (EdgeElement e : s) {
                if (e.number == start) {
                    s.remove(e);
                }
            }
        }

        for (int i = 1; i < len; i++) {
            Set<EdgeElement> set = table.get(start);
            boolean flag = false;  //if there is a common
            int min = len;
            int min_number = Integer.MAX_VALUE;
            for (EdgeElement e : set) {
                if (e.common) {
                    start = e.number;
                    children[i] = e.number;
                    flag = true;
                    break;
                }
                if (table.get(e.number).size() < min) {
                    min = table.get(e.number).size();
                    min_number = e.number;
                }
            }
            if (!flag) {
                start = min_number;
                children[i] = start;
            }

            //update table
            table.remove(start);
            for (Set<EdgeElement> s : table.values()) {
                for (EdgeElement e : s) {
                    if (e.number == start) {
                        s.remove(e);
                    }
                }
            }
        }

        Individual[] individuals = new Individual[1];
        List<Integer> childrenList = new ArrayList<>() {{
            for (int i = 0; i < len; i++) {
                add(children[i]);
            }
        }};
        individuals[0] = new Individual(childrenList);
        return individuals;
    }

    private Map<Integer, Set<EdgeElement>> EdgeTable(Individual i1, Individual i2) {
        List<Integer> l1 = i1.getTour();
        List<Integer> l2 = i2.getTour();
        int len = l1.size();
        Map<Integer, Set<EdgeElement>> table = new HashMap<>();
        // put l1 in table
        for (int i = 0; i < len; i++) {
            Set<EdgeElement> set = new HashSet<>();
            if (i == 0) {
                set.add(new EdgeElement(l1.get(1), false));
                set.add(new EdgeElement(l1.get(len - 1), false));
            }
            else if (i == len - 1) {
                set.add(new EdgeElement(l1.get(0), false));
                set.add(new EdgeElement(l1.get(len - 2), false));
            }
            else {
                set.add(new EdgeElement(l1.get(i - 1), false));
                set.add(new EdgeElement(l1.get(i + 1), false));
            }
            table.put(l1.get(i), set);
        }
        // put l2 in table
        for (int i = 0; i < len; i++) {
            Set<EdgeElement> set = table.get(l2.get(i));
            int neigh1, neigh2;
            if (i == 0) {
                neigh1 = l2.get(1);
                neigh2 = l2.get(len - 1);
            }
            else if (i == len - 1) {
                neigh1 = l2.get(0);
                neigh2 = l2.get(len - 2);
            }
            else {
                neigh1 = l2.get(i - 1);
                neigh2 = l2.get(i + 1);
            }
            boolean flag1 = false;
            boolean flag2 = false;
            for (EdgeElement e : set) {
                if (e.number == neigh1) {
                    e.setCommon(true);
                    flag1 = true;
                }
                if (e.number == neigh2) {
                    e.setCommon(true);
                    flag2 = true;
                }
            }
            if (!flag1) {
                set.add(new EdgeElement(neigh1, false));
            }
            if (!flag2) {
                set.add(new EdgeElement(neigh2, false));
            }
            table.put(l2.get(i), set);
        }
        return table;
    }

    static class EdgeElement {
        int number;
        boolean common;

        public EdgeElement(int number, boolean common) {
            this.number = number;
            this.common = common;
        }

        public void setCommon(boolean common) {
            this.common = common;
        }
    }

}
