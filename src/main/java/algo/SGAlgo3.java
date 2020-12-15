package algo;

import base.Individual;
import base.Population;
import problem.TSPProblem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SGAlgo3 extends SimpleGeneticAlgo{

    private final Random random = new Random();

    private final double probCross = 0.8;
    private final double probMutation = 0.1;

    protected SGAlgo3(int IndividualNum, int generationCntUpper) {
        super(IndividualNum, generationCntUpper);
    }

    @Override
    public int solve(TSPProblem problem) {
        Population population = new Population(individualNum, problem.getCityNum());
        int generationCnt = 1;
        while (true) {
            List<Individual> offsprings = new ArrayList<>();
            Population matingPool = population.elitism(problem);
            List<Individual> matingIndividuals = matingPool.getIndividuals();
            for (int i = 0; i < individualNum; i += 2) {
                double randNum = Math.random();
                if (randNum < probCross) {
                    List<Individual> children = matingIndividuals.get(i)
                            .partiallyMappedCrossover(matingIndividuals.get(i + 1));
                    offsprings.addAll(children);
                }
                else {
                    offsprings.add(matingIndividuals.get(i));
                    offsprings.add(matingIndividuals.get(i + 1));
                }
            }
            for (int i = 0; i < individualNum; i++) {
                while (Math.random() < probMutation) {
                    offsprings.set(i, offsprings.get(i).scramble());
                }
            }
            population = new Population(offsprings);
            if (generationCnt > generationCntUpper) {
                return population.getLeastPathDistance(problem);
            }
            generationCnt++;
        }
    }

}
