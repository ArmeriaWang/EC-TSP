package base;

import java.util.ArrayList;
import java.util.List;

public class Population {

    private final List<Individual> individuals;

    public Population(int individualNum, int cityNum) {
        individuals = new ArrayList<Individual>();
        for (int i = 0; i < individualNum; i++) {
            individuals.add(new Individual(cityNum));
        }
    }
}
