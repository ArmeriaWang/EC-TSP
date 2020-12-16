package utils;

import base.Population;
import problem.TSPProblem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ResultRecorder {

    private final String filePath = "out/log/";
    private final File file;

    public ResultRecorder(String algoName) throws IOException {
        file = new File(filePath + "result_" + algoName + ".txt");
        file.createNewFile();
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(algoName + "\n\n");
        fileWriter.flush();
        fileWriter.close();
    }

    public void writeNewRun(TSPProblem problem, int individualNum) throws IOException {
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.write("\nInstance name: " + problem.getName() + "\n");
        fileWriter.write("Population size: " + individualNum + "\n\n");
        fileWriter.flush();
        fileWriter.close();
    }

    public void writeResult(int generationCnt,
                            Population population,
                            int leastDis,
                            TSPProblem problem) throws IOException {
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.write(String.format("  After %d generations:\n", generationCnt));
        fileWriter.write(String.format("    least  : %d\n", population.getLeastTourDis(problem)));
        fileWriter.write(String.format("    mean   : %d\n", population.getMeanTourDis(problem)));
        fileWriter.write(String.format("    std dev: %d\n", population.getStandardDeviationTourDis(problem)));
        fileWriter.write(String.format("  least until now: %d\n\n", leastDis));
        fileWriter.flush();
        fileWriter.close();
    }

}
