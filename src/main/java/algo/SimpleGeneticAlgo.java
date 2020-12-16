package algo;

import base.Population;
import problem.TSPProblem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class SimpleGeneticAlgo {

    protected final int individualNum;
    protected final int generationCntUpper;
    private final String logPath = "out/log/";
    protected String logName;
    private File logFile;

    protected SimpleGeneticAlgo(int IndividualNum, int generationCntUpper) {
        this.individualNum = IndividualNum;
        this.generationCntUpper = generationCntUpper;
    }

    public static SimpleGeneticAlgo getAlgoInstance(int type, int IndividualNum, int generationCntUpper) {
        switch (type) {
            case 1:
                return new SGAlgo1(IndividualNum, generationCntUpper);
            case 2:
                return new SGAlgo2(IndividualNum, generationCntUpper);
            case 3:
                return new SGAlgo3(IndividualNum, generationCntUpper);
            default:
                throw new IllegalArgumentException();
        }
    }

    public abstract void solve(TSPProblem problem) throws IOException;

    protected void createLog(String logName) throws IOException {
        logFile = new File(logPath + logName);
        System.out.println(logPath + logName);
        logFile.createNewFile();
    }

    protected void writeLog(int generationCnt, Population population, TSPProblem problem) throws IOException {
        FileWriter fileWriter = new FileWriter(logFile, true);
        fileWriter.write(String.format("After %d generations:\n", generationCnt));
        fileWriter.write(String.format("  least  : %d\n", population.getLeastTourDis(problem)));
        fileWriter.write(String.format("  mean   : %d\n", population.getMeanTourDis(problem)));
        fileWriter.write(String.format("  std dev: %d\n\n", population.getStandardDeviationTourDis(problem)));
    }

}
