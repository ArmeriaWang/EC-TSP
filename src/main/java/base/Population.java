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

    public int getLeastTourDis(TSPProblem problem) {
        int leastDis = individuals.get(0).getTourDistance(problem);
        for (Individual individual : individuals) {
            leastDis = Math.min(leastDis, individual.getTourDistance(problem));
        }
        return leastDis;
    }

    public int getMeanTourDis(TSPProblem problem) {
        int sum = 0;
        for (Individual individual : individuals) {
            sum += individual.getTourDistance(problem);
        }
        return sum / individuals.size();
    }

    public int getStandardDeviationTourDis(TSPProblem problem) {
        int mean = getMeanTourDis(problem);
        int sd = 0;
        for (Individual individual : individuals) {
            sd += Math.abs(mean - individual.getTourDistance(problem));
        }
        return sd;
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
