package base;

import problem.TSPProblem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Population {

    private final List<Individual> individuals;

    public Population(int individualNum, int cityNum) {
        individuals = new ArrayList<>();
        for (int i = 0; i < individualNum; i++) {
            individuals.add(new Individual(cityNum));
        }
    }

    public Population(List<Individual> individuals) {
        this.individuals = Collections.unmodifiableList(individuals);
    }

    public List<Individual> getIndividuals() {
        return new ArrayList<>(individuals);
    }

    public int getLeastPathDistance(TSPProblem problem) {
        int leastDis = individuals.get(0).getPathDistance(problem);
        for (Individual individual : individuals) {
            leastDis = Math.max(leastDis, individual.getPathDistance(problem));
        }
        return leastDis;
    }

    public Population fitnessProportionate(TSPProblem problem) {
        // TODO
        return null;
    }

    public Population tournamentSelection(TSPProblem problem) {
        // TODO
        return null;
    }

    public Population elitism(TSPProblem problem) {
        // TODO
        return null;
    }

}
