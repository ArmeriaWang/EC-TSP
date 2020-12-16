package base;

import problem.TSPProblem;

import java.util.*;

public class Population {

    private final List<Individual> individuals;
    private final Random random = new Random();

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

    public int getIndividualNum() {
        return individuals.size();
    }

    public int getLeastTourDis(TSPProblem problem) {
        int leastDis = individuals.get(0).getTourDistance(problem);
        for (Individual individual : individuals) {
            leastDis = Math.min(leastDis, individual.getTourDistance(problem));
        }
        return leastDis;
    }

    public int getSumTourDis(TSPProblem problem) {
        int sum = 0;
        for (Individual individual : individuals) {
            sum += individual.getTourDistance(problem);
        }
        return sum;
    }

    public int getMeanTourDis(TSPProblem problem) {
        return getSumTourDis(problem) / individuals.size();
    }

    public int getStandardDeviationTourDis(TSPProblem problem) {
        int mean = getMeanTourDis(problem);
        int sd = 0;
        for (Individual individual : individuals) {
            sd += Math.abs(mean - individual.getTourDistance(problem));
        }
        return sd;
    }

    public Map<Individual, Integer> getIndividualDistanceMap(TSPProblem problem) {
        Map<Individual, Integer> result = new HashMap<>();
        for (Individual individual : individuals) {
            result.put(individual, individual.getTourDistance(problem));
        }
        return result;
    }

    public Population fitnessProportionate(TSPProblem problem) {
        List<Double> fitnessList = new ArrayList<>();
        int fitnessSum = 0;
        for (Individual individual : individuals) {
            double fitness = individual.getFitness(problem, getSumTourDis(problem));
            fitnessList.add(fitness);
            fitnessSum += fitness;
        }
        List<Double> proportionList = new ArrayList<>();
        proportionList.add(fitnessList.get(0) / fitnessSum);
        for (int i = 1; i < individuals.size() - 1; i++) {
            proportionList.add(proportionList.get(i - 1) + fitnessList.get(i) / fitnessSum);
        }
        proportionList.add(1.0);
        List<Individual> parents = new ArrayList<>();
        for (int i = 0; i < individuals.size(); i++) {
            double randNum = Math.random();
            int bsResult = Collections.binarySearch(proportionList, randNum);
            int index = bsResult >= 0 ? bsResult : -bsResult - 1;
            parents.add(individuals.get(index));
        }
        return new Population(parents);
    }

    public Population tournamentSelection(TSPProblem problem, int sampleNum) {
        List<Individual> parents = new ArrayList<>();
        List<Individual> candidates = new ArrayList<>();
        for (int i = 0; i < individuals.size(); i++) {
            candidates.clear();
            while (candidates.size() != sampleNum) {
                Individual candidate = individuals.get(random.nextInt(individuals.size()));
                if (!candidates.contains(candidate)) {
                    candidates.add(candidate);
                }
            }
            Individual bestCandidate = candidates.get(0);
            int leastDistance = bestCandidate.getTourDistance(problem);
            for (Individual candidate : candidates) {
                int distance = candidate.getTourDistance(problem);
                if (distance < leastDistance) {
                    bestCandidate = candidate;
                    leastDistance = distance;
                }
            }
            parents.add(bestCandidate);
        }

        return new Population(parents);
    }

    public Population elitism(TSPProblem problem, Population offsprings, int eliteNum) {
        List<Individual> parentList = new ArrayList<>(individuals);
        List<Individual> offspringList = offsprings.getIndividuals();
        Map<Individual, Integer> parentMap = getIndividualDistanceMap(problem);
        parentList.sort(new IndividualComparator(parentMap));
        Map<Individual, Integer> offspringMap = offsprings.getIndividualDistanceMap(problem);
        offspringList.sort(new IndividualComparator(offspringMap));
        List<Individual> individuals = new ArrayList<>();
        individuals.addAll(parentList.subList(0, eliteNum));
        individuals.addAll(offspringList.subList(0, individuals.size() - eliteNum));
        return new Population(individuals);
    }

}

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
