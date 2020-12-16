package algo;

import base.Individual;
import base.Population;
import problem.TSPProblem;
import utils.ResultRecorder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SGAlgo3 extends SimpleGeneticAlgo {

    private final double probCross = 0.8;
    private final double probMutation = 0.1;

    protected SGAlgo3(int IndividualNum, int generationCntUpper, ResultRecorder resultRecorder) {
        super(IndividualNum, generationCntUpper, resultRecorder);
    }

    @Override
    public void solve(TSPProblem problem) throws IOException {
        resultRecorder.writeNewRun(problem, individualNum);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String sysTime = sf.format(System.currentTimeMillis());
        logName = problem.getName() + "_algo3_" + "indNum_" + individualNum + "_" + sysTime + ".txt";
        createLog(logName);

        Population population = new Population(individualNum, problem.getCityNum());
        int generationCnt = 1;
        int leastDis = population.getLeastTourDis(problem);
        int sampleNum = (int) Math.sqrt(individualNum);
        while (true) {
            List<Individual> offsprings = new ArrayList<>();
            Population matingPool = population.tournamentSelection(problem, sampleNum);
            List<Individual> matingIndividuals = matingPool.getIndividuals();
            for (int i = 0; i < individualNum; i += 2) {
                double randNum = Math.random();
                if (randNum < probCross) {
                    List<Individual> children = matingIndividuals.get(i).cycleCrossover(matingIndividuals.get(i + 1));
                    offsprings.addAll(children);
                }
                else {
                    offsprings.add(matingIndividuals.get(i));
                    offsprings.add(matingIndividuals.get(i + 1));
                }
            }
            for (int i = 0; i < offsprings.size(); i++) {
                while (Math.random() < probMutation) {
                    offsprings.set(i, offsprings.get(i).inversion());
                }
            }
            population = matingPool.elitism(problem, new Population(offsprings), (int) (individualNum * 0.1));
            leastDis = Math.min(leastDis, population.getLeastTourDis(problem));
            generationCnt++;
            if (generationCnt % 100 == 0) {
                writeLog(generationCnt, population, leastDis, problem);
            }
            if (generationCnt == 5000 || generationCnt == 10000 || generationCnt == 20000) {
                resultRecorder.writeResult(generationCnt, population, leastDis, problem);
            }
            if (generationCnt > generationCntUpper) {
                population.getLeastTourDis(problem);
                return;
            }
        }
    }

}
