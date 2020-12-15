package problem;

import coordinate.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ProblemReader {

    private static String name;
    private static int cityNum;
    private static String edgeWeightType;
    private static String edgeWeightFormat;
    private static Scanner scanner;

    public static TSPProblem readProblem(File file) throws FileNotFoundException {
        scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.startsWith("NAME")) {
                name = line.split(":")[1].trim();
            }
            else if (line.startsWith("DIMENSION")) {
                cityNum = Integer.parseInt(line.split(":")[1].trim());
            }
            else if (line.startsWith("EDGE_WEIGHT_TYPE")) {
                edgeWeightType = line.split(":")[1].trim();
                if (edgeWeightType.equals("EUC_2D") || edgeWeightType.equals("ATT") || edgeWeightType.equals("GEO")) {
                    return readCoordProblem();
                }
                else if (edgeWeightType.equals("EXPLICIT")) {
                    return readGraphProblem();
                }
            }
        }
        throw new IllegalArgumentException();
    }

    private static CoordProblem readCoordProblem() {
        String line = scanner.nextLine().trim();
        while (!line.startsWith("NODE_COORD_SECTION")) {
            line = scanner.nextLine().trim();
        }
        List<Coordinate> coordinates = new ArrayList<>();
        line = scanner.nextLine().trim();
        while (!line.equals("EOF")) {
            String[] splits = line.split("\\s+");
            double x = Double.parseDouble(splits[1]);
            double y = Double.parseDouble(splits[2]);
            Coordinate c;
            switch (edgeWeightType) {
                case "EUC_2D":
                    c = new PlanarCoordinate(x, y);
                    break;
                case "GEO":
                    c = new GeoCoordinate(x, y);
                    break;
                case "ATT":
                    c = new ATTCoordinate(x, y);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            coordinates.add(c);
            line = scanner.nextLine().trim();
        }
        return new CoordProblem(name, cityNum, coordinates);
    }

    private static GraphProblem readGraphProblem() {
        String line = scanner.nextLine().trim();
        while (!line.startsWith("NODE_COORD_SECTION")) {
            line = scanner.nextLine().trim();
        }
        line = scanner.nextLine().trim();
        List<List<Integer>> weights = new ArrayList<>();
        while (!line.equals("EOF")) {
            if (line.startsWith("EDGE_WEIGHT_FORMAT")) {
                edgeWeightFormat = line.split(":")[1].trim();
            }
            else if (line.startsWith("EDGE_WEIGHT_SECTION")) {
                List<Integer> allList = new ArrayList<>();
                line = scanner.nextLine().trim();
                while (!(line.startsWith("EOF") || line.startsWith("DISPLAY_DATA_SECTION"))) {
                    String[] numbers = line.split("\\s+");
                    for (String split : numbers) {
                        allList.add(Integer.parseInt(split));
                    }
                    line = scanner.nextLine().trim();
                }
                for (int i = 0; i < cityNum; i++) {
                    weights.add(new ArrayList<>());
                    for (int j = 0; j < cityNum; j++) {
                        weights.get(i).add(0);
                    }
                }
                int index = 0;
                switch (edgeWeightFormat) {
                    case "FULL_MATRIX":
                        for (int i = 0; i < cityNum; i++) {
                            for (int j = 0; j < cityNum; j++) {
                                int weight_ij = allList.get(index);
                                weights.get(i).set(j, weight_ij);
                                index++;
                            }
                        }
                        break;
                    case "UPPER_ROW":
                        for (int i = 0; i < cityNum; i++) {
                            weights.get(i).set(i, 0);
                            for (int j = i + 1; j < cityNum; j++) {
                                int weight_ij = allList.get(index);
                                weights.get(i).set(j, weight_ij);
                                weights.get(j).set(i, weight_ij);
                                index++;
                            }
                        }
                        break;
                    case "UPPER_DIAG_ROW":
                        for (int i = 0; i < cityNum; i++) {
                            for (int j = i; j <= i; j++) {
                                int weight_ij = allList.get(index);
                                weights.get(i).set(j, weight_ij);
                                weights.get(j).set(i, weight_ij);
                                index++;
                            }
                        }
                        break;
                    case "LOWER_DIAG_ROW":
                        for (int i = 0; i < cityNum; i++) {
                            for (int j = 0; j <= i; j++) {
                                int weight_ij = allList.get(index);
                                weights.get(i).set(j, weight_ij);
                                weights.get(j).set(i, weight_ij);
                                index++;
                            }
                        }
                        break;
                }
            }
        }
        return new GraphProblem(name, cityNum, weights);
    }

}
