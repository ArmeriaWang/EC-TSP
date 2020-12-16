package algo;

import base.Population;
import problem.TSPProblem;
import utils.ResultRecorder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class SimpleGeneticAlgo {

    protected final int individualNum;
    protected final int generationCntUpper;
    protected final ResultRecorder resultRecorder;
    private final String logPath = "out/log/";
    protected String logName;
    private File logFile;

    protected SimpleGeneticAlgo(int IndividualNum, int generationCntUpper, ResultRecorder resultRecorder) {
        this.individualNum = IndividualNum;
        this.generationCntUpper = generationCntUpper;
        this.resultRecorder = resultRecorder;
    }

    public static SimpleGeneticAlgo getAlgoInstance(int type,
                                                    int IndividualNum,
                                                    int generationCntUpper,
                                                    ResultRecorder resultRecorder) {
        switch (type) {
            case 1:
                return new SGAlgo1(IndividualNum, generationCntUpper, resultRecorder);
            case 2:
                return new SGAlgo2(IndividualNum, generationCntUpper, resultRecorder);
            case 3:
                return new SGAlgo3(IndividualNum, generationCntUpper, resultRecorder);
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

    protected void writeLog(int generationCnt,
                            Population population,
                            int leastDis,
                            TSPProblem problem) throws IOException {
        FileWriter fileWriter = new FileWriter(logFile, true);
        fileWriter.write(String.format("After %d generations:\n", generationCnt));
        fileWriter.write(String.format("  least  : %d\n", population.getLeastTourDis(problem)));
        fileWriter.write(String.format("  mean   : %d\n", population.getMeanTourDis(problem)));
        fileWriter.write(String.format("  std dev: %d\n", population.getStandardDeviationTourDis(problem)));
        fileWriter.write(String.format("least until now: %d\n\n", leastDis));
        fileWriter.flush();
        fileWriter.close();
    }

}
