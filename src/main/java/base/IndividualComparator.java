package base;

import java.util.Comparator;
import java.util.Map;

class IndividualComparator implements Comparator<Individual> {

    private final Map<Individual, Integer> map;

    public IndividualComparator(Map<Individual, Integer> map) {
        this.map = map;
    }

    @Override
    public int compare(Individual o1, Individual o2) {
        return Integer.compare(map.get(o1), map.get(o2));
    }

}
