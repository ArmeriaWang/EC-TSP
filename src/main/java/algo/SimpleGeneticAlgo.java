package algo;

import problem.TSPProblem;

public abstract class SimpleGeneticAlgo {

    protected final int individualNum;
    protected final int generationCntUpper;

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

    public abstract int solve(TSPProblem problem);

}
