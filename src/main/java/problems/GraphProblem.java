package problems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GraphProblem extends TSPProblem{

    private final List<List<Integer>> weights;

    public GraphProblem(String name, int cityNum, List<List<Integer>> weights) {
        super(name, cityNum);
        List<List<Integer>> tmpWeights = new ArrayList<>();
        for (List<Integer> list : weights) {
            tmpWeights.add(Collections.unmodifiableList(list));
        }
        this.weights = Collections.unmodifiableList(tmpWeights);
    }

    @Override
    public Integer getDistance(int city1, int city2) {
        return weights.get(city1).get(city2);
    }

}
