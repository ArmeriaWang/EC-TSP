import algo.SimpleGeneticAlgo;
import problem.ProblemReader;
import problem.TSPProblem;
import utils.ResultRecorder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String dataFilePath = "src/main/resources/data/";
    private static final List<File> fileList = new ArrayList<>();
    private static final List<Integer> populationSizeList = new ArrayList<>();

    public static void init() {
        fileList.add(new File(dataFilePath + "eil51.tsp"));
        fileList.add(new File(dataFilePath + "eil76.tsp"));
        fileList.add(new File(dataFilePath + "eil101.tsp"));
        fileList.add(new File(dataFilePath + "st70.tsp"));
        fileList.add(new File(dataFilePath + "kroA100.tsp"));
        fileList.add(new File(dataFilePath + "kroC100.tsp"));
        fileList.add(new File(dataFilePath + "kroD100.tsp"));
        fileList.add(new File(dataFilePath + "lin105.tsp"));
        fileList.add(new File(dataFilePath + "pcb442.tsp"));
        fileList.add(new File(dataFilePath + "pr2392.tsp"));
        populationSizeList.add(10);
        populationSizeList.add(20);
        populationSizeList.add(50);
        populationSizeList.add(100);
    }

    public static void runExp1() throws IOException {
        ResultRecorder resultRecorder = new ResultRecorder("Algo1");
        for (File file : fileList) {
            TSPProblem problem = ProblemReader.readProblem(file);
            for (int size : populationSizeList) {
                problem.solve(SimpleGeneticAlgo.getAlgoInstance(1, size, 20000, resultRecorder));
//                problem.solve(SimpleGeneticAlgo.getAlgoInstance(2, size, 20000,
//                        new ResultRecorder("Algo2")));
//                problem.solve(SimpleGeneticAlgo.getAlgoInstance(3, size, 20000, resultRecorder));
            }
        }
    }

    public static void runExp2() {

    }

    public static void main(String[] args) throws IOException {
        init();
        runExp1();
    }

}
